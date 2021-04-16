package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.ArticleTag.*
import com.example.myapplication.databinding.ActivityAppBinding
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.tabs.TabLayoutMediator
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import kotlin.math.abs
import kotlin.math.max

class AppActivity : AppCompatActivity(), FragmentOnClickListener {
    private var articles: List<ArticleModel> = listOf(
        ArticleModel(
            title = R.string.oboe_title,
            text = R.string.oboe_text,
            image = R.drawable.android_high_performance_game_audio_with_oboe_header,
            tags = listOf(DEVELOPER_NEWS, FEATURES),
            id = 0
        ), ArticleModel(
            title = R.string.android_12_title,
            text = R.string.android_12_text,
            image = R.drawable.android_12_logo,
            tags = listOf(DEVELOPER_NEWS, FEATURES),
            id = 1
        ), ArticleModel(
            title = R.string.final_challenge_title,
            text = R.string.final_chellenge_text,
            image = R.drawable.final_challeng,
            tags = listOf(DEVELOPER_NEWS, CHALLENGE_WEEK),
            id = 2
        ), ArticleModel(
            title = R.string.boost_develop_title,
            text = R.string.boost_develop_text,
            image = R.drawable.play_logo,
            tags = listOf(DEVELOPER_NEWS, OTHER),
            id = 3
        ), ArticleModel(
            title = R.string.week_three_challenge_title,
            text = R.string.week_three_challenge_text,
            image = R.drawable.week_three_challenge,
            tags = listOf(DEVELOPER_NEWS, CHALLENGE_WEEK),
            id = 4
        ), ArticleModel(
            title = R.string.sub_dollar_title,
            text = R.string.sub_dollar_text,
            image = R.drawable.sub_dollar,
            tags = listOf(DEVELOPER_NEWS, OTHER),
            id = 5
        )
    )

    private lateinit var binding: ActivityAppBinding
    private val minScale = 0.65f
    private val minAlpha = 0.3f
    private var booleanTags1: BooleanArray = booleanArrayOf(true, true, true, true)
    private var badgeStateArray = arrayOf(
        BadgeState(0, 0),
        BadgeState(1, 0),
        BadgeState(2, 0),
        BadgeState(3, 0),
        BadgeState(4, 0),
        BadgeState(5, 0),
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        if (savedInstanceState != null) {
            booleanTags1 = savedInstanceState.getBooleanArray("Array")
                ?: error("Error in save state!")
            badgeStateArray = savedInstanceState.getParcelableArray("badges") as Array<BadgeState>

        }
        articlesFilter(articles)
        restoreBadgeState(badgeStateArray)
        binding.toolbar.title = "Developer news"
        binding.toolbar.menu.findItem(R.id.actionFilter).setOnMenuItemClickListener {
            showFilterDialogFragment()
            true
        }


        binding.containerViewPager2.setPageTransformer { page, position ->
            when {
                position < -1 -> {
                    page.alpha = 0f
                }
                position <= 1 -> {
                    page.scaleX = max(minScale, 1 - abs(position))
                    page.scaleY = max(minScale, 1 - abs(position))
                    page.alpha = max(minAlpha, 1 - abs(position))
                }
                else -> {
                    page.alpha = 0f
                }
            }
        }


        binding.containerViewPager2.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                badgeStateArray[position].count = 0
                binding.tabsPager.getTabAt(position)?.removeBadge()
            }
        })

    }


    private fun articlesFilter(listArticles: List<ArticleModel>) {
        val filterArticlesList = mutableSetOf<ArticleModel>()
        if (booleanTags1[0]) {
            filterArticlesList.addAll(listArticles.filter {
                it.tags.contains(FEATURES)
            })
        }
        if (booleanTags1[1]) {
            filterArticlesList.addAll(listArticles.filter {
                it.tags.contains(DEVELOPER_NEWS)
            })
        }
        if (booleanTags1[2]) {
            filterArticlesList.addAll(listArticles.filter {
                it.tags.contains(CHALLENGE_WEEK)
            })
        }
        if (booleanTags1[3]) {
            filterArticlesList.addAll(listArticles.filter {
                it.tags.contains(OTHER)
            })
        }
        val adapter = ViewPagerAdapter(filterArticlesList.toList(), this)
        binding.containerViewPager2.adapter = adapter
        val wormDotsIndicator = findViewById<WormDotsIndicator>(R.id.worm_dots_indicator)
        val viewPager = findViewById<ViewPager2>(R.id.containerViewPager2)
        viewPager.adapter = adapter
        wormDotsIndicator.setViewPager2(viewPager)
        TabLayoutMediator(binding.tabsPager, binding.containerViewPager2) { tab, position ->
            tab.text = "News #${position + 1}"
        }.attach()
    }

    private fun restoreBadgeState(badgeArray: Array<BadgeState>) {
        for (badge in badgeArray) {
            createBadge(badge.ArticleId, badge.count)
        }
    }

    private fun createBadge(id: Int, count: Int) {
        if (count > 0) {
            binding.tabsPager.getTabAt(id)?.orCreateBadge?.apply {
                number = count
                badgeGravity = BadgeDrawable.TOP_END
            }
        }

    }

    override fun onFragmentClick(data: Any) {
        if (data is Int) {
            binding.tabsPager.getTabAt(data)?.orCreateBadge?.apply {
                number += 1
                for (badge in badgeStateArray) {
                    if (badge.ArticleId == data) {
                        badge.increaseCount()
                    }
                }
                badgeGravity = BadgeDrawable.TOP_END
            }
        }
        if (data is BooleanArray) {
            booleanTags1 = data
            articlesFilter(articles)
        }
    }

    //Отобразить диалог без фрагмента
    private fun showFilterDialog() {
        val listTags = arrayOf("Features", "Developer news", "Challenge week", "Other")
        val booleanTags = booleanArrayOf(true, true, true, true)
        AlertDialog.Builder(this)
            .setTitle("Choose tags")
            .setMultiChoiceItems(listTags, booleanTags) { _, which, _ ->
                toast(this, "You choose ${listTags[which]}")
            }
            .setPositiveButton("Filter", { _, _ -> })
            .setNegativeButton("Cancel", { _, _ -> })
            .show()

    }

    private fun showFilterDialogFragment() {
        FilterDialogFragment.newInstance(booleanTags1)
            .show(supportFragmentManager, "Filter dialog")
    }

    companion object {
        fun toast(context: Context, text: String) {
            Toast.makeText(
                context,
                text,
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBooleanArray("Array", booleanTags1)
        outState.putParcelableArray("badges", badgeStateArray)

    }


}

package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.ArticleTag.*
import com.example.myapplication.databinding.ActivityAppBinding
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.tabs.TabLayoutMediator
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import java.util.ArrayList
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
    private var badgeStateId0 = BadgeState(0, 0)
    private var badgeStateId1 = BadgeState(1, 0)
    private var badgeStateId2 = BadgeState(2, 0)
    private var badgeStateId3 = BadgeState(3, 0)
    private var badgeStateId4 = BadgeState(4, 0)
    private var badgeStateId5 = BadgeState(5, 0)

    //Пытался через лист, но как сохранить лист из инстанс дата класса?
    private var badgeStateList = listOf<BadgeState>(
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
            badgeStateId0 = savedInstanceState.getParcelable("Id0")
                ?: error("Error in parcel!")
            badgeStateId1 = savedInstanceState.getParcelable("Id1")
                ?: error("Error in parcel!")
            badgeStateId2 = savedInstanceState.getParcelable("Id2")
                ?: error("Error in parcel!")
            badgeStateId3 = savedInstanceState.getParcelable("Id3")
                ?: error("Error in parcel!")
            badgeStateId4 = savedInstanceState.getParcelable("Id4")
                ?: error("Error in parcel!")
            badgeStateId5 = savedInstanceState.getParcelable("Id5")
                ?: error("Error in parcel!")

        }
        articlesFilter(articles)
        restoreAllBadge()
        binding.toolbar.title = "Developer news"
        binding.toolbar.menu.findItem(R.id.actionFilter).setOnMenuItemClickListener {
            showFilterDialogFragment()
            //showFilterDialog()
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
        // restoreBadgeState(badgeStateList)
    }

    private fun restoreBadgeState(badge: BadgeState) {
        when (badge.ArticleId) {
            0 -> createBadge(0, badge.count)
            1 -> createBadge(1, badge.count)
            2 -> createBadge(2, badge.count)
            3 -> createBadge(3, badge.count)
            4 -> createBadge(4, badge.count)
            5 -> createBadge(5, badge.count)
        }
    }

    private fun restoreAllBadge() {
        restoreBadgeState(badgeStateId0)
        restoreBadgeState(badgeStateId1)
        restoreBadgeState(badgeStateId2)
        restoreBadgeState(badgeStateId3)
        restoreBadgeState(badgeStateId4)
        restoreBadgeState(badgeStateId5)
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
                when (data) {
                    0 -> badgeStateId0.increaseCount()
                    1 -> badgeStateId1.increaseCount()
                    2 -> badgeStateId2.increaseCount()
                    3 -> badgeStateId3.increaseCount()
                    4 -> badgeStateId4.increaseCount()
                    5 -> badgeStateId5.increaseCount()
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
        outState.putParcelable("Id0", badgeStateId0)
        outState.putParcelable("Id1", badgeStateId1)
        outState.putParcelable("Id2", badgeStateId2)
        outState.putParcelable("Id3", badgeStateId3)
        outState.putParcelable("Id4", badgeStateId4)
        outState.putParcelable("Id5", badgeStateId5)

    }


}

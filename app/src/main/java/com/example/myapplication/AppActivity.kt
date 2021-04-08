package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
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
import kotlin.math.abs
import kotlin.math.max

class AppActivity : AppCompatActivity(), FragmentOnClickListener {
    private var articles: List<ArticleModel> = listOf(
        ArticleModel(
            R.string.oboe_title,
            R.string.oboe_text,
            R.drawable.android_high_performance_game_audio_with_oboe_header,
            listOf(DEVELOPER_NEWS, FEATURES)
        ), ArticleModel(
            R.string.android_12_title,
            R.string.android_12_text,
            R.drawable.android_12_logo,
            listOf(DEVELOPER_NEWS, FEATURES)
        ), ArticleModel(
            R.string.final_challenge_title,
            R.string.final_chellenge_text,
            R.drawable.final_challeng,
            listOf(DEVELOPER_NEWS, CHALLENGE_WEEK)
        ), ArticleModel(
            R.string.boost_develop_title,
            R.string.boost_develop_text,
            R.drawable.play_logo,
            listOf(DEVELOPER_NEWS, OTHER)
        ), ArticleModel(
            R.string.week_three_challenge_title,
            R.string.week_three_challenge_text,
            R.drawable.week_three_challenge,
            listOf(DEVELOPER_NEWS, CHALLENGE_WEEK)
        ), ArticleModel(
            R.string.sub_dollar_title,
            R.string.sub_dollar_text,
            R.drawable.sub_dollar,
            listOf(DEVELOPER_NEWS, OTHER)
        )
    )

    private lateinit var binding: ActivityAppBinding
    private val minScale = 0.65f
    private val minAlpha = 0.3f
    private var booleanTags1: BooleanArray = booleanArrayOf(true, true, true, true)
    private var booleanTags2: BooleanArray = booleanArrayOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
/*        if (savedInstanceState != null) {
            booleanTags2 = savedInstanceState.getBooleanArray("Array")
                ?: error("Error in save state!")
            Log.d(
                "CustomTAF",
                "сейчас booleanTags2 такой в месте восстановления : ${booleanTags2[0]},${booleanTags2[1]},${booleanTags2[2]},${booleanTags2[3]}"
            )
        }*/
        articlesFilter(articles)
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


        binding.testButton.setOnClickListener {
            val currentItem = binding.containerViewPager2.currentItem
            if (currentItem <= 4) {
                binding.containerViewPager2.currentItem = currentItem + 1
            } else binding.containerViewPager2.currentItem = 0
        }

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
                it.tags.contains(OTHER)
            })
        }
        if (booleanTags1[3]) {
            filterArticlesList.addAll(listArticles.filter {
                it.tags.contains(CHALLENGE_WEEK)
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

    override fun onFragmentClick(data: Any) {
        if (data is Int) {
            binding.tabsPager.getTabAt(data)?.orCreateBadge?.apply {
                number += 1
                badgeGravity = BadgeDrawable.TOP_END
            }
        }
        if (data is BooleanArray) {
            booleanTags1 = data
            booleanTags2 = data
            articlesFilter(articles)
            Log.d(
                "CustomTAF",
                "сейчас booleanTags2 такой : ${booleanTags2[0]},${booleanTags2[1]},${booleanTags2[2]},${booleanTags2[3]}"
            )
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
        Log.d(
            "CustomTAF",
            "сейчас лист такой в активити: ${booleanTags1[0]},${booleanTags1[1]},${booleanTags1[2]},${booleanTags1[3]}"
        )
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

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putBooleanArray("Array", booleanTags2)

    }

}

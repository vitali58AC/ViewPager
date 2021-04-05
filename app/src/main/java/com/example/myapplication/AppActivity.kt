package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.databinding.ActivityAppBinding
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.tabs.TabLayoutMediator
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import kotlin.math.abs
import kotlin.math.max

class AppActivity : AppCompatActivity(), FragmentOnClickListener {
    private val articles: List<ArticleModel> = listOf(
        ArticleModel(
            R.string.oboe_title,
            R.string.oboe_text,
            R.drawable.android_high_performance_game_audio_with_oboe_header
        ), ArticleModel(
            R.string.android_12_title,
            R.string.android_12_text,
            R.drawable.android_12_logo
        ), ArticleModel(
            R.string.final_challenge_title,
            R.string.final_chellenge_text,
            R.drawable.final_challeng
        ), ArticleModel(
            R.string.boost_develop_title,
            R.string.boost_develop_text,
            R.drawable.play_logo
        ), ArticleModel(
            R.string.week_three_challenge_title,
            R.string.week_three_challenge_text,
            R.drawable.week_three_challenge
        ), ArticleModel(
            R.string.sub_dollar_title,
            R.string.sub_dollar_text,
            R.drawable.sub_dollar
        )
    )

    private lateinit var binding: ActivityAppBinding
    private val minScale = 0.65f
    private val minAlpha = 0.3f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val adapter = ViewPagerAdapter(articles, this)
        binding.containerViewPager2.adapter = adapter
        val wormDotsIndicator = findViewById<WormDotsIndicator>(R.id.worm_dots_indicator)
        val viewPager = findViewById<ViewPager2>(R.id.containerViewPager2)
        viewPager.adapter = adapter
        wormDotsIndicator.setViewPager2(viewPager)
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
        TabLayoutMediator(binding.tabsPager, binding.containerViewPager2) { tab, position ->
            tab.text = "News #${position + 1}"
        }.attach()

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

    override fun onFragmentClick(data: Any) {
        binding.tabsPager.getTabAt(data as Int)?.orCreateBadge?.apply {
            number += 1
            badgeGravity = BadgeDrawable.TOP_END
        }
    }
}

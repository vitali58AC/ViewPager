package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityAppBinding

class AppActivity : AppCompatActivity() {
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val adapter = ViewPagerAdapter(articles, this)

        binding.containerViewPager2.adapter = adapter
    }
}
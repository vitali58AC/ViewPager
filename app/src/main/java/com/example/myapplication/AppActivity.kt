package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityAppBinding

class AppActivity : AppCompatActivity() {
    private val articles: List<ArticleModel> = listOf(
        ArticleModel(
            R.string.android_12_title,
            R.string.android_12_text,
            R.drawable.android_12_logo
        ), ArticleModel(
            R.string.android_12_title,
            R.string.android_12_text,
            R.drawable.android_12_logo
        ), ArticleModel(
            R.string.oboe_title,
            R.string.oboe_text,
            R.drawable.android_high_performance_game_audio_with_oboe_header
        ), ArticleModel(
            R.string.oboe_title,
            R.string.oboe_text,
            R.drawable.android_high_performance_game_audio_with_oboe_header
        ), ArticleModel(
            R.string.oboe_title,
            R.string.oboe_text,
            R.drawable.android_high_performance_game_audio_with_oboe_header
        ), ArticleModel(
            R.string.oboe_title,
            R.string.oboe_text,
            R.drawable.android_high_performance_game_audio_with_oboe_header
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
package com.example.myapplication

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.circularreveal.CircularRevealHelper

class ViewPagerAdapter(
    private val screens: List<ArticleModel>,
    activity: FragmentActivity
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return screens.size
    }

    override fun createFragment(position: Int): Fragment {
        val screen: ArticleModel = screens[position]
        return ViewPagerFragment.newInstance(
            screen.title,
            screen.text,
            screen.image
        )
    }
}
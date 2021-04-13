package com.example.myapplication

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ArticleModel(
    @StringRes val title: Int,
    @StringRes val text: Int,
    @DrawableRes val image: Int,
    val tags: List<ArticleTag>,
    val id: Int
) {
}
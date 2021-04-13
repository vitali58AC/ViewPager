package com.example.myapplication

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BadgeState(
    val ArticleId: Int,
    var count: Int
    ) : Parcelable {

        fun increaseCount(): BadgeState {
            return copy(count++)
        }
    }

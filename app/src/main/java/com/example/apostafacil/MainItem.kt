package com.example.apostafacil

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class MainItem(
    val id: Int,
    @DrawableRes val img: Int,
    @StringRes val textImg: Int,
    val colorBG: Int,
    val colorBtn: Int,
    @StringRes val textAposta: Int,
)

package com.kareemdev.fakestore.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.kareemdev.fakestore.R

val fonts = FontFamily(
    Font(R.font.productsans_black),
    Font(R.font.productsans_bold, weight = FontWeight.Bold),
    Font(R.font.productsans_light, weight = FontWeight.Light),
    Font(R.font.productsans_thin, weight = FontWeight.Thin),
    Font(R.font.productsans_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
)
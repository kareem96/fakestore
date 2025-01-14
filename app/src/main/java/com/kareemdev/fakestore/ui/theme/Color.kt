package com.kareemdev.fakestore.ui.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val LightGray = Color(0xFFD8D8D8)
val DarkGray = Color(0xFF2A2A2A)
val StarColor = Color(0xFFFFC94D)

val ShimmerLightGray = Color(0xFFF1F1F1)
val ShimmerMediumGray = Color(0xFFE3E3E3)
val ShimmerDarkGray = Color(0xFF1D1D1D)

val greenColor = Color(0xFF0F9D58)
val NightBlue = Color(0xFF151B54)
val NaviBlue = Color(0xFF000080)

val Colors.statusBarColor
    get() = if (isLight) Purple700 else Color.Black

val Colors.welcomeScreenBackgroundColor
    get() = if (isLight) Color.White else Color.Black

val Colors.titleColor
    get() = if (isLight) DarkGray else LightGray

val Colors.descriptionColor
    get() = if (isLight) DarkGray.copy(alpha = 0.5f)
    else LightGray.copy(alpha = 0.5f)

val Colors.activeIndicatorColor
    get() = if (isLight) Purple500 else Purple700

val Colors.inactiveIndicatorColor
    get() = if (isLight) LightGray else DarkGray

val Colors.buttonBackgroundColor
    get() = if (isLight) Purple500 else Purple700

val Colors.topAppBarContentColor: Color
    get() = if (isLight) Color.White else LightGray

val Colors.topAppBarBackgroundColor: Color
    get() = if (isLight) Purple700 else Color.Black

val Colors.contrastColor: Color
    get() = if (isLight) Color.Black else Color.White

val Colors.Purple500_White: Color
    get() = if (isLight) Purple500 else Color.White

val Colors.White_Purple500: Color
    get() = if (isLight) Color.White else Purple500

val Colors.DarkGray_LightGray: Color
    get() = if (isLight) DarkGray else LightGray

val Colors.Purple500_Black: Color
    get() = if (isLight) Purple500 else Color.Black

val Colors.ShimmerMediumGray_ShimmerDarkGray: Color
    get() = if (isLight) ShimmerMediumGray else ShimmerDarkGray

val Colors.ShimmerLightGray_Black: Color
    get() = if (isLight) ShimmerLightGray else Color.Black
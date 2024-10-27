package com.kareemdev.fakestore.ui.navigation

sealed class Screen (val route: String){
    object SplashScreen : Screen("splash_screen")
    object LoginScreen : Screen("login_screen")
    object Home:Screen("home")
    object Category:Screen("category")
    object Cart:Screen("cart")
    object Profile:Screen("profile")
    object MainScreen:Screen("main_screen")
    object DetailNoteScreen:Screen("detail_note_screen")
    object CreateNoteScreen:Screen("create_note_screen")
}
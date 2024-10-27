package com.kareemdev.fakestore.ui.navigation.tool

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kareemdev.fakestore.FakeStoreApp
import com.kareemdev.fakestore.ui.navigation.Screen
import com.kareemdev.fakestore.ui.screen.auth.login.LoginScreen
import com.kareemdev.fakestore.ui.screen.cart.CartPage
import com.kareemdev.fakestore.ui.screen.category.CategoryPage
import com.kareemdev.fakestore.ui.screen.home.detail.DetailPage
import com.kareemdev.fakestore.ui.screen.home.HomePage
import com.kareemdev.fakestore.ui.screen.splash.SplashScreen
import com.kareemdev.fakestore.ui.theme.ThemeState

@Composable
fun SetupNavGraph(navHostController: NavHostController, themeState: MutableState<ThemeState>) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.SplashScreen.route,
    ) {
        composable(route = Screen.SplashScreen.route) {
            SplashScreen(navController = navHostController)
        }
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navHostController)
        }

        composable(
            route = "${Screen.MainScreen.route}?startPage={startPage}",
            arguments = listOf(navArgument("startPage") {
                defaultValue = 0
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val startPage = backStackEntry.arguments?.getInt("startPage") ?: 0
            FakeStoreApp(
                navController = navHostController,
                themeState = themeState,
                startPage = startPage
            )
        }

        composable(route = Screen.Home.route) {
            HomePage(navController = navHostController)
        }
        composable(route = Screen.Category.route) {
            CategoryPage(navController = navHostController)
        }
        composable(route = Screen.Cart.route) {
            CartPage(navController = navHostController)
        }

        composable(
            route = "${Screen.DetailNoteScreen.route}/{noteId}",
            arguments = listOf(navArgument("noteId"){
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId")
            DetailPage(navController = navHostController, noteId = noteId)
        }

        /*composable(route = Screen.CreateNoteScreen.route) {
            CreateNotePage(navController = navHostController)
        }*/
    }
}
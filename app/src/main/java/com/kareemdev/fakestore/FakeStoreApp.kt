package com.kareemdev.fakestore

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.kareemdev.fakestore.ui.navigation.NavigationItem
import com.kareemdev.fakestore.ui.navigation.tool.BottomNavigationBar
import com.kareemdev.fakestore.ui.theme.ThemeState
import androidx.compose.material3.Scaffold
import com.kareemdev.fakestore.ui.screen.cart.CartPage
import com.kareemdev.fakestore.ui.screen.category.CategoryPage
import com.kareemdev.fakestore.ui.screen.home.HomePage
import com.kareemdev.fakestore.ui.screen.profile.ProfilePage


@OptIn(ExperimentalPagerApi::class)
@Composable
fun FakeStoreApp(
    navController: NavHostController,
    themeState: MutableState<ThemeState>,
    startPage: Int = 0
) {
    val mainScreen = listOf(
        NavigationItem.Home,
        NavigationItem.Category,
        NavigationItem.Cart,
        NavigationItem.Profile,
    )
    val pagerState = rememberPagerState(initialPage = startPage)
    Scaffold(
    bottomBar = {
        BottomNavigationBar(pagerState = pagerState, items = mainScreen)
    },
    content = { padding ->
        Box(modifier = Modifier.padding(padding)) {
            ViewPagerScreen(
                pagerState = pagerState,
                appNavController = navController,
                themeState = themeState
            )
        }
    },
    )
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ViewPagerScreen(
    pagerState: PagerState,
    appNavController: NavHostController,
    themeState: MutableState<ThemeState>
) {
    val coroutineScope = rememberPagerState()
    HorizontalPager(count = 4, state = pagerState) { page ->
        when (page) {
            0 -> HomePage(navController = appNavController)
            1 -> CategoryPage(navController = appNavController)
            2 -> CartPage(navController = appNavController)
            3 -> ProfilePage(
                navController = appNavController,
                themeState = themeState
            )
        }

    }
}
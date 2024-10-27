package com.kareemdev.fakestore.ui.navigation.tool

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.kareemdev.fakestore.R
import com.kareemdev.fakestore.ui.navigation.NavigationItem
import com.kareemdev.fakestore.ui.theme.Purple500_Black
import com.kareemdev.fakestore.ui.theme.White_Purple500
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BottomNavigationBar(
    pagerState: PagerState,
    items: List<NavigationItem>,
) {
    val coroutineScope = rememberCoroutineScope()
    val bgColor = MaterialTheme.colors.Purple500_Black
    val contentColor = MaterialTheme.colors.White_Purple500

    BottomNavigation(backgroundColor = bgColor, contentColor = Color.White) {
        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                alwaysShowLabel = true,
                selectedContentColor = contentColor,
                unselectedContentColor = contentColor.copy(0.5f),
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                icon = {
                    if (item.title == "Home")
                        Icon(
                            painterResource(id = R.drawable.round_home),
                            contentDescription = item.title
                        )
                    else Icon(item.icon, contentDescription = item.title)
                },
            )
        }
    }
}
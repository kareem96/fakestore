package com.kareemdev.fakestore.ui.screen.splash

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kareemdev.fakestore.R
import com.kareemdev.fakestore.ui.navigation.Screen

@Composable
fun SplashScreen(
    navController: NavHostController,
    splashViewModel: SplashViewModel = hiltViewModel()
) {
    val degrees = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    val token by splashViewModel.userToken.collectAsState(initial = null)
    LaunchedEffect(key1 = true) {
        degrees.animateTo(
            targetValue = 360f,
            animationSpec = tween(
                durationMillis = 1000,
                delayMillis = 200
            )
        )
        navController.popBackStack()
        if (!token.isNullOrEmpty()) {
//            navController.navigate(Screen.MainScreen.route)
            navController.navigate("${Screen.MainScreen.route}?startPage=0")
        } else navController.navigate(Screen.LoginScreen.route)
    }
    Log.d("TAG", "SplashScreen: $token")
    Splash(degrees = degrees.value)
}


@Composable
fun Splash(degrees: Float) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.rotate(degrees = degrees),
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(R.string.logo),
            colorFilter = ColorFilter.tint(Color.White)
        )
    }
}
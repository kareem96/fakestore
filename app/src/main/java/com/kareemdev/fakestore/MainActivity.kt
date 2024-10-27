package com.kareemdev.fakestore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kareemdev.fakestore.ui.MainViewModel
import com.kareemdev.fakestore.ui.navigation.tool.SetupNavGraph
import com.kareemdev.fakestore.ui.theme.FakestoreTheme
import com.kareemdev.fakestore.ui.theme.ThemeState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        DialogManager.init(this, mainViewModel)
        enableEdgeToEdge()
        setContent {
            val themeState = mainViewModel.themeState.collectAsState()
            FakestoreTheme(
                themeState = themeState.value
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    navController = rememberNavController()
                    SetupNavGraph(
                        navHostController = navController,
                        themeState = themeState as MutableState<ThemeState>
                    )
                }
            }
        }
    }
}
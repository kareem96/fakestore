package com.kareemdev.fakestore.ui.screen.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kareemdev.fakestore.data.remote.response.BaseResult
import com.kareemdev.fakestore.data.remote.response.product.ProductResponse
import com.kareemdev.fakestore.data.remote.response.product.ProductResponseItem
import com.kareemdev.fakestore.ui.navigation.Screen
import com.kareemdev.fakestore.ui.theme.statusBarColor
import com.kareemdev.fakestore.ui.theme.welcomeScreenBackgroundColor

@Composable
fun HomePage(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val userInfo by homeViewModel.getUserInfo().observeAsState()
    val listProduct by homeViewModel.getListProduct.observeAsState()

    // System UI Controller for status bar
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = MaterialTheme.colors.primary)

    userInfo?.let { user ->
        LaunchedEffect(user) {
            homeViewModel.getListNote(user.token.toString())
        }
    }
    Scaffold(
        backgroundColor = MaterialTheme.colors.welcomeScreenBackgroundColor,
        topBar = {
            TopAppBar(
                title = { Text(text = "Home", style = MaterialTheme.typography.h5) },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    when (listProduct) {
                        is BaseResult.Loading -> {
                            Text(text = "Loading notes...")
                        }

                        is BaseResult.Success -> {
                            val products = (listProduct as BaseResult.Success<ProductResponse>).data ?: emptyList()
                            Column {
                                products.forEach { product ->
                                    NoteCard(
                                        product = product,
                                        onClick = { selected ->
                                            navController.navigate("${Screen.DetailNoteScreen.route}/${selected.id}")
                                        }
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }

                        is BaseResult.Failed -> {
                            val errorMessage = (listProduct as BaseResult.Failed).error
                            Text(text = "Error: $errorMessage")
                        }

                        is BaseResult.Idle -> {}
                        else -> {
                            Text(text = "Loading user data..")
                        }
                    }
                }

            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                Toast.makeText(context, "this feature not available", Toast.LENGTH_SHORT).show()
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    )
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteCard(product: ProductResponseItem, onClick: (ProductResponseItem) -> Unit) {
    Card(
        onClick = { onClick(product) },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row (
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Image(
                painter = rememberImagePainter(product.image),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .weight(1f)

            )

            Column(
                modifier = Modifier
                    .weight(4f)
                    .padding(start = 8.dp, end = 8.dp)
            ) {
                Text(text = product.title, style = MaterialTheme.typography.h6)
                Text(text = product.description, style = MaterialTheme.typography.body2)
            }
        }
    }
}
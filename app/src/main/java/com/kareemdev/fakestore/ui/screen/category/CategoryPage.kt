package com.kareemdev.fakestore.ui.screen.category

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kareemdev.fakestore.data.remote.response.BaseResult
import com.kareemdev.fakestore.data.remote.response.product.CategoryListResponseItem
import com.kareemdev.fakestore.ui.navigation.Screen

@Composable
fun CategoryPage(
    navController: NavController,
    categoryViewModel: CategoryViewModel = hiltViewModel()
) {
    val userInfo by categoryViewModel.getUserInfo().observeAsState()
    val titleCategory by categoryViewModel.titleCategory.observeAsState()
    val listCategoryMap by categoryViewModel.listCategoryMap.observeAsState(emptyMap())

    // System UI Controller for status bar
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = MaterialTheme.colors.primary)

    // Trigger fetching the title categories if user info is available
    userInfo?.token?.let { token ->
        LaunchedEffect(token) {
            categoryViewModel.getTitleCategory(token)
        }
    }
    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            TopAppBar(
                title = { Text(text = "Categories", style = MaterialTheme.typography.h5) },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                when (val result = titleCategory) {
                    is BaseResult.Loading -> {
                        Text(text = "Loading categories...")
                    }
                    is BaseResult.Success -> {
                        val titles = result.data ?: emptyList()
                        titles.forEach { title ->
                            Text(
                                text = title,
                                style = MaterialTheme.typography.h6,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )

                            // Fetch the list category for the current title if not already fetched
                            if (listCategoryMap[title] == null) {
                                LaunchedEffect(title) {
                                    categoryViewModel.getListCategory(userInfo?.token.orEmpty(), title)
                                }
                            }

                            // Check the state of the listCategory for the current title
                            when (val listCategory = listCategoryMap[title]) {
                                is BaseResult.Loading -> {
                                    Log.d("CategoryPage", "Loading categories for $title...")
                                    Text(text = "Loading categories...")
                                }
                                is BaseResult.Success -> {
                                    Log.d("CategoryPage", "Categories loaded for $title: ${listCategory.data}")
                                    val products = listCategory.data ?: emptyList()
                                    Column {
                                        products.forEach { product ->
                                            CategoryCard(
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
                                    Log.e("CategoryPage", "Error loading categories for $title: ${listCategory.error}")
                                    Text(text = "Error: ${listCategory.error}")
                                }
                                BaseResult.Idle -> {
                                    Text(text = "No categories available.")
                                }
                                null -> {
                                    Log.e("CategoryPage", "No data for category $title")
                                    Text(text = "Error: No data")
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                    is BaseResult.Failed -> {
                        Text(text = "Error: ${result.error}")
                    }
                    is BaseResult.Idle -> {
                        Text(text = "Loading user data...")
                    }
                    null -> {
                        Text(text = "Error: No data")
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryCard(product: CategoryListResponseItem, onClick: (CategoryListResponseItem) -> Unit) {
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
            // Left Image
            Image(
                painter = rememberImagePainter(product.image),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .weight(1f)
            )

            // Center Column for Title and Description
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
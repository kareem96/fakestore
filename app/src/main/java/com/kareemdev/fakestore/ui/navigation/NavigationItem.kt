package com.kareemdev.fakestore.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(
    var route: String,
    var icon: ImageVector,
    var title: String,
){
    object Home : NavigationItem("home", Icons.Default.Home, "Home")
    object Category : NavigationItem("category", Icons.Default.List, "Category")
    object Cart : NavigationItem("cart", Icons.Default.ShoppingCart, "Cart")
    object Profile : NavigationItem("profile", Icons.Default.AccountCircle, "Profile")
}
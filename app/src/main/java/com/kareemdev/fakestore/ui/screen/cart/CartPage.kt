package com.kareemdev.fakestore.ui.screen.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kareemdev.fakestore.data.remote.response.BaseResult
import com.kareemdev.fakestore.data.remote.response.product.CartResponse
import com.kareemdev.fakestore.data.remote.response.product.CartResponseItem
import com.kareemdev.fakestore.ui.theme.statusBarColor
import com.kareemdev.fakestore.ui.theme.welcomeScreenBackgroundColor
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CartPage(
    navController: NavController,
    cartViewModel: CartViewModel = hiltViewModel()
) {
    var showSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }
    var snackbarDuration by remember { mutableStateOf(SnackbarDuration.Short) }

    val listCart by cartViewModel.cart.observeAsState()

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colors.statusBarColor
    )

    // Fetch cart items
    LaunchedEffect(Unit) {
        cartViewModel.getListCart()
    }

    // BottomSheet state
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            CheckoutBottomSheet(
                onDismiss = { scope.launch { bottomSheetState.hide() } }
            )
        }
    ) {
        Scaffold(
            backgroundColor = MaterialTheme.colors.welcomeScreenBackgroundColor,
            topBar = {
                TopAppBar(
                    title = { Text(text = "Cart", style = MaterialTheme.typography.h5) },
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
                        when (listCart) {
                            is BaseResult.Loading -> {
                                Text(text = "Loading cart...")
                            }
                            is BaseResult.Success -> {
                                val products =
                                    (listCart as BaseResult.Success<CartResponse>).data ?: emptyList()
                                Column {
                                    products.forEach { product ->
                                        CartCard(
                                            product = product,
                                            onDeleteClick = { productId ->
                                                cartViewModel.deleteCart(productId)
                                            },
                                            onCheckoutClick = {
                                                scope.launch { bottomSheetState.show() }
                                            }
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }
                                }
                            }
                            is BaseResult.Failed -> {
                                val errorMessage = (listCart as BaseResult.Failed).error
                                Text(text = "Error: $errorMessage")
                            }
                            is BaseResult.Idle -> {}
                            null -> TODO()
                        }
                    }
                }
            },

            // Add Snackbar to the scaffold
            bottomBar = {
                if (showSnackbar) {
                    Snackbar(
                        backgroundColor = Color.White,
                        action = {
                            Button(onClick = {
                                showSnackbar = false // Close the snackbar on button click
                            }) {
                                Text("Dismiss")
                            }
                        },
                        modifier = Modifier.padding(16.dp),
                        content = { Text(snackbarMessage) }
                    )
                }
            }
        )
    }

    // Observe deleteCart state directly
    cartViewModel.deleteCart.observeAsState().value?.let { result ->
        when (result) {
            is BaseResult.Loading -> {
                // Show loading state if needed
            }
            is BaseResult.Success -> {
                // Show success message in Snackbar
                snackbarMessage = "Cart item deleted successfully."
                snackbarDuration = SnackbarDuration.Short
                showSnackbar = true
                cartViewModel.getListCart() // Refresh cart after delete
            }
            is BaseResult.Failed -> {
                // Show error message in Snackbar
                snackbarMessage = (result as BaseResult.Failed).error.toString()
                snackbarDuration = SnackbarDuration.Short
                showSnackbar = true
            }
            else -> {}
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CartCard(
    product: CartResponseItem,
    onDeleteClick: (Int) -> Unit,
    onCheckoutClick: () -> Unit
) {
    Card(
        onClick = { /* Handle product click if needed */ },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "ID: ${product.id}", style = MaterialTheme.typography.h6)
            Spacer(Modifier.height(5.dp))
            product.products.forEach {
                Text(
                    text = "Product ID: ${it.productId} = Quantity: ${it.quantity}",
                    style = MaterialTheme.typography.body2
                )
            }
            Spacer(Modifier.height(5.dp))
            Text(text = "Date: ${product.date}", style = MaterialTheme.typography.body2)

            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Red
                    ),
                    onClick = {
                        onDeleteClick(product.id) // Pass the product ID to the delete function
                    },
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .weight(1f)
                ) {
                    Text("Delete Cart")
                }
                Button(
                    onClick = {
                        onCheckoutClick() // Trigger checkout action
                    },
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .weight(1f)
                ) {
                    Text("Checkout Cart")
                }
            }
        }
    }
}

@Composable
fun CheckoutBottomSheet(onDismiss: () -> Unit) {
    Column(

        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .padding(16.dp)
    ) {
        Text(text = "Checkout", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(16.dp))

        // Add your checkout UI elements here
        Text(text = "Review your items and confirm your purchase.")

        // Add confirm and cancel buttons
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ){
            Button(
                onClick = {
                    onDismiss()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Confirm Purchase")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = onDismiss,
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancel")
            }
        }
    }
}
package com.kareemdev.fakestore.ui.screen.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kareemdev.fakestore.data.remote.response.BaseResult
import com.kareemdev.fakestore.ui.navigation.Screen

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var user by remember { mutableStateOf("mor_2314") }
    var password by remember { mutableStateOf("83r5^_") }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var showLoadingDialog by remember { mutableStateOf(false) }

    val loginResult by viewModel.loginResult.observeAsState(BaseResult.Idle)

    // Handle login result state
    LaunchedEffect(loginResult) {
        when (loginResult) {
            is BaseResult.Loading -> showLoadingDialog = true
            is BaseResult.Success -> {
                showLoadingDialog = false
                navController.navigate(Screen.MainScreen.route) {
                    popUpTo(Screen.LoginScreen.route) { inclusive = true }
                }
            }
            is BaseResult.Failed -> {
                showLoadingDialog = false
                errorMessage = (loginResult as BaseResult.Failed).error.toString()
                showErrorDialog = true
            }
            is BaseResult.Idle -> {
                showLoadingDialog = false
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF))
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.align(Alignment.Center)
        ) {
            // Title
            Text(
                text = "Welcome Back!",
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Username input
            TextField(
                value = user,
                onValueChange = { user = it },
                label = { Text("Username") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = MaterialTheme.colors.primary,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = MaterialTheme.colors.primary,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Login button
            Button(
                onClick = {
                    if (user.isNotBlank() && password.isNotBlank()) {
                        viewModel.login(user, password)
                    } else {
                        errorMessage = "Please fill in all fields."
                        showErrorDialog = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login", color = Color.White)
            }
        }

        // Loading dialog
        if (showLoadingDialog) {
            AlertDialog(
                onDismissRequest = { /* No action */ },
                title = { Text("Loading") },
                text = { Text("Please wait...") },
                confirmButton = {}
            )
        }

        // Error dialog
        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = {
                    showErrorDialog = false
                },
                title = { Text("Login Failed") },
                text = { Text(errorMessage) },
                confirmButton = {
                    Button(onClick = {
                        showErrorDialog = false
                    }) {
                        Text("OK")
                    }
                },
            )
        }
    }
}
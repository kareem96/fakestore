package com.kareemdev.fakestore.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kareemdev.fakestore.R
import com.kareemdev.fakestore.ui.navigation.Screen
import com.kareemdev.fakestore.ui.theme.MEDIUM_PADDING
import com.kareemdev.fakestore.ui.theme.PROFILE_ICON_SIZE
import com.kareemdev.fakestore.ui.theme.Purple500
import com.kareemdev.fakestore.ui.theme.Purple500_Black
import com.kareemdev.fakestore.ui.theme.Theme
import com.kareemdev.fakestore.ui.theme.ThemeState
import com.kareemdev.fakestore.ui.theme.fonts
import com.kareemdev.fakestore.ui.theme.statusBarColor
import com.kareemdev.fakestore.ui.theme.welcomeScreenBackgroundColor

@Composable
fun ProfilePage(
    navController: NavHostController,
    themeState: MutableState<ThemeState>,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colors.statusBarColor
    )
    val context = LocalContext.current
    val displayColor = MaterialTheme.colors.Purple500_Black

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.welcomeScreenBackgroundColor)
    ) {
        Row {
            AsyncImage(
                model = ImageRequest.Builder(context).error(R.drawable.account_circle)
                    .data("user")
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(start = MEDIUM_PADDING, end = MEDIUM_PADDING, bottom = MEDIUM_PADDING)
                    .weight(3f)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(160.dp))
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MEDIUM_PADDING),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            Icon(
                modifier = Modifier.size(PROFILE_ICON_SIZE),
                imageVector = Icons.Default.Check,
                tint = displayColor,
                contentDescription = "DarkMode"
            )
            Spacer(modifier = Modifier.width(16.dp))
            androidx.compose.material.Text(
                text = stringResource(id = R.string.dark_theme),
                fontSize = MaterialTheme.typography.subtitle1.fontSize,
                fontWeight = FontWeight.Bold,
                fontFamily = fonts,
                color = displayColor,
            )
            Spacer(Modifier.weight(1f))
            Switch(
                checked = themeState.value.theme == Theme.Dark,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Purple500,
//                    checkedTrackColor = Color.ShimmerLightGray
                ),
                onCheckedChange = { checked ->
                    themeState.value = ThemeState(if (checked) Theme.Dark else Theme.Light)
                    profileViewModel.changeTheme(theme = themeState.value.theme)
                }
            )
        }

        Row(
            modifier = Modifier
                .padding(start = MEDIUM_PADDING)
                .clickable {
                    profileViewModel.logout()
                    navController.navigate(Screen.LoginScreen.route)
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            Icon(
                modifier = Modifier.size(PROFILE_ICON_SIZE),
                imageVector = Icons.Default.ExitToApp,
                tint = Color.Red,
                contentDescription = "Logout"
            )
            Spacer(modifier = Modifier.width(MEDIUM_PADDING))
            androidx.compose.material.Text(
                text = stringResource(id = R.string.log_out),
                modifier = Modifier.padding(vertical = MEDIUM_PADDING),
                fontSize = MaterialTheme.typography.subtitle1.fontSize,
                fontWeight = FontWeight.Bold,
                fontFamily = fonts,
                color = Color.Red,
            )
        }
    }
}
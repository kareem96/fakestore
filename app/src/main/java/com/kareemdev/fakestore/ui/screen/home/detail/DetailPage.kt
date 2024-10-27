package com.kareemdev.fakestore.ui.screen.home.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.kareemdev.fakestore.data.remote.response.BaseResult
import com.kareemdev.fakestore.data.remote.response.product.DetailProductResponse
import com.kareemdev.fakestore.ui.screen.home.HomeViewModel

@Composable
fun DetailPage(
    navController: NavController,
    noteId: Int?,
    viewModel: HomeViewModel = hiltViewModel()
) {
    var title by remember { mutableStateOf("") }
    var image by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val noteById by viewModel.getNoteById.observeAsState()

    LaunchedEffect(noteId) {
        noteId?.let { viewModel.getNoteById(it) }
    }

    Column {
        TopAppBar(
            title = { Text("Detail Product") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            backgroundColor = MaterialTheme.colors.primary,
            modifier = Modifier.statusBarsPadding()
        )

        when (noteById) {
            is BaseResult.Success -> {
                val note = (noteById as BaseResult.Success<DetailProductResponse?>).data
                title = note?.title.orEmpty()
                image = note?.image.orEmpty()
                description = note?.description.orEmpty()
            }

            is BaseResult.Loading -> {
                Text(text = "Loading notes...")
            }

            is BaseResult.Failed -> {
                Text("Error: ${(noteById as BaseResult.Failed).error}")
            }

            is BaseResult.Idle -> {}
            else -> {}
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Form Fields for Title and Description
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .fillMaxWidth()
        ) {

            // Center the Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Image(
                    painter = rememberImagePainter(data = image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .align(Alignment.Center)
                )
            }

            // Title and Description
            Text(
                text = title,
                style = MaterialTheme.typography.h6
            )
            Text(
                text = description,
                style = MaterialTheme.typography.body2
            )

            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Button(
                    onClick = {
                        /*if (noteId != null) {
                            viewModel.updateNoteById(noteId, title, description)
                        }*/
                    },
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .weight(1f)
                ) {
                    Text("Add to cart")
                }

            }
        }
    }
}
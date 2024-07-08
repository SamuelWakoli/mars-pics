package com.samwrotethecode.marspics.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Replay
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.samwrotethecode.marspics.domain.MarsPics

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    val viewModel: MarsViewModel = viewModel(factory = MarsViewModel.Factory)
    val uiState = viewModel.uiState.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Mars Pics") }, actions = {
                if (uiState is MarsUiState.Error)
                    IconButton(onClick = { viewModel.retry() }) {
                        Icon(imageVector = Icons.Outlined.Replay, contentDescription = "Reload")
                    }
            })
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            when (uiState) {
                is MarsUiState.Loading -> LoadingPage()
                is MarsUiState.Success -> PicsPage(pics = uiState.marsPics)
                is MarsUiState.Error -> ErrorPage()
            }
        }
    }
}

@Composable
fun LoadingPage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorPage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Outlined.Warning,
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = "An error occurred", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun PicsPage(
    modifier: Modifier = Modifier,
    pics: List<MarsPics>
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(260.dp),
        contentPadding = PaddingValues(
            horizontal = 4.dp,
            vertical = 4.dp
        ),
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        items(pics) { pic ->
            androidx.compose.material3.Card(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
            ) {
                AsyncImage(
                    model = pic.imgSrc,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}

package com.example.marvel.presentation.screen.comics.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.marvel.R
import com.example.marvel.presentation.components.ShimmerComicItem
import com.example.marvel.presentation.screen.comics.home.components.ComicItem
import com.example.marvel.presentation.screen.components.EmptyList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComicsHomeScreen(
    viewModel: ComicsHomeViewModel = hiltViewModel(),
    onNavigateComicDetail: (String) -> Unit,
    onNavigateComicsFavorites: () -> Unit
) {

    val state = viewModel.state

    Scaffold(
        modifier = Modifier
            .background(Color(0xFFfafafa))
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.title_app),
                        color = Color(0xFFFEFEFE),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        fontSize = 24.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFEC1C23),
                    navigationIconContentColor = Color(0xFFFEFEFE),
                    actionIconContentColor = Color(0xFFFEFEFE)
                ),
                actions = {
                    IconButton(
                        onClick = { onNavigateComicsFavorites() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = null
                        )
                    }
                }
            )

        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 12.dp,
                    end = 12.dp,
                    top = paddingValues.calculateTopPadding()
                )
        ) {
            if (state.isLoading == true) {
                items(8) {
                    ShimmerComicItem()
                }
            } else if (!state.isLoading!! && state.comics?.isEmpty() == true) {
                item {
                    EmptyList(modifier = Modifier) {
                        viewModel.onEvent(ComicsHomeEvent.GetComics)
                    }
                }
            } else {
                items(viewModel.state.comics?.size ?: 0) { position ->
                    ComicItem(
                        comic = viewModel.state.comics!![position],
                        onClickNavigateToComicDetail = { comicId ->
                            viewModel.onEvent(
                                ComicsHomeEvent.SetComicFavorite(viewModel.state.comics!![position])
                            )
                            onNavigateComicDetail(comicId)
                        }
                    )
                }
            }
        }
    }

}
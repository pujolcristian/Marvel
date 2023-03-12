package com.example.marvel.presentation.screen.comics.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.marvel.R
import com.example.marvel.domain.model.Comic
import com.example.marvel.presentation.components.ComicInfoRowList
import com.example.marvel.presentation.components.ShimmerComicItem
import com.example.marvel.presentation.screen.comics.home.components.ComicItem
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComicDetailScreen(
    comicId: String,
    viewModel: ComicDetailViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val state = viewModel.state
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val context = LocalContext.current

    LaunchedEffect(comicId) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.getComicById(comicId)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.title_toolbar_comics_detail),
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
                navigationIcon = {
                    IconButton(onClick = {
                        onNavigateBack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { padding ->

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {

            val (
                comicAvatar,
                comicDescription,
                comicInfoCreators,
                comicInfoCharacters,
                comicInfoStories
            ) = createRefs()

            val topGuideline = createGuidelineFromTop(16.dp)
            val startGuideline = createGuidelineFromStart(24.dp)
            val endGuideline = createGuidelineFromEnd(24.dp)

            val comic = Comic(
                state.comicDetail.id,
                state.comicDetail.title,
                state.comicDetail.description,
                state.comicDetail.image,
                state.comicDetail.datePublished,
                state.comicDetail.rating
            )
            if (state.isLoading) {
                ShimmerComicItem(modifier = Modifier.constrainAs(comicAvatar) {
                    top.linkTo(topGuideline)
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                    width = Dimension.fillToConstraints
                })
            } else {
                ComicItem(
                    comic = comic,
                    onClickNavigateToComicDetail = {},
                    modifier = Modifier.constrainAs(comicAvatar) {
                        top.linkTo(topGuideline)
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        width = Dimension.fillToConstraints
                    }
                )
            }
            Text(
                text = state.comicDetail.description,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Justify,
                modifier = Modifier.constrainAs(comicDescription) {
                    top.linkTo(comicAvatar.bottom, margin = 12.dp)
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                    width = Dimension.fillToConstraints
                }
            )
            ComicInfoRowList(
                items = state.comicDetail.creators,
                modifier = Modifier.constrainAs(comicInfoCreators) {
                    top.linkTo(comicDescription.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                },
                title = if (state.comicDetail.creators.isNotEmpty()) {
                    stringResource(id = R.string.title_creator)
                } else {
                    ""
                }
            )
            ComicInfoRowList(
                items = state.comicDetail.characters,
                modifier = Modifier.constrainAs(comicInfoCharacters) {
                    top.linkTo(comicInfoCreators.bottom)
                    start.linkTo(parent.start)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                },
                title = if (state.comicDetail.characters.isNotEmpty()) {
                    stringResource(id = R.string.title_characters)
                } else {
                    ""
                }
            )
            ComicInfoRowList(
                items = state.comicDetail.stories,
                modifier = Modifier.constrainAs(comicInfoStories) {
                    top.linkTo(comicInfoCharacters.bottom)
                    start.linkTo(parent.start)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                },
                title = if (state.comicDetail.stories.isNotEmpty()) {
                    stringResource(id = R.string.title_stories)
                } else {
                    ""
                }
            )
        }
    }
}
package com.example.marvel.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.marvel.R
import com.example.marvel.domain.model.CharacterComic
import com.example.marvel.domain.model.CreatorComic
import com.example.marvel.domain.model.StoryComic

@Composable
fun ComicInfoRowList(
    modifier: Modifier = Modifier,
    items: List<Any> = emptyList(),
    title: String
) {

    ConstraintLayout(
        modifier = modifier
    ) {
        val (
            tittleListComic,
            listComics
        ) = createRefs()
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Start,
            modifier = Modifier.constrainAs(tittleListComic) {
                top.linkTo(parent.top)
                start.linkTo(parent.start, margin = 24.dp)
            }
        )
        LazyRow(
            state = rememberLazyListState(),
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.constrainAs(listComics) {
                top.linkTo(tittleListComic.bottom, margin = 5.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.wrapContent
            }
        ) {
            item{
                Spacer(modifier = Modifier.width(16.dp))
            }
            items(items) {
                when (it) {
                    is StoryComic -> {
                        ComicInfoRowItem(
                            title = it.name,
                            imageUrl = it.imageUrl ?: stringResource(id = R.string.not_image_url)
                        )
                    }
                    is CharacterComic -> {
                        ComicInfoRowItem(
                            title = it.name,
                            imageUrl = it.imageUrl
                        )
                    }
                    is CreatorComic -> {
                        ComicInfoRowItem(
                            title = it.name,
                            imageUrl = it.imageUrl
                        )
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}
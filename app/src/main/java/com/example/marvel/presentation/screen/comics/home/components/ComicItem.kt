package com.example.marvel.presentation.screen.comics.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import com.example.marvel.R
import com.example.marvel.domain.model.Comic
import com.example.marvel.presentation.screen.components.RatingBar
import java.util.*

@Composable
fun ComicItem(
    comic: Comic,
    onClickNavigateToComicDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(vertical = 8.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(bounded = true, color = Color.Black),
                onClick = {
                    onClickNavigateToComicDetail(comic.id)
                }
            ),
        shape = RoundedCornerShape(10.dp),
        elevation =  CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context)
                        .crossfade(true)
                        .data(comic.image)
                        .size(Size.ORIGINAL)
                        .error(R.drawable.ic_catching_marvel)
                        .placeholder(R.drawable.ic_catching_marvel)
                        .build(),
                    filterQuality = FilterQuality.High
                ),
                contentDescription = comic.title,
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(6.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(4.dp))
            Column(Modifier.fillMaxHeight().padding(top = 4.dp)) {
                Text(
                    text = comic.title.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                )
                Spacer(modifier = Modifier.height(4.dp))
                RatingBar(rating = comic.rating)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "U.S. PRICE: $15.99",
                    fontWeight = FontWeight.Normal,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

package com.pershin.pawpedia.feature.pawdetails.presentation.carousel

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pershin.pawpedia.ui.theme.PawpediaTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagesCarousel(
    items: List<String>,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier.fillMaxWidth()) {
        val screenWidth = LocalConfiguration.current.screenWidthDp

        val state = rememberPagerState(initialPage = 0, pageCount = { items.size })
        HorizontalPager(
            pageSize = PageSize.Fixed((screenWidth / 1.2).dp),
            pageSpacing = 16.dp,
            state = state,
            contentPadding = PaddingValues(16.dp)
        ) { currentPage ->
            ImageItem(
                item = items[currentPage],
                isInTheCenter = state.currentPage == currentPage,
                modifier = Modifier.clickable {
                    onItemClick()
                }
            )
        }
    }
}

@Composable
private fun ImageItem(item: String, modifier: Modifier, isInTheCenter: Boolean) {
    Column(modifier) {

        val aspectRation = if (isInTheCenter) 1f else 0.7f

        val animatedIconSize by animateDpAsState(
            targetValue = aspectRation.dp,
            animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
        )

        AsyncImage(
            model = item,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(animatedIconSize.value)
                .clip(RoundedCornerShape(8.dp)),
            contentDescription = null
        )

    }
}


@Preview
@Composable
private fun CarouselItemPreview() {
    PawpediaTheme {
        ImageItem(
            "https://lumiere-a.akamaihd.net/v1/images/p_moana2_v3_94b2f083.jpeg",
            modifier = Modifier.width(300.dp),
            isInTheCenter = true
        )
    }
}

@Preview
@Composable
private fun CarouselPreview() {
    PawpediaTheme {
        ImagesCarousel(
            onItemClick = {},
            items = listOf(
                "https://lumiere-a.akamaihd.net/v1/images/p_moana2_v3_94b2f083.jpeg",
                "https://lumiere-a.akamaihd.net/v1/images/p_moana2_v3_94b2f083.jpeg",
                "https://lumiere-a.akamaihd.net/v1/images/p_moana2_v3_94b2f083.jpeg"
            )
        )
    }
}

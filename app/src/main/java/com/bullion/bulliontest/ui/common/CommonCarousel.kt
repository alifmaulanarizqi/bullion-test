package com.bullion.bulliontest.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.bullion.bulliontest.domain.model.Banner
import com.bullion.bulliontest.theme.BlueAC
import com.bullion.bulliontest.theme.dimension10
import com.bullion.bulliontest.theme.dimension12
import com.bullion.bulliontest.theme.dimension24
import com.bullion.bulliontest.theme.dimension4
import com.bullion.bulliontest.theme.dimension8

@Composable
fun CommonCarousel(
    banners: List<Banner>,
    modifier: Modifier = Modifier,
    aspectRatio: Float = 16f / 9f,
    cornerRadius: Dp = dimension12,
    pageSpacing: Dp = dimension12,
    horizontalPadding: Dp = dimension24,
) {
    if (banners.isEmpty()) return

    val pagerState = rememberPagerState(pageCount = { banners.size })

    Column(
        modifier = modifier
    ) {
        HorizontalPager(
            state = pagerState,
            pageSpacing = pageSpacing,
            contentPadding = PaddingValues(horizontal = horizontalPadding),
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            Image(
                painter = painterResource(id = banners[page].resId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(aspectRatio)
                    .clip(RoundedCornerShape(cornerRadius))
            )
        }
        Spacer(modifier = Modifier.height(dimension10))
        PagerIndicator(
            count = banners.size,
            currentIndex = pagerState.currentPage,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dimension4)
        )
    }
}

@Composable
fun PagerIndicator(
    count: Int,
    currentIndex: Int,
    modifier: Modifier = Modifier,
    selectedSize: Dp = dimension10,
    normalSize: Dp = dimension8,
    spacing: Dp = dimension4,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(count) { index ->
            val selected = index == currentIndex
            Box(
                modifier = Modifier
                    .padding(horizontal = spacing)
                    .size(if (selected) selectedSize else normalSize)
                    .clip(CircleShape)
                    .background(
                        if (selected) BlueAC
                        else BlueAC.copy(alpha = 0.5f)
                    )
            )
        }
    }
}
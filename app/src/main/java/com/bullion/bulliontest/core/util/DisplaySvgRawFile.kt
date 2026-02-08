package com.bullion.bulliontest.core.util

import android.content.Context
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.bullion.bulliontest.theme.dimension30
import coil3.svg.SvgDecoder

@Composable
fun DisplaySvgRawFile(
    model: Any,
    contentDescription: String? = null,
    size: Dp = dimension30,
    colorFilter: ColorFilter? = null
) {
    val context = LocalContext.current
    val imageLoader = remember(context) { provideSvgImageLoader(context) }

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(model)
            .build(),
        imageLoader = imageLoader,
        contentDescription = contentDescription,
        modifier = Modifier.size(size),
        colorFilter = colorFilter
    )
}

fun provideSvgImageLoader(context: Context): ImageLoader {
    return ImageLoader.Builder(context)
        .components { add(SvgDecoder.Factory()) }
        .build()
}
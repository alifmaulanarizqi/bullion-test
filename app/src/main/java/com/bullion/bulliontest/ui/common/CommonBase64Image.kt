package com.bullion.bulliontest.ui.common

import android.util.Base64
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage

@Composable
fun CommonBase64Image(
    base64: String,
    modifier: Modifier = Modifier
) {
    val bytes = decodeBase64ToBytes(base64)

    AsyncImage(
        model = bytes,
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}

fun decodeBase64ToBytes(value: String): ByteArray {
    val clean = value
        .trim()
        .removePrefix("data:image/jpeg;base64,")
        .removePrefix("data:image/jpg;base64,")
        .removePrefix("data:image/png;base64,")
        .removePrefix("data:image/webp;base64,")

    return Base64.decode(clean, Base64.DEFAULT)
}
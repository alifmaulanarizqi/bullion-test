package com.bullion.bulliontest.data.remote.request

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.BufferedSink
import okio.source

fun String.toTextRequestBody(): RequestBody =
    this.toRequestBody("text/plain".toMediaType())

fun uriToMultipartPart(
    context: Context,
    uri: Uri,
    formName: String = "photo"
): MultipartBody.Part {
    val contentResolver = context.contentResolver
    val mimeType = contentResolver.getType(uri) ?: "image/*"

    val fileName = contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        cursor.moveToFirst()
        if (nameIndex >= 0) cursor.getString(nameIndex) else "photo.jpg"
    } ?: "photo.jpg"

    val requestBody = object : RequestBody() {
        override fun contentType() = mimeType.toMediaTypeOrNull()

        override fun writeTo(sink: BufferedSink) {
            contentResolver.openInputStream(uri)?.use { input ->
                sink.writeAll(input.source())
            } ?: error("Cannot open InputStream for uri: $uri")
        }
    }

    return MultipartBody.Part.createFormData(formName, fileName, requestBody)
}
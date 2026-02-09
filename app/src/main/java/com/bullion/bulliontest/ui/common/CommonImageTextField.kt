package com.bullion.bulliontest.ui.common

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AttachFile
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bullion.bulliontest.theme.AppTypography
import com.bullion.bulliontest.theme.Blue92
import com.bullion.bulliontest.theme.Brown1A
import com.bullion.bulliontest.theme.GrayB2
import com.bullion.bulliontest.theme.GrayD6
import com.bullion.bulliontest.theme.Orange2A
import com.bullion.bulliontest.theme.dimension28

@Composable
fun CommonImageTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isError: Boolean = false,
    errorText: String? = null,
    onImagePicked: (Uri) -> Unit,
    modifier: Modifier = Modifier,
) {
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            onImagePicked(uri)
            onValueChange("Image selected")
        }
    }

    val openPicker = { imagePickerLauncher.launch("image/*") }

    OutlinedTextField(
        value = value,
        onValueChange = {},
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(55.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { openPicker() },
        readOnly = true,
        singleLine = true,
        isError = isError,
        supportingText = {
            if(isError) {
                Text(
                    text = errorText ?: "",
                    style = AppTypography.labelSmall.copy(
                        color = Color.Red
                    )
                )
            }
        },
        shape = RoundedCornerShape(dimension28),
        placeholder = { Text(placeholder, color = GrayB2) },
        trailingIcon = {
            IconButton(
                onClick = {
                    openPicker()
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.AttachFile,
                    contentDescription = "Pick image",
                    tint = Blue92
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Brown1A,
            unfocusedBorderColor = GrayD6,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            cursorColor = Orange2A
        )
    )
}
package com.bullion.bulliontest.ui.common

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.bullion.bulliontest.theme.AppTypography
import com.bullion.bulliontest.theme.Blue92
import com.bullion.bulliontest.theme.Brown1A
import com.bullion.bulliontest.theme.GrayB2
import com.bullion.bulliontest.theme.GrayD6
import com.bullion.bulliontest.theme.Orange2A
import com.bullion.bulliontest.theme.dimension28

@Composable
fun CommonPasswordTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "Placeholder here",
    passwordVisible: Boolean = false,
    isError: Boolean = false,
    errorText: String? = null,
    onClickEyeIcon: () -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 55.dp),
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
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(
                onClick = onClickEyeIcon
            ) {
                Icon(
                    imageVector = if (passwordVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
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
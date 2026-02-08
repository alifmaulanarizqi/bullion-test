package com.bullion.bulliontest.ui.common

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.bullion.bulliontest.theme.AppTypography
import com.bullion.bulliontest.theme.Brown1A
import com.bullion.bulliontest.theme.GrayB2
import com.bullion.bulliontest.theme.GrayD6
import com.bullion.bulliontest.theme.Orange2A
import com.bullion.bulliontest.theme.dimension28

@Composable
fun CommonTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType,
    isPassword: Boolean = false,
    isError: Boolean = false,
    errorText: String? = null,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
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
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Brown1A,
            unfocusedBorderColor = GrayD6,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            cursorColor = Orange2A
        )
    )
}
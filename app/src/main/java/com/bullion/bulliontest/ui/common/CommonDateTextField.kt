package com.bullion.bulliontest.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bullion.bulliontest.core.util.DateUtil.formatDate
import com.bullion.bulliontest.theme.AppTypography
import com.bullion.bulliontest.theme.Black1E
import com.bullion.bulliontest.theme.Brown1A
import com.bullion.bulliontest.theme.GrayB2
import com.bullion.bulliontest.theme.GrayD6
import com.bullion.bulliontest.theme.Orange2A
import com.bullion.bulliontest.theme.dimension28

@Composable
fun CommonDateTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isError: Boolean = false,
    errorText: String? = null,
    onClick: (showDialog: Boolean) -> Unit,
    showDialog: Boolean = false,
) {

    OutlinedTextField(
        value = value,
        onValueChange = {},
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(55.dp)
            .clickable {
                onClick(true)
              },
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
                    onClick(true)
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.CalendarMonth,
                    contentDescription = "Pick date",
                    tint = Black1E
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

    if (showDialog) {
        val datePickerState = rememberDatePickerState()

        DatePickerDialog(
            onDismissRequest = { onClick(false) },
            confirmButton = {
                TextButton(
                    onClick = {
                        val selected = datePickerState.selectedDateMillis
                        if (selected != null) {
                            onValueChange(formatDate(selected))
                        }
                        onClick(false)
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { onClick(false) }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
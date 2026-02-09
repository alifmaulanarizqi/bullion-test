package com.bullion.bulliontest.ui.feature.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.bullion.bulliontest.domain.model.User
import com.bullion.bulliontest.domain.model.UserDetail
import com.bullion.bulliontest.theme.AppTypography
import com.bullion.bulliontest.theme.Blue92
import com.bullion.bulliontest.theme.Gray5D
import com.bullion.bulliontest.theme.Gray7D
import com.bullion.bulliontest.theme.White
import com.bullion.bulliontest.theme.dimension10
import com.bullion.bulliontest.theme.dimension12
import com.bullion.bulliontest.theme.dimension16
import com.bullion.bulliontest.theme.dimension2
import com.bullion.bulliontest.theme.dimension24
import com.bullion.bulliontest.theme.dimension32
import com.bullion.bulliontest.theme.dimension6
import com.bullion.bulliontest.ui.common.CommonBase64Image
import com.bullion.bulliontest.ui.common.CommonFilledButton

@Composable
fun DetailUserDialog(
    user: UserDetail,
    onDismiss: () -> Unit,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(onDismissRequest = onDismiss) {
        // Overlay center card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimension16),
            contentAlignment = Alignment.Center
        ) {
                Surface(
                    modifier = modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(dimension16),
                    color = Color.White,
                    tonalElevation = 0.dp,
                    shadowElevation = dimension10
                ) {
                    Column(
                        modifier = Modifier
                            .padding(dimension16)
                            .verticalScroll(rememberScrollState())
                    ) {
                        // Close button (top right)
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            IconButton(
                                onClick = onDismiss,
                                modifier = Modifier
                                    .size(dimension16)
                                    .align(Alignment.TopEnd)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close",
                                    tint = Gray7D
                                )
                            }
                        }
                        Spacer(Modifier.height(dimension12))
                        // Avatar
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(RoundedCornerShape(dimension6)),
                            contentAlignment = Alignment.Center
                        ) {
                            CommonBase64Image(
                                base64 = user.photo,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                        Spacer(Modifier.height(dimension10))
                        // Name & email
                        Text(
                            text = "${user.firstName} ${user.lastName}",
                            style = AppTypography.labelSmall.copy(
                                fontWeight = FontWeight.W500,
                            )
                        )
                        Spacer(Modifier.height(dimension2))
                        Text(
                            text = user.email,
                            style = AppTypography.labelSmall.copy(
                                fontWeight = FontWeight.W500,
                                color = Gray5D
                            )
                        )
                        Spacer(Modifier.height(dimension10))
                        // Fields
                        DetailRow(label = "Gender", value = user.gender.uppercase())
                        Spacer(Modifier.height(dimension10))
                        DetailRow(label = "Phone Number", value = user.phone)
                        Spacer(Modifier.height(dimension10))
                        DetailRow(label = "Date of Birth", value = user.dateOfBirth)
                        Spacer(Modifier.height(dimension10))
                        DetailRow(label = "Address", value = user.address)
                        Spacer(Modifier.height(dimension10))
                        // Button
                        CommonFilledButton(
                            text = "Edit User",
                            onClick = onEdit,
                            buttonColor = Blue92,
                            textStyle = AppTypography.labelSmall.copy(
                                fontWeight = FontWeight.W500,
                                color = White
                            ),
                        )
                    }
                }
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
) {
    Column {
        Text(
            text = label,
            style = AppTypography.labelSmall.copy(
                fontSize = 10.sp,
                color = Gray5D,
                fontWeight = FontWeight.W500,
            )
        )
        Spacer(Modifier.height(dimension2))
        Text(
            text = value,
            style = AppTypography.labelSmall.copy(
                fontWeight = FontWeight.W500,
            )
        )
    }
}
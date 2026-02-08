package com.bullion.bulliontest.ui.feature.login

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bullion.bulliontest.R
import com.bullion.bulliontest.core.util.DisplaySvgRawFile
import com.bullion.bulliontest.theme.AppTypography
import com.bullion.bulliontest.theme.GradientBackground
import com.bullion.bulliontest.theme.GradientText
import com.bullion.bulliontest.theme.White
import com.bullion.bulliontest.theme.dimension16
import com.bullion.bulliontest.theme.dimension24
import com.bullion.bulliontest.theme.dimension28
import com.bullion.bulliontest.theme.dimension32
import com.bullion.bulliontest.theme.dimension8
import com.bullion.bulliontest.ui.common.CommonFilledButton
import com.bullion.bulliontest.ui.common.CommonTextField

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToRegister: () -> Unit,
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.event.collect { ev ->
            when (ev) {
                is LoginEvent.ShowError -> {
                    snackBarHostState.showSnackbar(ev.message)
                }
                LoginEvent.Success -> {
//                    onSuccessNavigate()
                }
            }
        }
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .background(GradientBackground)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimension24)
                    .weight(0.7f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                DisplaySvgRawFile(
                    model = R.raw.bullion_logo,
                    contentDescription = "Bullion Logo",
                    height = 130.dp,
                    width = 130.dp
                )
            }
            Surface(
                shape = RoundedCornerShape(topStart = dimension28, topEnd = dimension28),
                color = Color.White,
                shadowElevation = dimension8,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                LoginFormCard(
                    email = state.email,
                    password = state.password,
                    onEmailChange = viewModel::onEmailChange,
                    onPasswordChange = viewModel::onPasswordChange,
                    emailErrorText = state.emailError,
                    passwordErrorText = state.passwordError,
                    onSignIn = { /* TODO */ },
                    onAddNewUsers = {
                        onNavigateToRegister()
                    },
                )
            }
        }
    }
}

@Composable
fun LoginFormCard(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    emailErrorText: String? = null,
    passwordErrorText: String? = null,
    onSignIn: () -> Unit,
    onAddNewUsers: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimension24, vertical = dimension32),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = "Email Address",
                style = AppTypography.bodySmall.copy(
                    fontWeight = FontWeight.W500,
                    brush = GradientText
                )
            )
            Spacer(Modifier.height(dimension8))
            CommonTextField(
                value = email,
                onValueChange = onEmailChange,
                placeholder = "Enter your email",
                keyboardType = KeyboardType.Email,
                isError = emailErrorText != null,
                errorText = emailErrorText
            )
            Spacer(Modifier.height(dimension16))
            Text(
                text = "Password",
                style = AppTypography.bodySmall.copy(
                    fontWeight = FontWeight.W500,
                    brush = GradientText
                )
            )
            Spacer(Modifier.height(dimension8))
            CommonTextField(
                value = password,
                onValueChange = onPasswordChange,
                placeholder = "Enter your password",
                keyboardType = KeyboardType.Password,
                isPassword = true,
                isError = passwordErrorText != null,
                errorText = passwordErrorText
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(dimension16),
        ) {
            CommonFilledButton(
                onClick = onSignIn,
                text = "Sign In",
                textStyle = AppTypography.labelSmall.copy(
                    color = White,
                    fontWeight = FontWeight.W500
                )
            )
            CommonFilledButton(
                onClick = onAddNewUsers,
                text = "Add new Users",
                textStyle = AppTypography.labelSmall.copy(
                    color = White,
                    fontWeight = FontWeight.W500
                )
            )
        }
    }
}
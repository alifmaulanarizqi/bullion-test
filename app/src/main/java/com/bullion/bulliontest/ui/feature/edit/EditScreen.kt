package com.bullion.bulliontest.ui.feature.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bullion.bulliontest.R
import com.bullion.bulliontest.core.util.DisplaySvgRawFile
import com.bullion.bulliontest.core.util.GenderEnum
import com.bullion.bulliontest.theme.AppTypography
import com.bullion.bulliontest.theme.Black
import com.bullion.bulliontest.theme.Black03
import com.bullion.bulliontest.theme.GradientBackground
import com.bullion.bulliontest.theme.GradientText
import com.bullion.bulliontest.theme.Gray93
import com.bullion.bulliontest.theme.Orange2A
import com.bullion.bulliontest.theme.White
import com.bullion.bulliontest.theme.dimension1
import com.bullion.bulliontest.theme.dimension16
import com.bullion.bulliontest.theme.dimension20
import com.bullion.bulliontest.theme.dimension24
import com.bullion.bulliontest.theme.dimension28
import com.bullion.bulliontest.theme.dimension32
import com.bullion.bulliontest.theme.dimension8
import com.bullion.bulliontest.domain.model.UserDetail
import com.bullion.bulliontest.ui.common.CommonDateTextField
import com.bullion.bulliontest.ui.common.CommonFilledButton
import com.bullion.bulliontest.ui.common.CommonLoading
import com.bullion.bulliontest.ui.common.CommonTextField

@Composable
fun EditScreen(
    userDetail: UserDetail,
    viewModel: EditViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onBackWithRefresh: () -> Unit,
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val snackBarHostState = remember { SnackbarHostState() }

    // Initialize form with user data
    LaunchedEffect(userDetail) {
        viewModel.initializeUserData(userDetail)
    }

    LaunchedEffect(Unit) {
        viewModel.event.collect { ev ->
            when (ev) {
                is EditEvent.ShowError -> {
                    snackBarHostState.showSnackbar(ev.message)
                }
                is EditEvent.Success -> {
                    onBackWithRefresh()
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = White,
                    contentColor = Black03,
                    actionColor = Gray93
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .background(GradientBackground)
        ) {
            Spacer(modifier = Modifier.height(dimension32))
            AppBarSection(
                onBack = onBack
            )
            Spacer(modifier = Modifier.height(dimension32))
            Surface(
                shape = RoundedCornerShape(topStart = dimension28, topEnd = dimension28),
                color = Color.White,
                shadowElevation = dimension8,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                RegisterFormCard(
                    state = state,
                    viewModel = viewModel
                )
            }
        }

        // Loading Overlay
        CommonLoading(
            isLoading = state.isLoading
        )
    }
}

@Composable
private fun RegisterFormCard(
    state: EditState,
    viewModel: EditViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = dimension24),
    ) {
        Spacer(modifier = Modifier.height(dimension32))
        TextFieldWithLabel(
            value = state.firstName,
            onValueChange = viewModel::onFirstNameChange,
            valueErrorText = state.firstNameError,
            isError = state.firstNameError != null,
            placeholder = "Enter your first name",
            label = "First Name",
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(dimension16))
        TextFieldWithLabel(
            value = state.lastName,
            onValueChange = viewModel::onLastNameChange,
            valueErrorText = state.lastNameError,
            isError = state.lastNameError != null,
            placeholder = "Enter your last name",
            label = "Last Name",
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(dimension16))
        Text(
            text = "Gender",
            style = AppTypography.bodySmall.copy(
                fontWeight = FontWeight.W500,
                brush = GradientText
            )
        )
        Spacer(modifier = Modifier.height(dimension8))
        GenderRadioBoxRow(
            selected = state.gender,
            onSelected = viewModel::onGenderChange
        )
        Spacer(modifier = Modifier.height(dimension16))
        Text(
            text = "Date of Birth",
            style = AppTypography.bodySmall.copy(
                fontWeight = FontWeight.W500,
                brush = GradientText
            )
        )
        Spacer(modifier = Modifier.height(dimension8))
        CommonDateTextField(
            value = state.dateOfBirth,
            onValueChange = viewModel::onDateOfBirthChange,
            placeholder = "Enter your date of birth",
            isError = state.dateOfBirthError != null,
            errorText = state.dateOfBirthError,
            showDialog = state.showDialogDate,
            onClick = viewModel::onDateOfBirthClick
        )
        Spacer(modifier = Modifier.height(dimension16))
        TextFieldWithLabel(
            value = state.email,
            onValueChange = viewModel::onEmailChange,
            valueErrorText = state.emailError,
            isError = state.emailError != null,
            placeholder = "Enter your email",
            label = "Email Address",
            keyboardType = KeyboardType.Email
        )
        Spacer(modifier = Modifier.height(dimension16))
        TextFieldWithLabel(
            value = state.phoneNumber,
            onValueChange = viewModel::onPhoneNumberChange,
            valueErrorText = state.phoneNumberError,
            isError = state.phoneNumberError != null,
            placeholder = "Enter your phone number",
            label = "Phone Number",
            keyboardType = KeyboardType.Phone
        )
        Spacer(modifier = Modifier.height(dimension16))
        TextFieldWithLabel(
            value = state.address,
            onValueChange = viewModel::onAddressChange,
            valueErrorText = state.addressError,
            isError = state.addressError != null,
            placeholder = "Enter your address",
            label = "Address",
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(dimension24))
        CommonFilledButton(
            onClick = viewModel::submit,
            text = "Update User",
            textStyle = AppTypography.labelSmall.copy(
                color = White,
                fontWeight = FontWeight.W500
            )
        )
        Spacer(modifier = Modifier.height(dimension24))
    }
}

@Composable
private fun AppBarSection(
    onBack: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimension24),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                onBack()
            },
            modifier = Modifier
                .size(dimension32)
        ) {
            Icon(
                modifier = Modifier.size(dimension32),
                imageVector = Icons.Default.ChevronLeft,
                contentDescription = "Back Icon",
                tint = White
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            DisplaySvgRawFile(
                model = R.raw.bullion_logo,
                contentDescription = "Bullion Logo",
                width = 120.dp,
                height = 60.dp
            )
        }
        Box(
            modifier = Modifier.size(dimension32)
        )
    }
}

@Composable
private fun TextFieldWithLabel(
    value: String,
    onValueChange: (String) -> Unit,
    valueErrorText: String? = null,
    isError: Boolean,
    placeholder: String,
    label: String,
    keyboardType: KeyboardType
) {
    Column {
        Text(
            text = label,
            style = AppTypography.bodySmall.copy(
                fontWeight = FontWeight.W500,
                brush = GradientText
            )
        )
        Spacer(Modifier.height(dimension8))
        CommonTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = placeholder,
            keyboardType = keyboardType,
            isError = isError,
            errorText = valueErrorText
        )
    }
}

@Composable
private fun GenderRadioBoxRow(
    selected: GenderEnum?,
    onSelected: (GenderEnum) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.selectableGroup(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioBoxOption(
            text = "Male",
            selected = selected == GenderEnum.Male,
            onClick = { onSelected(GenderEnum.Male) }
        )
        Spacer(Modifier.width(18.dp))
        RadioBoxOption(
            text = "Female",
            selected = selected == GenderEnum.Female,
            onClick = { onSelected(GenderEnum.Female) }
        )
    }
}

@Composable
private fun RadioBoxOption(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.RadioButton
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(dimension20)
                .border(
                    width = dimension1,
                    color = if(selected) Orange2A else Gray93,
                    shape = RoundedCornerShape(3.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (selected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Orange2A,
                    modifier = Modifier.size(14.dp)
                )
            }
        }

        Spacer(Modifier.width(10.dp))

        Text(
            text = text,
            style = AppTypography.bodySmall.copy(
                color = Black
            )
        )
    }
}
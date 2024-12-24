package com.thejohnsondev.presentation.additem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.thejohnsondev.common.empty
import com.thejohnsondev.model.OneTimeEvent
import com.thejohnsondev.presentation.component.AdditionalFieldItem
import com.thejohnsondev.ui.components.BackArrowButton
import com.thejohnsondev.ui.components.HintTextField
import com.thejohnsondev.ui.components.LoadedImage
import com.thejohnsondev.ui.components.RoundedButton
import com.thejohnsondev.ui.components.RoundedContainer
import com.thejohnsondev.ui.designsystem.EqualRounded
import com.thejohnsondev.ui.designsystem.Percent90
import com.thejohnsondev.ui.designsystem.Size12
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size48
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.designsystem.Text20
import com.thejohnsondev.ui.designsystem.Text22
import com.thejohnsondev.ui.model.ButtonShape
import com.thejohnsondev.ui.model.PasswordUIModel
import com.thejohnsondev.ui.utils.KeyboardManager
import com.thejohnsondev.ui.utils.applyIf
import com.thejohnsondev.ui.utils.bounceClick
import com.thejohnsondev.ui.utils.isCompact
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import vaultmultiplatform.feature.vault.presentation.generated.resources.Res
import vaultmultiplatform.feature.vault.presentation.generated.resources.add_field
import vaultmultiplatform.feature.vault.presentation.generated.resources.ic_password
import vaultmultiplatform.feature.vault.presentation.generated.resources.organization
import vaultmultiplatform.feature.vault.presentation.generated.resources.password
import vaultmultiplatform.feature.vault.presentation.generated.resources.save
import vaultmultiplatform.feature.vault.presentation.generated.resources.title
import vaultmultiplatform.feature.vault.presentation.generated.resources.visibility

@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
fun AddVaultItemScreen(
    windowSizeClass: WindowWidthSizeClass,
    paddingValues: PaddingValues,
    sheetState: SheetState,
    viewModel: AddVaultItemViewModel = koinViewModel<AddVaultItemViewModel>(),
    vaultItem: PasswordUIModel? = null,
    onDismissRequest: () -> Unit,
    showErrorMessage: (String) -> Unit,
) {
    val state = viewModel.state.collectAsState(AddVaultItemViewModel.State())

    LaunchedEffect(true) {
        viewModel.getEventFlow().collect {
            when (it) {
                is OneTimeEvent.InfoMessage -> showErrorMessage(it.message)
                is OneTimeEvent.SuccessNavigation -> {
                    viewModel.clear()
                    onDismissRequest()
                }
            }
        }
    }

    AddVaultItemContent(
        sheetState = sheetState,
        state = state.value,
        windowSizeClass = windowSizeClass,
        paddingValues = paddingValues,
        vaultItem = vaultItem,
        onDismissRequest = onDismissRequest,
        onAction = viewModel::perform
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddVaultItemContent(
    sheetState: SheetState,
    state: AddVaultItemViewModel.State,
    windowSizeClass: WindowWidthSizeClass,
    paddingValues: PaddingValues,
    vaultItem: PasswordUIModel?,
    onDismissRequest: () -> Unit,
    onAction: (AddVaultItemViewModel.Action) -> Unit,
) {
    ModalBottomSheet(
        modifier = Modifier
            .applyIf(windowSizeClass.isCompact()) {
                systemBarsPadding()
            }.applyIf(!windowSizeClass.isCompact()) {
                padding(top = paddingValues.calculateTopPadding())
            },
        sheetState = sheetState,
        onDismissRequest = {
            onAction(AddVaultItemViewModel.Action.Clear)
            onDismissRequest()
        },
        properties = ModalBottomSheetProperties(
            shouldDismissOnBackPress = false
        ),
        dragHandle = {
            ModalDragHandle(onAction, onDismissRequest, state)
        },
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        AddPasswordFields(
            state = state,
                vaultItem = vaultItem,
            onAction = onAction
        )
    }
}

@Composable
private fun ModalDragHandle(
    onAction: (AddVaultItemViewModel.Action) -> Unit,
    onDismissRequest: () -> Unit,
    state: AddVaultItemViewModel.State
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Size16),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BackArrowButton(
            modifier = Modifier.padding(start = Size16),
            onClick = {
                onAction(AddVaultItemViewModel.Action.Clear)
                onDismissRequest()
            }
        )
        Button(
            modifier = Modifier
                .padding(end = Size16)
                .bounceClick(),
            onClick = {
                onAction(AddVaultItemViewModel.Action.SavePassword)
            },
            enabled = state.isValid,
        ) {
            Text(text = stringResource(Res.string.save))
        }
    }
}

@Composable
internal fun AddPasswordFields(
    state: AddVaultItemViewModel.State,
    vaultItem: PasswordUIModel?,
    onAction: (AddVaultItemViewModel.Action) -> Unit,
) {
    val organizationFocusRequester = remember {
        FocusRequester()
    }
    val titleFocusRequester = remember {
        FocusRequester()
    }
    val passwordFocusRequester = remember {
        FocusRequester()
    }
    val keyboardController = KeyboardManager.getKeyboardController()
    val isPasswordHidden = remember {
        mutableStateOf(false)
    }
    val eyeImage = if (isPasswordHidden.value) Icons.Filled.VisibilityOff else Icons.Filled.Visibility

    LaunchedEffect(true) {
        if (vaultItem != null) {
            onAction(AddVaultItemViewModel.Action.SetPasswordForEdit(vaultItem))
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
        ) {
            OrganizationField(
                onAction = onAction,
                state = state,
                organizationFocusRequester = organizationFocusRequester,
                titleFocusRequester = titleFocusRequester
            )
            TitleField(
                onAction = onAction,
                state = state,
                titleFocusRequester = titleFocusRequester,
                passwordFocusRequester = passwordFocusRequester
            )
            PasswordField(
                onAction = onAction,
                state = state,
                passwordFocusRequester = passwordFocusRequester,
                keyboardController = keyboardController,
                isPasswordHidden = isPasswordHidden,
                eyeImage = eyeImage
            )
            Spacer(modifier = Modifier.size(Size16))
            AdditionalFieldsList(state = state, onAction = onAction)
            RoundedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Size16)
                    .bounceClick(),
                text = stringResource(Res.string.add_field),
                onClick = {
                    onAction(AddVaultItemViewModel.Action.AddAdditionalField)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                buttonShape = ButtonShape.ROUNDED
            )
        }
    }

}

@Composable
private fun AdditionalFieldsList(
    state: AddVaultItemViewModel.State,
    onAction: (AddVaultItemViewModel.Action) -> Unit
) {
    state.additionalFields.forEachIndexed { index, additionalField ->
        AdditionalFieldItem(
            modifier = Modifier
                .padding(start = Size16, end = Size16, top = Size8),
            title = additionalField.title,
            value = additionalField.value,
            onTitleChanged = { title ->
                onAction(
                    AddVaultItemViewModel.Action.EnterAdditionalFieldTitle(
                        id = additionalField.id,
                        title
                    )
                )
            },
            onValueChanged = { value ->
                onAction(
                    AddVaultItemViewModel.Action.EnterAdditionalFieldValue(
                        additionalField.id,
                        value
                    )
                )
            }, onDeleteClick = {
                onAction(
                    AddVaultItemViewModel.Action.RemoveAdditionalField(
                        additionalField.id
                    )
                )
            },
            isEditMode = state.isEdit)
    }
}

@Composable
private fun PasswordField(
    onAction: (AddVaultItemViewModel.Action) -> Unit,
    state: AddVaultItemViewModel.State,
    passwordFocusRequester: FocusRequester,
    keyboardController: SoftwareKeyboardController?,
    isPasswordHidden: MutableState<Boolean>,
    eyeImage: ImageVector
) {
    RoundedContainer(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = Size16, end = Size16, top = Size8),
        color = MaterialTheme.colorScheme.surfaceVariant,
        isLastItem = true,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HintTextField(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(Percent90)
                    .padding(horizontal = Size12, vertical = Size16),
                onValueChanged = { password ->
                    onAction(AddVaultItemViewModel.Action.EnterPassword(password))
                },
                hint = stringResource(Res.string.password),
                value = state.password,
                focusRequester = passwordFocusRequester,
                textColor = MaterialTheme.colorScheme.onSurface,
                fontSize = Text20,
                maxLines = 2,
                onKeyboardAction = {
                    keyboardController?.hide()
                },
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password,
                passwordVisible = !isPasswordHidden.value
            )
            IconButton(
                modifier = Modifier.padding(end = Size8),
                onClick = {
                    isPasswordHidden.value = !isPasswordHidden.value
                }) {
                Icon(
                    imageVector = eyeImage,
                    contentDescription = stringResource(Res.string.visibility),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun TitleField(
    onAction: (AddVaultItemViewModel.Action) -> Unit,
    state: AddVaultItemViewModel.State,
    titleFocusRequester: FocusRequester,
    passwordFocusRequester: FocusRequester
) {
    RoundedContainer(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = Size16, end = Size16),
        color = MaterialTheme.colorScheme.surfaceVariant,
        isFirstItem = true,
    ) {
        HintTextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = Size12, vertical = Size16),
            onValueChanged = { title ->
                onAction(AddVaultItemViewModel.Action.EnterTitle(title))
            },
            value = state.title,
            hint = stringResource(Res.string.title),
            focusRequester = titleFocusRequester,
            textColor = MaterialTheme.colorScheme.onSurface,
            fontSize = Text20,
            maxLines = 2,
            onKeyboardAction = {
                passwordFocusRequester.requestFocus()
            },
            imeAction = ImeAction.Next
        )

    }
}

@Composable
private fun OrganizationField(
    onAction: (AddVaultItemViewModel.Action) -> Unit,
    state: AddVaultItemViewModel.State,
    organizationFocusRequester: FocusRequester,
    titleFocusRequester: FocusRequester
) {
    Row(
        modifier = Modifier.padding(start = Size16, end = Size16, bottom = Size16),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(Size48),
            color = Color.White,
            shape = EqualRounded.small
        ) {
            LoadedImage(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Size4),
                imageUrl = String.empty,
                placeholderDrawableResource = Res.drawable.ic_password,
                errorDrawableResource = Res.drawable.ic_password,
                placeholderDrawableTintColor = MaterialTheme.colorScheme.inversePrimary,
                backgroundColor = Color.White
            )
        }
        HintTextField(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(start = Size16),
            onValueChanged = { organization ->
                onAction(AddVaultItemViewModel.Action.EnterOrganization(organization))
            },
            value = state.organization,
            focusRequester = organizationFocusRequester,
            hint = stringResource(Res.string.organization),
            textColor = MaterialTheme.colorScheme.onSurface,
            fontSize = Text22,
            maxLines = 2,
            onKeyboardAction = {
                titleFocusRequester.requestFocus()
            },
            imeAction = ImeAction.Next
        )
    }
}
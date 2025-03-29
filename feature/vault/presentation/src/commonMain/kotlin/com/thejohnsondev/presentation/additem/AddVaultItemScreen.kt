package com.thejohnsondev.presentation.additem

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.thejohnsondev.common.SCROLL_DOWN_DELAY
import com.thejohnsondev.common.empty
import com.thejohnsondev.model.OneTimeEvent
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.model.vault.AdditionalFieldDto
import com.thejohnsondev.presentation.component.AdditionalFieldItem
import com.thejohnsondev.presentation.component.CategorySelectorItem
import com.thejohnsondev.ui.components.BackArrowButton
import com.thejohnsondev.ui.components.HintTextField
import com.thejohnsondev.ui.components.LoadedImage
import com.thejohnsondev.ui.components.Loader
import com.thejohnsondev.ui.components.RoundedButton
import com.thejohnsondev.ui.components.RoundedContainer
import com.thejohnsondev.ui.designsystem.EqualRounded
import com.thejohnsondev.ui.designsystem.Percent90
import com.thejohnsondev.ui.designsystem.Size12
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size22
import com.thejohnsondev.ui.designsystem.Size24
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size48
import com.thejohnsondev.ui.designsystem.Size56
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.designsystem.Text20
import com.thejohnsondev.ui.designsystem.Text22
import com.thejohnsondev.ui.displaymessage.getAsText
import com.thejohnsondev.ui.model.PasswordUIModel
import com.thejohnsondev.ui.model.button.ButtonShape
import com.thejohnsondev.ui.utils.KeyboardManager
import com.thejohnsondev.ui.utils.ResDrawable
import com.thejohnsondev.ui.utils.ResString
import com.thejohnsondev.ui.utils.applyIf
import com.thejohnsondev.ui.utils.bounceClick
import com.thejohnsondev.ui.utils.isCompact
import com.thejohnsondev.ui.utils.onEnterClick
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import vaultmultiplatform.core.ui.generated.resources.add_field
import vaultmultiplatform.core.ui.generated.resources.ic_password
import vaultmultiplatform.core.ui.generated.resources.password
import vaultmultiplatform.core.ui.generated.resources.save
import vaultmultiplatform.core.ui.generated.resources.title
import vaultmultiplatform.core.ui.generated.resources.update
import vaultmultiplatform.core.ui.generated.resources.username
import vaultmultiplatform.core.ui.generated.resources.visibility

private const val DELAY_BEFORE_FOCUS = 500L

@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
internal fun AddVaultItemScreen(
    windowSizeClass: WindowWidthSizeClass,
    paddingValues: PaddingValues,
    sheetState: SheetState,
    viewModel: AddVaultItemViewModel = koinViewModel<AddVaultItemViewModel>(),
    vaultItem: PasswordUIModel? = null,
    onDismissRequest: () -> Unit,
    showSuccessMessage: (String) -> Unit,
) {
    val state = viewModel.state.collectAsState(AddVaultItemViewModel.State())

    LaunchedEffect(true) {
        viewModel.getEventFlow().collect {
            when (it) {
                is OneTimeEvent.SuccessNavigation -> {
                    it.message?.getAsText()?.let(showSuccessMessage)
                    viewModel.clear()
                    onDismissRequest()
                }
            }
        }
    }

    AddVaultItemContent(
        sheetState = sheetState,
        state = state.value,
        enteredTitle = viewModel.enteredTitle,
        enteredUserName = viewModel.enteredUserName,
        enteredPassword = viewModel.enteredPassword,
        additionalFields = viewModel.additionalFields,
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
    enteredTitle: MutableState<String>,
    enteredUserName: MutableState<String>,
    enteredPassword: MutableState<String>,
    additionalFields: MutableState<List<AdditionalFieldDto>>,
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
            enteredTitle = enteredTitle,
            enteredUserName = enteredUserName,
            enteredPassword = enteredPassword,
            additionalFields = additionalFields,
            vaultItemForEdit = vaultItem,
            onAction = onAction
        )
    }
}

@Composable
private fun ModalDragHandle(
    onAction: (AddVaultItemViewModel.Action) -> Unit,
    onDismissRequest: () -> Unit,
    state: AddVaultItemViewModel.State,
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
            if (state.screenState is ScreenState.Loading) {
                Loader(
                    modifier = Modifier.size(Size24),
                    iconTintColor = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(text = stringResource(if (state.isEdit) ResString.update else ResString.save))
            }
        }
    }
}

@Composable
internal fun AddPasswordFields(
    state: AddVaultItemViewModel.State,
    enteredTitle: MutableState<String>,
    enteredUserName: MutableState<String>,
    enteredPassword: MutableState<String>,
    additionalFields: MutableState<List<AdditionalFieldDto>>,
    vaultItemForEdit: PasswordUIModel?,
    onAction: (AddVaultItemViewModel.Action) -> Unit,
) {
    val titleFocusRequester = remember {
        FocusRequester()
    }
    val userNameFocusRequester = remember {
        FocusRequester()
    }
    val passwordFocusRequester = remember {
        FocusRequester()
    }
    val keyboardController = KeyboardManager.getKeyboardController()
    val isPasswordHidden = remember {
        mutableStateOf(false)
    }
    val eyeImage =
        if (isPasswordHidden.value) Icons.Filled.VisibilityOff else Icons.Filled.Visibility

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(true) {
        if (vaultItemForEdit != null) {
            onAction(AddVaultItemViewModel.Action.SetPasswordForEdit(vaultItemForEdit))
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
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
        ) {
            TitleField(
                state = state,
                onAction = onAction,
                enteredTitle = enteredTitle,
                titleFocusRequester = titleFocusRequester,
                userNameFocusRequester = userNameFocusRequester
            )
            CategorySelectorItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = Size16, vertical = Size8),
                state = state,
                onAction = onAction
            )
            UserNameField(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = Size16, end = Size16, top = Size8),
                onAction = onAction,
                enteredUserName = enteredUserName,
                userNameFocusRequester = userNameFocusRequester,
                passwordFocusRequester = passwordFocusRequester
            )
            PasswordField(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = Size16, end = Size16, top = Size8, bottom = Size8),
                onAction = onAction,
                enteredPassword = enteredPassword,
                passwordFocusRequester = passwordFocusRequester,
                keyboardController = keyboardController,
                isPasswordHidden = isPasswordHidden,
                eyeImage = eyeImage
            )
            AdditionalFieldsList(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = Size8, bottom = Size8),
                state = state,
                additionalFields = additionalFields,
                onAction = onAction
            )
            RoundedButton(
                modifier = Modifier.fillMaxWidth().padding(Size16).bounceClick(),
                text = stringResource(ResString.add_field),
                onClick = {
                    onAction(AddVaultItemViewModel.Action.AddAdditionalField)
                    coroutineScope.launch {
                        delay(SCROLL_DOWN_DELAY)
                        scrollState.animateScrollTo(scrollState.maxValue)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                buttonShape = ButtonShape.ROUNDED
            )
        }
    }

    LaunchedEffect(Unit) {
        if (vaultItemForEdit == null) {
            delay(DELAY_BEFORE_FOCUS)
            titleFocusRequester.requestFocus()
        }
    }

}

@Composable
private fun AdditionalFieldsList(
    modifier: Modifier = Modifier,
    state: AddVaultItemViewModel.State,
    additionalFields: MutableState<List<AdditionalFieldDto>>,
    onAction: (AddVaultItemViewModel.Action) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        additionalFields.value.forEach { additionalField ->
            AdditionalFieldItem(
                modifier = Modifier
                    .padding(
                        start = Size16,
                        end = Size16,
                        bottom = Size8
                    )
                    .onEnterClick {
                        onAction(AddVaultItemViewModel.Action.SavePassword)
                    },
                title = additionalField.title,
                value = additionalField.value,
                onTitleChanged = { title ->
                    onAction(
                        AddVaultItemViewModel.Action.EnterAdditionalFieldTitle(
                            id = additionalField.id, title
                        )
                    )
                },
                onValueChanged = { value ->
                    onAction(
                        AddVaultItemViewModel.Action.EnterAdditionalFieldValue(
                            additionalField.id, value
                        )
                    )
                },
                onDeleteClick = {
                    onAction(
                        AddVaultItemViewModel.Action.RemoveAdditionalField(
                            additionalField.id
                        )
                    )
                },
                isEditMode = state.isEdit
            )
        }
    }
}

@Composable
private fun PasswordField(
    modifier: Modifier = Modifier,
    onAction: (AddVaultItemViewModel.Action) -> Unit,
    enteredPassword: MutableState<String>,
    passwordFocusRequester: FocusRequester,
    keyboardController: SoftwareKeyboardController?,
    isPasswordHidden: MutableState<Boolean>,
    eyeImage: ImageVector,
) {
    RoundedContainer(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        isBottomRounded = true,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HintTextField(
                modifier = Modifier
                    .focusRequester(passwordFocusRequester)
                    .wrapContentHeight()
                    .fillMaxWidth(Percent90)
                    .padding(horizontal = Size12, vertical = Size16)
                    .onEnterClick {
                        onAction(AddVaultItemViewModel.Action.SavePassword)
                    },
                onValueChanged = { password ->
                    onAction(AddVaultItemViewModel.Action.EnterPassword(password))
                },
                hint = stringResource(ResString.password),
                value = enteredPassword.value,
                textColor = MaterialTheme.colorScheme.onSurface,
                fontSize = Text20,
                onKeyboardAction = KeyboardActions {
                    keyboardController?.hide()
                },
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password,
                passwordVisible = !isPasswordHidden.value
            )
            IconButton(modifier = Modifier.padding(end = Size8), onClick = {
                isPasswordHidden.value = !isPasswordHidden.value
            }) {
                Icon(
                    imageVector = eyeImage,
                    contentDescription = stringResource(ResString.visibility),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun UserNameField(
    modifier: Modifier = Modifier,
    onAction: (AddVaultItemViewModel.Action) -> Unit,
    enteredUserName: MutableState<String>,
    userNameFocusRequester: FocusRequester,
    passwordFocusRequester: FocusRequester,
) {
    RoundedContainer(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        isTopRounded = true,
    ) {
        HintTextField(
            modifier = Modifier
                .focusRequester(userNameFocusRequester)
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = Size12, vertical = Size16)
                .onEnterClick {
                    onAction(AddVaultItemViewModel.Action.SavePassword)
                },
            onValueChanged = { userName ->
                onAction(AddVaultItemViewModel.Action.EnterUserName(userName))
            },
            value = enteredUserName.value,
            hint = stringResource(ResString.username),
            textColor = MaterialTheme.colorScheme.onSurface,
            fontSize = Text20,
            onKeyboardAction = KeyboardActions {
                passwordFocusRequester.requestFocus()
            },
            imeAction = ImeAction.Next
        )

    }
}

@Composable
private fun TitleField(
    state: AddVaultItemViewModel.State,
    onAction: (AddVaultItemViewModel.Action) -> Unit,
    enteredTitle: MutableState<String>,
    titleFocusRequester: FocusRequester,
    userNameFocusRequester: FocusRequester,
) {
    Row(
        modifier = Modifier.padding(start = Size16, end = Size16, bottom = Size16),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(start = Size8)
                .size(Size56)
        ) {
            Surface(
                modifier = Modifier
                    .size(Size48),
                color = Color.Transparent,
                shape = EqualRounded.small
            ) {
                LoadedImage(
                    modifier = Modifier
                        .fillMaxSize(),
                    imageUrl = state.organizationLogo,
                    placeholderDrawableResource = ResDrawable.ic_password,
                    errorDrawableResource = ResDrawable.ic_password,
                    placeholderDrawableTintColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    backgroundColor = Color.Transparent,
                    showLoading = state.isLogoLoading
                )
            }
            if (state.showClearLogoButton) {
                Box(
                    modifier = Modifier
                        .size(Size22)
                        .align(Alignment.BottomEnd)
                        .padding(top = Size4, start = Size4)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .clickable {
                            onAction(AddVaultItemViewModel.Action.ClearLogo)
                        }
                ) {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(Size12),
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
        HintTextField(
            modifier = Modifier
                .focusRequester(titleFocusRequester)
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(start = Size16, bottom = Size8)
                .onEnterClick {
                    onAction(AddVaultItemViewModel.Action.SavePassword)
                },
            onValueChanged = { title ->
                onAction(AddVaultItemViewModel.Action.EnterTitle(title))
            },
            value = enteredTitle.value,
            hint = stringResource(ResString.title),
            textColor = MaterialTheme.colorScheme.onSurface,
            fontSize = Text22,
            onKeyboardAction = KeyboardActions {
                userNameFocusRequester.requestFocus()
            },
            imeAction = ImeAction.Next
        )
    }
}
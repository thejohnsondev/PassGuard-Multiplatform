package com.thejohnsondev.presentation.additem

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.thejohnsondev.analytics.Analytics
import com.thejohnsondev.common.SCROLL_DOWN_DELAY
import com.thejohnsondev.model.OneTimeEvent
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.model.auth.logo.FindLogoResponse
import com.thejohnsondev.model.vault.AdditionalFieldDto
import com.thejohnsondev.presentation.component.CategorySelectorItem
import com.thejohnsondev.presentation.passwordgenerator.PASSWORD_ANIM_DURATION
import com.thejohnsondev.presentation.passwordgenerator.PasswordGeneratorBottomSheet
import com.thejohnsondev.presentation.passwordgenerator.randomAnimation
import com.thejohnsondev.ui.components.LoadedImage
import com.thejohnsondev.ui.components.button.RoundedButton
import com.thejohnsondev.ui.components.container.ExpandableContent
import com.thejohnsondev.ui.components.container.RoundedContainer
import com.thejohnsondev.ui.components.dialog.ModalDragHandle
import com.thejohnsondev.ui.components.loader.Loader
import com.thejohnsondev.ui.components.loader.StrengthLevelIndicator
import com.thejohnsondev.ui.components.text.PrimaryTextField
import com.thejohnsondev.ui.components.text.PrimaryTextFieldWithBackground
import com.thejohnsondev.ui.components.text.TextFieldIconBehavior
import com.thejohnsondev.ui.components.vault.AdditionalFieldItem
import com.thejohnsondev.ui.components.vault.passworditem.PasswordUIModel
import com.thejohnsondev.ui.designsystem.EquallyRounded
import com.thejohnsondev.ui.designsystem.Percent100
import com.thejohnsondev.ui.designsystem.Size12
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size22
import com.thejohnsondev.ui.designsystem.Size24
import com.thejohnsondev.ui.designsystem.Size28
import com.thejohnsondev.ui.designsystem.Size32
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size48
import com.thejohnsondev.ui.designsystem.Size56
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.designsystem.SizeDefault
import com.thejohnsondev.ui.designsystem.Text18
import com.thejohnsondev.ui.designsystem.Text20
import com.thejohnsondev.ui.designsystem.Text22
import com.thejohnsondev.ui.designsystem.TopRounded
import com.thejohnsondev.ui.designsystem.colorscheme.themeColorUrl
import com.thejohnsondev.ui.displaymessage.getAsText
import com.thejohnsondev.ui.utils.KeyboardManager
import com.thejohnsondev.ui.utils.ResDrawable
import com.thejohnsondev.ui.utils.ResString
import com.thejohnsondev.ui.utils.applyIf
import com.thejohnsondev.ui.utils.bounceClick
import com.thejohnsondev.ui.utils.isCompact
import com.thejohnsondev.ui.utils.onEnterClick
import com.thejohnsondev.ui.utils.padding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import vaultmultiplatform.core.ui.generated.resources.add_field
import vaultmultiplatform.core.ui.generated.resources.domain
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

    LaunchedEffect(Unit) {
        Analytics.trackScreen(
            if(vaultItem == null) {
                "Add Vault Item Screen"
            } else {
                "Edit Vault Item Screen"
            }
        )
    }

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
        enteredDomain = viewModel.enteredDomain,
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
    enteredDomain: MutableState<String>,
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
            ModalDragHandle(
                onDismissRequest = {
                    onAction(AddVaultItemViewModel.Action.Clear)
                    onDismissRequest()
                },
                endContent = {
                    val buttonTitle = stringResource(if (state.isEdit) ResString.update else ResString.save)
                    Button(
                        modifier = Modifier
                            .padding(end = Size16)
                            .bounceClick()
                            .semantics {
                                contentDescription = buttonTitle
                            },
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
                            Text(text = buttonTitle)
                        }
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        AddPasswordFields(
            state = state,
            enteredTitle = enteredTitle,
            enteredDomain = enteredDomain,
            enteredUserName = enteredUserName,
            enteredPassword = enteredPassword,
            additionalFields = additionalFields,
            vaultItemForEdit = vaultItem,
            onAction = onAction
        )
        GeneratePasswordDialog(
            windowSizeClass = windowSizeClass,
            paddingValues = paddingValues,
            state = state,
            onAction = onAction
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GeneratePasswordDialog(
    windowSizeClass: WindowWidthSizeClass,
    paddingValues: PaddingValues,
    state: AddVaultItemViewModel.State,
    onAction: (AddVaultItemViewModel.Action) -> Unit,
) {
    val generatePasswordBottomSheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = false)

    if (state.showGeneratePasswordBottomSheet) {
        PasswordGeneratorBottomSheet(
            windowSizeClass,
            paddingValues,
            generatePasswordBottomSheetState,
            onPasswordGenerated = {
                onAction(AddVaultItemViewModel.Action.EnterPassword(it.password))
                onAction(AddVaultItemViewModel.Action.ShowHideGeneratePasswordBottomSheet(false))
            },
            onDismissRequest = {
                onAction(AddVaultItemViewModel.Action.ShowHideGeneratePasswordBottomSheet(false))
            }
        )
    }
}

@Composable
internal fun AddPasswordFields(
    state: AddVaultItemViewModel.State,
    enteredTitle: MutableState<String>,
    enteredDomain: MutableState<String>,
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
            DomainField(
                modifier = Modifier
                    .padding(top = Size8, horizontal = Size16)
                    .wrapContentHeight()
                    .fillMaxWidth(),
                state = state,
                enteredDomain = enteredDomain,
                onAction = onAction
            )
            LogoSearchResults(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = Size16),
                state = state,
                onAction = onAction
            )
            CategorySelectorItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(
                        horizontal = Size16,
                        bottom = Size8,
                        top = Size8
                    ),
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
                state = state,
                enteredPassword = enteredPassword,
                passwordFocusRequester = passwordFocusRequester,
                keyboardController = keyboardController,
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
                modifier = Modifier.fillMaxWidth().padding(horizontal = Size16),
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
                )
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
private fun TitleField(
    state: AddVaultItemViewModel.State,
    onAction: (AddVaultItemViewModel.Action) -> Unit,
    enteredTitle: MutableState<String>,
    titleFocusRequester: FocusRequester,
    userNameFocusRequester: FocusRequester,
) {
    Row(
        modifier = Modifier.padding(
            start = Size16,
            end = Size16,
            bottom = SizeDefault
        ),
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
                shape = EquallyRounded.small
            ) {
                LoadedImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            onAction(AddVaultItemViewModel.Action.ToggleShowHideLogoSearchResult)
                        },
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
        PrimaryTextField(
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

@Composable
private fun DomainField(
    modifier: Modifier = Modifier,
    enteredDomain: MutableState<String>,
    state: AddVaultItemViewModel.State,
    onAction: (AddVaultItemViewModel.Action) -> Unit,
) {
    PrimaryTextFieldWithBackground(
        modifier = modifier,
        onValueChanged = {
            onAction(AddVaultItemViewModel.Action.EnterDomain(it))
        },
        hint = stringResource(ResString.domain),
        value = enteredDomain.value,
        textColor = themeColorUrl,
        fontSize = Text18,
        backgroundShape = RoundedCornerShape(
            topStart = Size16,
            bottomStart = if (state.isLogoSearchResultsVisible) SizeDefault else Size4,
            topEnd = Size16,
            bottomEnd = if (state.isLogoSearchResultsVisible) SizeDefault else Size4
        )
    )
}

@Composable
private fun LogoSearchResults(
    modifier: Modifier = Modifier,
    state: AddVaultItemViewModel.State,
    onAction: (AddVaultItemViewModel.Action) -> Unit,
) {
    ExpandableContent(
        visible = state.isLogoSearchResultsVisible,
    ) {
        RoundedContainer(
            modifier = modifier,
            color = MaterialTheme.colorScheme.surfaceContainerHigh,
            shape = RoundedCornerShape(
                topStart = SizeDefault,
                topEnd = SizeDefault,
                bottomStart = Size16,
                bottomEnd = Size16
            )
        ) {
            Column {
                state.logoSearchResults.forEachIndexed { index, item ->
                    SearchResultItem(
                        index = index,
                        item = item,
                        state = state,
                        onAction = onAction,
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchResultItem(
    index: Int,
    item: FindLogoResponse,
    state: AddVaultItemViewModel.State,
    onAction: (AddVaultItemViewModel.Action) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = Size8,
                end = Size8,
                top = Size8,
                bottom = if (index == state.logoSearchResults.size - 1) Size8 else SizeDefault
            )
            .clip(
                RoundedCornerShape(Size8)
            )
            .clickable {
                onAction(AddVaultItemViewModel.Action.SelectLogo(item))
            }
            .padding(Size4),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier
                .size(Size48),
            color = Color.Transparent,
            shape = EquallyRounded.small
        ) {
            LoadedImage(
                modifier = Modifier
                    .fillMaxSize(),
                imageUrl = item.logoUrl,
                placeholderDrawableResource = ResDrawable.ic_password,
                errorDrawableResource = ResDrawable.ic_password,
                placeholderDrawableTintColor = MaterialTheme.colorScheme.onPrimaryContainer,
                backgroundColor = Color.Transparent,
                showLoading = state.isLogoLoading
            )
        }
        Text(
            modifier = Modifier
                .padding(horizontal = Size8),
            text = item.name,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
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
    PrimaryTextFieldWithBackground(
        modifier = modifier
            .focusRequester(userNameFocusRequester)
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
        imeAction = ImeAction.Next,
        backgroundShape = TopRounded
    )
}

@Composable
private fun PasswordField(
    modifier: Modifier = Modifier,
    onAction: (AddVaultItemViewModel.Action) -> Unit,
    state: AddVaultItemViewModel.State,
    enteredPassword: MutableState<String>,
    passwordFocusRequester: FocusRequester,
    keyboardController: SoftwareKeyboardController?,
) {
    val hapticFeedback = LocalHapticFeedback.current
    val rotationAngle = remember { mutableStateOf(0f) }
    val animatedRotationAngle by animateFloatAsState(
        targetValue = rotationAngle.value,
        animationSpec = tween(durationMillis = PASSWORD_ANIM_DURATION),
        label = "Icon Rotation"
    )
    Column {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PrimaryTextFieldWithBackground(
                modifier = Modifier
                    .weight(Percent100)
                    .focusRequester(passwordFocusRequester)
                    .onEnterClick {
                        onAction(AddVaultItemViewModel.Action.SavePassword)
                    },
                onValueChanged = { password ->
                    onAction(AddVaultItemViewModel.Action.EnterPassword(password))
                },
                hint = stringResource(ResString.password),
                value = enteredPassword.value,
                textColor = MaterialTheme.colorScheme.onSurface,
                onKeyboardAction = KeyboardActions {
                    keyboardController?.hide()
                },
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password,
                textFieldIconBehavior = TextFieldIconBehavior.HideShow,
                backgroundShape = RoundedCornerShape(
                    topStart = Size4,
                    bottomStart = Size16,
                    topEnd = Size4,
                    bottomEnd = Size4
                )
            )
            RoundedContainer(
                modifier = Modifier
                    .padding(start = Size8)
                    .bounceClick(),
                shape = RoundedCornerShape(
                    topStart = Size4,
                    bottomStart = Size4,
                    topEnd = Size4,
                    bottomEnd = Size16
                ),
                onClick = {
                    hapticFeedback.performHapticFeedback(
                        HapticFeedbackType.LongPress
                    )
                    onAction(AddVaultItemViewModel.Action.GeneratePassword)
                    randomAnimation(rotationAngle)
                },
                onLongClick = {
                    hapticFeedback.performHapticFeedback(
                        HapticFeedbackType.LongPress
                    )
                    keyboardController?.hide()
                    onAction(AddVaultItemViewModel.Action.ShowHideGeneratePasswordBottomSheet(true))
                }
            ) {
                Icon(
                    modifier = Modifier
                        .padding(Size12)
                        .size(Size32)
                        .rotate(animatedRotationAngle),
                    imageVector = Icons.Default.Casino,
                    contentDescription = stringResource(ResString.visibility),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        AnimatedVisibility(
            visible = state.showEnteredPasswordStrength
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = Size28)
                    .height(Size48),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .padding(end = Size8)
                        .weight(Percent100),
                    text = buildAnnotatedString {
                        append(state.enteredPasswordStrength?.suggestion.orEmpty())
                    },
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodySmall,
                )
                StrengthLevelIndicator(
                    level = state.enteredPasswordStrength?.level ?: 0.0f
                )
            }
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
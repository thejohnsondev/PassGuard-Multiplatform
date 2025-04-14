package com.thejohnsondev.presentation.passwordgenerator

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thejohnsondev.common.EXPAND_ANIM_DURATION
import com.thejohnsondev.model.tools.PASSWORD_GENERATOR_MAX_LENGTh
import com.thejohnsondev.model.tools.PASSWORD_GENERATOR_MIN_LENGTH
import com.thejohnsondev.ui.components.button.RoundedButton
import com.thejohnsondev.ui.components.button.ToggleOptionItem
import com.thejohnsondev.ui.components.container.ExpandableContent
import com.thejohnsondev.ui.components.container.RoundedContainer
import com.thejohnsondev.ui.designsystem.EndRounded
import com.thejohnsondev.ui.designsystem.Percent100
import com.thejohnsondev.ui.designsystem.Size100
import com.thejohnsondev.ui.designsystem.Size12
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size48
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.designsystem.StartRounded
import com.thejohnsondev.ui.utils.ResString
import com.thejohnsondev.ui.utils.padding
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import vaultmultiplatform.core.ui.generated.resources.password_generator_configure
import vaultmultiplatform.core.ui.generated.resources.password_generator_copy
import vaultmultiplatform.core.ui.generated.resources.password_generator_digits
import vaultmultiplatform.core.ui.generated.resources.password_generator_generate
import vaultmultiplatform.core.ui.generated.resources.password_generator_include
import vaultmultiplatform.core.ui.generated.resources.password_generator_length
import vaultmultiplatform.core.ui.generated.resources.password_generator_length_characters
import vaultmultiplatform.core.ui.generated.resources.password_generator_lowercase
import vaultmultiplatform.core.ui.generated.resources.password_generator_special
import vaultmultiplatform.core.ui.generated.resources.password_generator_uppercase

@OptIn(KoinExperimentalAPI::class)
@Composable
fun PasswordGeneratorWidget(
    modifier: Modifier = Modifier,
    viewModel: PasswordGeneratorViewModel = koinViewModel(),
    onCopyClick: (String) -> Unit = {},
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.perform(PasswordGeneratorViewModel.Action.FetchConfig)
    }

    PasswordGeneratorWidgetContent(
        modifier = modifier,
        state = state.value,
        onAction = {
            when (it) {
                is PasswordGeneratorViewModel.Action.Copy -> {
                    onCopyClick(it.password)
                }

                else -> {
                    // no-op
                }
            }
            viewModel.perform(it)
        },
    )
}

// this should be private, but the previews are in another module rn
@Composable
fun PasswordGeneratorWidgetContent(
    modifier: Modifier = Modifier,
    state: PasswordGeneratorViewModel.State,
    onAction: (PasswordGeneratorViewModel.Action) -> Unit,
) {
    val hapticFeedback = LocalHapticFeedback.current
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RoundedContainer(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Column {
                MainPasswordView(state)
                GenerateButtonsRow(
                    hapticFeedback = hapticFeedback,
                    state = state,
                    onAction = onAction
                )
            }
        }
        RoundedContainer(
            modifier = Modifier
                .padding(top = Size8)
                .fillMaxWidth(),
        ) {
            ConfigurationView(
                hapticFeedback = hapticFeedback,
                state = state,
                onAction = onAction
            )
        }
    }
}

@Composable
private fun MainPasswordView(state: PasswordGeneratorViewModel.State) {
    Box(
        modifier = Modifier
            .height(Size100),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .padding(Size8)
                .fillMaxWidth(),
            text = buildAnnotatedString {
                state.passwordGeneratedResult?.password.orEmpty().forEach { char ->
                    when {
                        char.isDigit() -> {
                            withStyle(
                                style = SpanStyle(color = MaterialTheme.colorScheme.primary) // Green color
                            ) {
                                append(char)
                            }
                        }
                        !char.isLetterOrDigit() -> {
                            withStyle(
                                style = SpanStyle(color = MaterialTheme.colorScheme.tertiary) // Violet color
                            ) {
                                append(char)
                            }
                        }
                        else -> {
                            append(char)
                        }
                    }
                }
            },
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge,
        )
    }
    HorizontalDivider(
        modifier = Modifier
            .padding(horizontal = Size4)
            .fillMaxWidth()
    )
    Box(
        modifier = Modifier
            .height(Size48),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .padding(Size8)
                .fillMaxWidth(),
            text = buildAnnotatedString {
                append(state.passwordGeneratedResult?.suggestion.orEmpty())
            },
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Composable
private fun GenerateButtonsRow(
    hapticFeedback: HapticFeedback,
    state: PasswordGeneratorViewModel.State,
    onAction: (PasswordGeneratorViewModel.Action) -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = Size12, bottom = Size12),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RoundedButton(
            modifier = Modifier
                .padding(end = Size4)
                .weight(Percent100),
            text = stringResource(ResString.password_generator_generate),
            imageVector = Icons.Default.Casino,
            buttonShape = StartRounded,
            onClick = {
                hapticFeedback.performHapticFeedback(
                    HapticFeedbackType.LongPress
                )
                onAction(PasswordGeneratorViewModel.Action.GeneratePassword)
            }
        )
        RoundedButton(
            modifier = Modifier
                .padding(start = Size4)
                .weight(Percent100),
            text = stringResource(ResString.password_generator_copy),
            imageVector = Icons.Default.CopyAll,
            buttonShape = EndRounded,
            onClick = {
                hapticFeedback.performHapticFeedback(
                    HapticFeedbackType.LongPress
                )
                onAction(
                    PasswordGeneratorViewModel.Action.Copy(
                        password = state.passwordGeneratedResult?.password.orEmpty()
                    )
                )
            }
        )
    }
}

@Composable
fun ConfigurationView(
    hapticFeedback: HapticFeedback,
    state: PasswordGeneratorViewModel.State,
    onAction: (PasswordGeneratorViewModel.Action) -> Unit,
) {
    val isConfigurationExpanded = remember { mutableStateOf(false) }

    val transitionState = remember {
        MutableTransitionState(isConfigurationExpanded.value).apply {
            targetState = !isConfigurationExpanded.value
        }
    }
    val transition = rememberTransition(transitionState, label = "")
    val arrowRotation by transition.animateFloat({
        tween(durationMillis = EXPAND_ANIM_DURATION)
    }, label = "") {
        if (isConfigurationExpanded.value) 90f else 0f
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(Size16))
                .clickable {
                    isConfigurationExpanded.value = !isConfigurationExpanded.value
                }
                .padding(vertical = Size8),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(Size12),
                text = stringResource(ResString.password_generator_configure),
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.weight(Percent100))
            Icon(
                modifier = Modifier
                    .rotate(arrowRotation)
                    .padding(Size12),
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
            )
        }
        ExpandableContent(isConfigurationExpanded.value) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = Size12),
                    text = stringResource(ResString.password_generator_length),
                )
                Text(
                    modifier = Modifier
                        .padding(start = Size4),
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            )
                        ) {
                            append(state.length.toString())
                        }
                    },
                    textAlign = TextAlign.Center,
                )
                Text(
                    modifier = Modifier,
                    text = stringResource(ResString.password_generator_length_characters),
                    textAlign = TextAlign.Center,
                )
            }
            Slider(
                modifier = Modifier
                    .padding(horizontal = Size12, top = Size12)
                    .fillMaxWidth(),
                value = state.length.toFloat(),
                onValueChange = {
                    if (it.toInt() != state.previousLengthValue) {
                        hapticFeedback.performHapticFeedback(
                            HapticFeedbackType.LongPress
                        )
                        onAction(PasswordGeneratorViewModel.Action.UpdateLength(it.toInt()))
                    }
                },
                valueRange = PASSWORD_GENERATOR_MIN_LENGTH..PASSWORD_GENERATOR_MAX_LENGTh,
                steps = (PASSWORD_GENERATOR_MAX_LENGTh - 1).toInt(),
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = Size12, top = Size12),
                text = stringResource(ResString.password_generator_include),
            )
            ToggleOptionItem(
                modifier = Modifier
                    .padding(top = Size12, bottom = Size4, horizontal = Size8),
                optionTitle = stringResource(ResString.password_generator_lowercase),
                optionDescription = null,
                isSelected = state.includeLower,
                isFirstItem = true
            ) {
                hapticFeedback.performHapticFeedback(
                    HapticFeedbackType.LongPress
                )
                onAction(PasswordGeneratorViewModel.Action.UpdateIncludeLower(it))
            }
            ToggleOptionItem(
                modifier = Modifier
                    .padding(vertical = Size4, horizontal = Size8),
                optionTitle = stringResource(ResString.password_generator_uppercase),
                optionDescription = null,
                isSelected = state.includeUpper,
            ) {
                hapticFeedback.performHapticFeedback(
                    HapticFeedbackType.LongPress
                )
                onAction(PasswordGeneratorViewModel.Action.UpdateIncludeUpper(it))
            }
            ToggleOptionItem(
                modifier = Modifier
                    .padding(vertical = Size4, horizontal = Size8),
                optionTitle = stringResource(ResString.password_generator_digits),
                optionDescription = null,
                isSelected = state.includeDigits,
            ) {
                hapticFeedback.performHapticFeedback(
                    HapticFeedbackType.LongPress
                )
                onAction(PasswordGeneratorViewModel.Action.UpdateIncludeDigits(it))
            }
            ToggleOptionItem(
                modifier = Modifier
                    .padding(top = Size4, bottom = Size8, horizontal = Size8),
                optionTitle = stringResource(ResString.password_generator_special),
                optionDescription = null,
                isSelected = state.includeSpecial,
                isLastItem = true
            ) {
                hapticFeedback.performHapticFeedback(
                    HapticFeedbackType.LongPress
                )
                onAction(PasswordGeneratorViewModel.Action.UpdateIncludeSpecial(it))
            }
        }
    }

}
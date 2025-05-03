package com.thejohnsondev.presentation.passwordgenerator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.thejohnsondev.model.tools.PasswordGeneratedResult
import com.thejohnsondev.ui.components.button.BackArrowButton
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.utils.ResString
import com.thejohnsondev.ui.utils.applyIf
import com.thejohnsondev.ui.utils.bounceClick
import com.thejohnsondev.ui.utils.isCompact
import org.jetbrains.compose.resources.stringResource
import vaultmultiplatform.core.ui.generated.resources.select

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordGeneratorBottomSheet(
    windowSizeClass: WindowWidthSizeClass,
    paddingValues: PaddingValues,
    sheetState: SheetState,
    onPasswordGenerated: (PasswordGeneratedResult) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val generatedPasswordState = remember {
        mutableStateOf<PasswordGeneratedResult?>(null)
    }
    ModalBottomSheet(
        modifier = Modifier
            .applyIf(windowSizeClass.isCompact()) {
                systemBarsPadding()
            }.applyIf(!windowSizeClass.isCompact()) {
                padding(top = paddingValues.calculateTopPadding())
            },
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        properties = ModalBottomSheetProperties(
            shouldDismissOnBackPress = false
        ),
        dragHandle = {
            ModalDragHandle(
                onDismissRequest = onDismissRequest,
                onSelectClick = {
                    generatedPasswordState.value?.let { password ->
                        onPasswordGenerated(password)
                    }
                })
        },
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            PasswordGeneratorWidget(
                modifier = Modifier
                    .padding(Size16),
                showConfigureByDefault = true,
                onPasswordGenerated = {
                    generatedPasswordState.value = it
                }
            )
        }
    }
}

@Composable
private fun ModalDragHandle(
    onDismissRequest: () -> Unit,
    onSelectClick: () -> Unit,
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
                onDismissRequest()
            }
        )
        Button(
            modifier = Modifier
                .padding(end = Size16)
                .bounceClick(),
            onClick = {
                onSelectClick()
            },
        ) {
            Text(text = stringResource(ResString.select))
        }
    }
}
package com.thejohnsondev.localization

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import com.thejohnsondev.ui.components.dialog.ModalDragHandle
import com.thejohnsondev.ui.designsystem.EquallyRounded
import com.thejohnsondev.ui.designsystem.Percent50i
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size32
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.utils.ResString
import com.thejohnsondev.ui.utils.applyIf
import com.thejohnsondev.ui.utils.bounceClick
import com.thejohnsondev.ui.utils.isCompact
import com.thejohnsondev.ui.utils.padding
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import vaultmultiplatform.core.ui.generated.resources.language_description

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectLanguageBottomSheet(
    windowSizeClass: WindowWidthSizeClass,
    sheetState: SheetState,
    selectedLanguage: Language?,
    onLanguageSelected: (Language) -> Unit,
    onDismissRequest: () -> Unit,
) {
    ModalBottomSheet(
        modifier = Modifier
            .applyIf(windowSizeClass.isCompact()) {
                statusBarsPadding()
            },
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        properties = ModalBottomSheetProperties(
            shouldDismissOnBackPress = false
        ),
        dragHandle = {
            ModalDragHandle(
                onDismissRequest = onDismissRequest,
            )
        },
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = stringResource(ResString.language_description),
            style = MaterialTheme.typography.titleLarge
        )
        LazyColumn(
            modifier = Modifier
                .padding(top = Size16, bottom = Size16)
        ) {
            items(Language.entries) { language ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Size16, vertical = Size8)
                        .clip(EquallyRounded.medium)
                        .bounceClick()
                        .clickable {
                            onLanguageSelected(language)
                            onDismissRequest()
                        }
                        .applyIf(selectedLanguage == language) {
                            background(MaterialTheme.colorScheme.primaryContainer)
                        }
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier
                                .padding(start = Size16, vertical = Size16)
                                .size(Size32)
                                .clip(RoundedCornerShape(percent = Percent50i)),
                            painter = painterResource(language.typeFlagDrawableResource),
                            contentDescription = stringResource(language.typeNameStringResource)
                        )
                        Text(
                            modifier = Modifier.padding(start = Size16, vertical = Size16),
                            text = stringResource(language.typeNameStringResource),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }

}


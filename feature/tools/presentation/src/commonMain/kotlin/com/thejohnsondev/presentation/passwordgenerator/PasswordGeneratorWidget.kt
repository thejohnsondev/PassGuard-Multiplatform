package com.thejohnsondev.presentation.passwordgenerator

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun PasswordGeneratorWidget(
    modifier: Modifier = Modifier,
    viewModel: PasswordGeneratorViewModel = koinViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    PasswordGeneratorWidgetContent(
        state = state.value,
        onAction = viewModel::perform
    )

}

// this should be private, but the previews are in another module rn
@Composable
fun PasswordGeneratorWidgetContent(
    state: PasswordGeneratorViewModel.State,
    onAction: (PasswordGeneratorViewModel.Action) -> Unit,
) {
    // TODO implement
}
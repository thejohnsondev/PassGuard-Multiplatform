package com.thejohnsondev.presentation.vaulthealth

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thejohnsondev.ui.components.ArcProgressbar
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.utils.padding
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun VaultHealthWidget(
    modifier: Modifier = Modifier,
    viewModel: VaultHealthViewModel = koinViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.perform(VaultHealthViewModel.Action.GenerateReport)
    }

    VaultHealthWidgetContent(
        modifier = modifier,
        state = state.value,
        onAction = viewModel::perform
    )

}

@Composable
fun VaultHealthWidgetContent(
    modifier: Modifier,
    state: VaultHealthViewModel.State,
    onAction: (VaultHealthViewModel.Action) -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ArcProgressbar(
            modifier = Modifier
                .padding(horizontal = Size16),
            progress = state.report?.overallScore ?: 0f,
            text = "Score"
        )
    }
}
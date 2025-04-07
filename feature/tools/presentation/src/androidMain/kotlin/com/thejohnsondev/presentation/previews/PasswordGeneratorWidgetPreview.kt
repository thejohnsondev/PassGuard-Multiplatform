package com.thejohnsondev.presentation.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.thejohnsondev.presentation.passwordgenerator.PasswordGeneratorViewModel
import com.thejohnsondev.presentation.passwordgenerator.PasswordGeneratorWidgetContent

@Preview(showBackground = true)
@Composable
fun PasswordGeneratorWidgetPreview() {
    PasswordGeneratorWidgetContent(
        state = PasswordGeneratorViewModel.State(),
        onAction = {}
    )
}
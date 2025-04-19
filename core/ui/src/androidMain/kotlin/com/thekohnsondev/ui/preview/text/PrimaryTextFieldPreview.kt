package com.thekohnsondev.ui.preview.text

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.thejohnsondev.ui.components.text.PrimaryTextField
import com.thejohnsondev.ui.components.text.TextFieldIconBehavior
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.utils.padding

@Preview(showBackground = true)
@Composable
private fun PrimaryTextFieldPreview() {
    PrimaryTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Size8),
        value = "fdsfds",
        onValueChanged = {},
        hint = "Enter your name"
    )
}


@Preview(showBackground = true)
@Composable
private fun PrimaryTextFieldToTitlePreview() {
    PrimaryTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Size8),
        value = "fdsfds",
        onValueChanged = {},
        hint = "Enter your name"
    )
}


@Preview(showBackground = true)
@Composable
private fun PrimaryPasswordTextFieldPreview() {
    PrimaryTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Size8),
        value = "fdsfds",
        onValueChanged = {},
        hint = "Enter your password",
        keyboardType = KeyboardType.Password,
        textFieldIconBehavior = TextFieldIconBehavior.HideShow
    )

}

@Preview(showBackground = true)
@Composable
private fun PrimaryClearTextFieldPreview() {
    PrimaryTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Size8),
        onValueChanged = {},
        hint = "Type to start searching",
        textFieldIconBehavior = TextFieldIconBehavior.Clear(onClick = {
            // Do something
        })
    )

}

@Preview(showBackground = true)
@Composable
private fun PrimaryIconTextFieldPreview() {
    PrimaryTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Size8),
        onValueChanged = {},
        hint = "Type to start searching",
        textFieldIconBehavior = TextFieldIconBehavior.Icon(
            icon = Icons.Default.Search
        )
    )

}

@Preview(showBackground = true)
@Composable
private fun PrimaryErrorTextFieldPreview() {
    PrimaryTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Size8),
        onValueChanged = {},
        hint = "Type to start searching",
        errorText = "Error text"
    )
}


@Preview(showBackground = true)
@Composable
private fun PrimaryReadOnlyTextFieldPreview() {
    PrimaryTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Size8),
        value = "Predefined value",
        onValueChanged = {},
        hint = "Value from dropdown",
        readOnly = true,
        textFieldIconBehavior = TextFieldIconBehavior.Icon(
            icon = Icons.Default.KeyboardArrowDown
        )
    )

}
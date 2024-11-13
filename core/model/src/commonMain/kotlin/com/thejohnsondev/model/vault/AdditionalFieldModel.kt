package com.thejohnsondev.model.vault

data class AdditionalFieldModel(
    var id: String,
    val title: String,
    val value: String,
) {
    companion object {
        val testAdditionalField = AdditionalFieldModel(
            id = "1",
            title = "exampleField1",
            value = "exampleValue1"
        )
    }
}

package com.thejohnsondev.model.vault

data class AdditionalFieldDto(
    var id: String,
    val title: String,
    val value: String,
) {
    companion object {
        val testAdditionalField = AdditionalFieldDto(
            id = "1",
            title = "exampleField1",
            value = "exampleValue1"
        )
    }
}

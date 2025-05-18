package com.thejohnsondev.ui.components.vault.passworditem

import com.thejohnsondev.model.vault.AdditionalFieldDto
import com.thejohnsondev.ui.model.CategoryUIModel
import com.thejohnsondev.ui.model.FilterUIModel.Companion.mapToCategory
import com.thejohnsondev.ui.model.filterlists.FiltersProvider

data class PasswordUIModel(
    val id: String,
    val title: String,
    val organizationLogo: String? = null,
    val domain: String? = null,
    val userName: String,
    val password: String,
    val additionalFields: List<AdditionalFieldDto> = emptyList(),
    val createdTime: String?,
    val modifiedTime: String?,
    val isFavorite: Boolean,
    val isExpanded: Boolean = false,
    val category: CategoryUIModel,
    val showUpdateAnimation: Boolean = false
) {
    companion object {
        val testPasswordUIModel = PasswordUIModel(
            id = "1",
            title = "Example Organization 1",
            organizationLogo = "https://example.com/logo1.png",
            userName = "Example Title 1",
            password = "examplePassword123",
            additionalFields = listOf(
                AdditionalFieldDto.testAdditionalField,
                AdditionalFieldDto.testAdditionalField.copy(
                    id = "2",
                    title = "exampleField2",
                    value = "exampleValue2"
                )
            ),
            modifiedTime = "November 2 2024 20:01",
            createdTime = "November 1 2024 10:22",
            isFavorite = false,
            category = FiltersProvider.Category.getDefaultCategoryFilter().mapToCategory()
        )
        val testPasswordItems = listOf(
            testPasswordUIModel.copy(
                id = "1",
                additionalFields = listOf()
            ),
            testPasswordUIModel.copy(
                id = "2",
                userName = "Pass Title 2",
                title = "Example Organization 2",
                organizationLogo = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c1/Google_%22G%22_logo.svg/1200px-Google_%22G%22_logo.svg.png",
                additionalFields = listOf(
                    AdditionalFieldDto.testAdditionalField
                ),
                category = FiltersProvider.Category.getDefaultCategoryFilter().mapToCategory()
            ),
            testPasswordUIModel.copy(
                id = "3",
                userName = "Pass Title 3",
                title = "Example Organization 3",
                isFavorite = true,
                additionalFields = listOf(
                    AdditionalFieldDto.testAdditionalField,
                    AdditionalFieldDto.testAdditionalField.copy(
                        id = "2",
                        title = "exampleField2",
                        value = "exampleValue2"
                    )
                ),
                category = FiltersProvider.Category.getDefaultCategoryFilter().mapToCategory()
            ),
            testPasswordUIModel.copy(
                id = "4",
                userName = "Pass Title example 4",
                title = "Example Organization 4",
                organizationLogo = "https://example.com/logo4.png",
                additionalFields = listOf(
                    AdditionalFieldDto.testAdditionalField,
                    AdditionalFieldDto.testAdditionalField.copy(
                        id = "2",
                        title = "exampleField2",
                        value = "exampleValue2"
                    ),
                    AdditionalFieldDto.testAdditionalField.copy(
                        id = "3",
                        title = "exampleField3",
                        value = "exampleValue3"
                    ),
                ),
                category = FiltersProvider.Category.getDefaultCategoryFilter().mapToCategory()
            ),
            testPasswordUIModel.copy(
                id = "5",
                userName = "Pass Title example 5",
                title = "Example Organization 5",
                additionalFields = listOf(
                    AdditionalFieldDto.testAdditionalField,
                    AdditionalFieldDto.testAdditionalField.copy(
                        id = "2",
                        title = "exampleField2",
                        value = "exampleValue2"
                    ),
                    AdditionalFieldDto.testAdditionalField.copy(
                        id = "3",
                        title = "exampleField3",
                        value = "exampleValue3"
                    ),
                    AdditionalFieldDto.testAdditionalField.copy(
                        id = "4",
                        title = "exampleField4",
                        value = "exampleValue4"
                    ),
                )
            ),
            testPasswordUIModel.copy(
                id = "6",
                userName = "Pass Title example 6",
                title = "Example Organization 6",
                organizationLogo = "https://example.com/logo6.png"
            ),
            testPasswordUIModel.copy(
                id = "7",
                userName = "Example Title 7",
                title = "Example Organization 7",
                isFavorite = true
            ),
            testPasswordUIModel.copy(
                id = "8",
                userName = "Example Title 8",
                title = "Example Organization 8",
                organizationLogo = "https://example.com/logo8.png"
            ),
            testPasswordUIModel.copy(
                id = "9",
                userName = "Example Title 9",
                title = "Example Organization 9",
                category = FiltersProvider.Category.getDefaultCategoryFilter().mapToCategory()
            ),
            testPasswordUIModel.copy(
                id = "10",
                userName = "Example Title 10",
                title = "Example Organization 10",
                organizationLogo = "https://example.com/logo10.png",
                category = FiltersProvider.Category.getDefaultCategoryFilter().mapToCategory()
            ),
            testPasswordUIModel.copy(
                id = "11",
                userName = "Example Title 11",
                title = "Example Organization 11",
                isFavorite = true
            ),
            testPasswordUIModel.copy(
                id = "12",
                userName = "Example Title 12",
                title = "Example Organization 12",
                organizationLogo = "https://example.com/logo12.png",
                category = FiltersProvider.Category.getDefaultCategoryFilter().mapToCategory()
            ),
            testPasswordUIModel.copy(
                id = "13",
                userName = "Example Title 13",
                title = "Example Organization 13"
            ),
            testPasswordUIModel.copy(
                id = "14",
                userName = "Example Title 14",
                title = "Example Organization 14",
                organizationLogo = "https://example.com/logo14.png"
            ),
            testPasswordUIModel.copy(
                id = "15",
                userName = "Example Title 15",
                title = "Example Organization 15",
                isFavorite = true,
                category = FiltersProvider.Category.getDefaultCategoryFilter().mapToCategory()
            ),
            testPasswordUIModel.copy(
                id = "16",
                userName = "Example Title 16",
                title = "Example Organization 16",
                organizationLogo = "https://example.com/logo16.png"
            ),
            testPasswordUIModel.copy(
                id = "17",
                userName = "Example Title 17",
                title = "Example Organization 17",
            ),
            testPasswordUIModel.copy(
                id = "18",
                userName = "Example Title 18",
                title = "Example Organization 18",
                organizationLogo = "https://example.com/logo18.png",
                category = FiltersProvider.Category.getDefaultCategoryFilter().mapToCategory()
            ),
            testPasswordUIModel.copy(
                id = "19",
                userName = "Example Title 19",
                title = "Example Organization 19",
                isFavorite = true,
                category = FiltersProvider.Category.getDefaultCategoryFilter().mapToCategory()
            ),
            testPasswordUIModel.copy(
                id = "20",
                userName = "Example Title 20",
                title = "Example Organization 20",
                organizationLogo = "https://example.com/logo20.png",
                category = FiltersProvider.Category.getDefaultCategoryFilter().mapToCategory()
            ),
            testPasswordUIModel.copy(
                id = "21",
                userName = "Example Title 21",
                title = "Example Organization 21"
            )
        )
    }
}
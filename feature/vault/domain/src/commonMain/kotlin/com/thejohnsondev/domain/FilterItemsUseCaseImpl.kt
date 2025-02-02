package com.thejohnsondev.domain

import com.thejohnsondev.ui.model.FilterUIModel
import com.thejohnsondev.ui.model.PasswordUIModel
import com.thejohnsondev.ui.model.filterlists.FiltersProvider.ItemType.passwordsFilterUIModel

// TODO cover with test cases
class FilterItemsUseCaseImpl : FilterItemsUseCase {

    override suspend fun invoke(
        items: List<PasswordUIModel>,
        typeFilters: List<FilterUIModel>,
        categoryFilters: List<FilterUIModel>
    ): List<PasswordUIModel> {
        return items.filter { item ->
            typeFilters.any { filter ->
                if (typeFilters.none { it.isSelected }) {
                    true
                } else {
                    filter.isSelected && filter.id == passwordsFilterUIModel.id // only password items are available now
                }
            } && categoryFilters.any { filter ->
                if (categoryFilters.none { it.isSelected }) {
                    true
                } else {
                    filter.isSelected && filter.id == item.category.id
                }
            }
        }
    }

}
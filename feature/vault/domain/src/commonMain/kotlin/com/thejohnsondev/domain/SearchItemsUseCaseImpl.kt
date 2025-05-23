package com.thejohnsondev.domain

import com.thejohnsondev.ui.components.vault.passworditem.PasswordUIModel

class SearchItemsUseCaseImpl : SearchItemsUseCase {
    override fun invoke(
        query: String,
        isDeepSearchEnabled: Boolean,
        list: List<PasswordUIModel>
    ): List<PasswordUIModel> {
        if (query.isEmpty()) return list
        return if (isDeepSearchEnabled) {
            list.filter {
                it.userName.contains(query, ignoreCase = true)
                        || it.title.contains(query, ignoreCase = true)
                        || it.password.contains(query, ignoreCase = true)
                        || it.additionalFields.any { field ->
                    field.value.contains(query, ignoreCase = true)
                            || field.title.contains(query, ignoreCase = true)
                }
            }
        } else {
            list.filter {
                it.userName.contains(query, ignoreCase = true)
                        || it.title.contains(query, ignoreCase = true)
            }
        }
    }
}
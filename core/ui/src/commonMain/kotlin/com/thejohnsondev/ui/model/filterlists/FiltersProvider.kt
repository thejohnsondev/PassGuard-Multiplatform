package com.thejohnsondev.ui.model.filterlists

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StickyNote2
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Work
import com.thejohnsondev.common.SORT_SHOW_FAVORITES_AT_TOP
import com.thejohnsondev.common.SORT_TIME_NEW
import com.thejohnsondev.common.SORT_TIME_OLD
import com.thejohnsondev.common.SORT_TITLE_AZ
import com.thejohnsondev.common.SORT_TITLE_ZA
import com.thejohnsondev.common.VAULT_ITEM_CATEGORY_FINANCE
import com.thejohnsondev.common.VAULT_ITEM_CATEGORY_OTHERS
import com.thejohnsondev.common.VAULT_ITEM_CATEGORY_PERSONAL
import com.thejohnsondev.common.VAULT_ITEM_CATEGORY_WORK
import com.thejohnsondev.common.VAULT_ITEM_TYPE_BANK_ACCOUNTS
import com.thejohnsondev.common.VAULT_ITEM_TYPE_NOTES
import com.thejohnsondev.common.VAULT_ITEM_TYPE_PASSWORDS
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.filters.FinanceSelectableItemColors
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.filters.OtherSelectableItemColors
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.filters.PersonalSelectableItemColors
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.filters.WorkSelectableItemColors
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.themes.MaterialSelectableItemColors
import com.thejohnsondev.ui.model.IconContainer
import com.thejohnsondev.ui.model.FilterUIModel
import com.thejohnsondev.ui.utils.ResDrawable
import com.thejohnsondev.ui.utils.ResString
import vaultmultiplatform.core.ui.generated.resources.bank_accounts
import vaultmultiplatform.core.ui.generated.resources.finances
import vaultmultiplatform.core.ui.generated.resources.ic_password
import vaultmultiplatform.core.ui.generated.resources.ic_sort_alph_az
import vaultmultiplatform.core.ui.generated.resources.ic_sort_alph_za
import vaultmultiplatform.core.ui.generated.resources.ic_sort_time_new
import vaultmultiplatform.core.ui.generated.resources.ic_sort_time_old
import vaultmultiplatform.core.ui.generated.resources.notes
import vaultmultiplatform.core.ui.generated.resources.other
import vaultmultiplatform.core.ui.generated.resources.passwords
import vaultmultiplatform.core.ui.generated.resources.personal
import vaultmultiplatform.core.ui.generated.resources.show_favorites_at_top
import vaultmultiplatform.core.ui.generated.resources.sort_date_newest
import vaultmultiplatform.core.ui.generated.resources.sort_date_oldest
import vaultmultiplatform.core.ui.generated.resources.sort_title_az
import vaultmultiplatform.core.ui.generated.resources.sort_title_za
import vaultmultiplatform.core.ui.generated.resources.work

object FiltersProvider {

    object ItemType {

        val passwordsFilterUIModel = FilterUIModel(
            id = VAULT_ITEM_TYPE_PASSWORDS,
            nameResId = ResString.passwords,
            iconContainer = IconContainer(
                imageVectorResId = ResDrawable.ic_password
            ),
            colors = MaterialSelectableItemColors,
            isSelected = false
        )

        val notesFilterUIModel = FilterUIModel(
            id = VAULT_ITEM_TYPE_NOTES,
            nameResId = ResString.notes,
            iconContainer = IconContainer(
                imageVector = Icons.AutoMirrored.Filled.StickyNote2
            ),
            colors = MaterialSelectableItemColors,
            isSelected = false
        )

        val bankAccountsFilterUIModel = FilterUIModel(
            id = VAULT_ITEM_TYPE_BANK_ACCOUNTS,
            nameResId = ResString.bank_accounts,
            iconContainer = IconContainer(
                imageVector = Icons.Default.CreditCard
            ),
            colors = MaterialSelectableItemColors,
            isSelected = false
        )

        fun getVaultItemTypeFilters() = listOf(
            passwordsFilterUIModel,
            notesFilterUIModel,
            bankAccountsFilterUIModel
        )

        fun getVaultItemTypeFilterById(id: String): FilterUIModel = when (id) {
            VAULT_ITEM_TYPE_PASSWORDS -> passwordsFilterUIModel
            VAULT_ITEM_TYPE_NOTES -> notesFilterUIModel
            VAULT_ITEM_TYPE_BANK_ACCOUNTS -> bankAccountsFilterUIModel
            else -> throw IllegalArgumentException("Unknown vault item type id: $id")
        }
    }

    object Category {

        val personalFilterUIModel = FilterUIModel(
            id = VAULT_ITEM_CATEGORY_PERSONAL,
            nameResId = ResString.personal,
            iconContainer = IconContainer(
                imageVector = Icons.Filled.Person
            ),
            colors = PersonalSelectableItemColors,
            isSelected = false
        )

        val workFilterUIModel = FilterUIModel(
            id = VAULT_ITEM_CATEGORY_WORK,
            nameResId = ResString.work,
            iconContainer = IconContainer(
                imageVector = Icons.Default.Work
            ),
            colors = WorkSelectableItemColors,
            isSelected = false
        )

        val financeFilterUIModel = FilterUIModel(
            id = VAULT_ITEM_CATEGORY_FINANCE,
            nameResId = ResString.finances,
            iconContainer = IconContainer(
                imageVector = Icons.Default.MonetizationOn
            ),
            colors = FinanceSelectableItemColors,
            isSelected = false
        )

        val othersFilterUIModel = FilterUIModel(
            id = VAULT_ITEM_CATEGORY_OTHERS,
            nameResId = ResString.other,
            iconContainer = IconContainer(
                imageVector = Icons.Default.MoreHoriz
            ),
            colors = OtherSelectableItemColors,
            isSelected = false
        )

        fun getVaultCategoryFilters() = listOf(
            personalFilterUIModel,
            workFilterUIModel,
            financeFilterUIModel,
            othersFilterUIModel
        )

        fun getDefaultCategoryFilter() = personalFilterUIModel

        fun getCategoryFilterUiModelById(id: String): FilterUIModel = when (id) {
            VAULT_ITEM_CATEGORY_PERSONAL -> personalFilterUIModel
            VAULT_ITEM_CATEGORY_WORK -> workFilterUIModel
            VAULT_ITEM_CATEGORY_FINANCE -> financeFilterUIModel
            VAULT_ITEM_CATEGORY_OTHERS -> othersFilterUIModel
            else -> throw IllegalArgumentException("Unknown item category type id: $id")
        }
    }

    object Sorting {
        val sortTitleAZFilterUIModel = FilterUIModel(
            id = SORT_TITLE_AZ,
            nameResId = ResString.sort_title_az,
            iconContainer = IconContainer(
                imageVectorResId = ResDrawable.ic_sort_alph_az
            ),
            colors = MaterialSelectableItemColors,
            isSelected = false
        )

        val sortTitleZAFilterUIModel = FilterUIModel(
            id = SORT_TITLE_ZA,
            nameResId = ResString.sort_title_za,
            iconContainer = IconContainer(
                imageVectorResId = ResDrawable.ic_sort_alph_za
            ),
            colors = MaterialSelectableItemColors,
            isSelected = false
        )

        val sortTimeNewFilterUIModel = FilterUIModel(
            id = SORT_TIME_NEW,
            nameResId = ResString.sort_date_newest,
            iconContainer = IconContainer(
                imageVectorResId = ResDrawable.ic_sort_time_new
            ),
            colors = MaterialSelectableItemColors,
            isSelected = false
        )

        val sortTimeOldFilterUIModel = FilterUIModel(
            id = SORT_TIME_OLD,
            nameResId = ResString.sort_date_oldest,
            iconContainer = IconContainer(
                imageVectorResId = ResDrawable.ic_sort_time_old
            ),
            colors = MaterialSelectableItemColors,
            isSelected = false
        )

        val sortShowFavoritesAtTopFilterUIModel = FilterUIModel(
            id = SORT_SHOW_FAVORITES_AT_TOP,
            nameResId = ResString.show_favorites_at_top,
            iconContainer = IconContainer(
                imageVector = Icons.Default.Star
            ),
            colors = MaterialSelectableItemColors,
            isSelected = false
        )

        fun getSortOrderFilters() = listOf(
            sortTimeNewFilterUIModel,
            sortTimeOldFilterUIModel,
            sortTitleAZFilterUIModel,
            sortTitleZAFilterUIModel
        )
    }

}
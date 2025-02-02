package com.thejohnsondev.ui.model

import com.thejohnsondev.common.SORT_TIME_NEW
import com.thejohnsondev.common.SORT_TIME_OLD
import com.thejohnsondev.common.SORT_TITLE_AZ
import com.thejohnsondev.common.SORT_TITLE_ZA

enum class SortOrder {
    DATE_DESC,
    DATE_ASC,
    TITLE_DESC,
    TITLE_ASC;

    companion object {

        fun List<FilterUIModel>.toSortOrder(): SortOrder {
            val selected = find { it.isSelected }
            return when (selected?.id) {
                SORT_TITLE_AZ -> TITLE_ASC
                SORT_TITLE_ZA -> TITLE_DESC
                SORT_TIME_NEW -> DATE_DESC
                SORT_TIME_OLD -> DATE_ASC
                else -> DATE_DESC
            }
        }

    }
}
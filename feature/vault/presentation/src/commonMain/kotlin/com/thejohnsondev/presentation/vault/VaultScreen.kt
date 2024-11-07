package com.thejohnsondev.presentation.vault

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.thejohnsondev.model.vault.AdditionalFieldModel
import com.thejohnsondev.model.vault.CategoryModel
import com.thejohnsondev.model.vault.PasswordUIModel
import com.thejohnsondev.ui.components.PasswordItem
import com.thejohnsondev.ui.designsystem.Size68
import com.thejohnsondev.ui.designsystem.getAppLogo
import com.thejohnsondev.ui.model.ScaffoldConfig
import com.thejohnsondev.ui.scaffold.BottomNavItem
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.vectorResource
import vaultmultiplatform.feature.vault.presentation.generated.resources.Res
import vaultmultiplatform.feature.vault.presentation.generated.resources.add
import vaultmultiplatform.feature.vault.presentation.generated.resources.vault

@Composable
fun VaultScreen(
    windowSizeClass: WindowWidthSizeClass,
    viewModel: VaultViewModel,
    paddingValues: PaddingValues,
    setScaffoldConfig: (ScaffoldConfig) -> Unit,
) {
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val listState = rememberLazyListState()
    val expandedFab by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex == 0
        }
    }
    val appLogo = vectorResource(getAppLogo())
    var expanded by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(true) {
        setScaffoldConfig(
            ScaffoldConfig(
                topAppBarTitle = getString(Res.string.vault),
                topAppBarIcon = appLogo,
                isFabVisible = true,
                fabTitle = getString(Res.string.add),
                fabIcon = Icons.Default.Add,
                onFabClick = {
                    // TODO handle this
                },
                isFabExpanded = expandedFab,
                snackBarHostState = snackBarHostState,
                bottomBarItemIndex = BottomNavItem.Vault.index
            )
        )
    }
    val passwordsList = remember {
        listOf(
            PasswordUIModel(
                id = "12345",
                organization = "Example Organization 1",
                organizationLogo = "https://example.com/logo1.png",
                title = "Example Title 1",
                password = "examplePassword123",
                additionalFields = listOf(
                    AdditionalFieldModel(
                        id = "1",
                        title = "exampleField1",
                        value = "exampleValue1"
                    ),
                    AdditionalFieldModel(id = "2", title = "exampleField2", value = "exampleValue2")
                ),
                modifiedTime = "November 2 2024 20:01",
                createdTime = "November 1 2024 10:22",
                isFavorite = false,
                isExpanded = true,
                category = CategoryModel(
                    id = "personal",
                    name = "Personal",
                    iconId = 1,
                    colorHex = "#FFFFFF"
                )
            ),
            PasswordUIModel(
                id = "67890",
                organization = "Example Organization 2",
                organizationLogo = null,
                title = "Example Title 2",
                password = "examplePassword456",
                additionalFields = listOf(
                    AdditionalFieldModel(
                        id = "3",
                        title = "exampleField3",
                        value = "exampleValue3"
                    ),
                    AdditionalFieldModel(id = "4", title = "exampleField4", value = "exampleValue4")
                ),
                createdTime = "2023-10-03T12:00:00Z",
                modifiedTime = "2023-10-04T12:00:00Z",
                isFavorite = true,
                category = CategoryModel(
                    id = "work",
                    name = "Work",
                    iconId = 2,
                    colorHex = "#0000FF"
                )
            ),
            PasswordUIModel(
                id = "11223",
                organization = "Example Organization 3",
                organizationLogo = null,
                title = "Example Title 3",
                password = "examplePassword789",
                additionalFields = listOf(
                    AdditionalFieldModel(
                        id = "5",
                        title = "exampleField5",
                        value = "exampleValue5"
                    ),
                    AdditionalFieldModel(id = "6", title = "exampleField6", value = "exampleValue6")
                ),
                createdTime = "2023-10-05T12:00:00Z",
                modifiedTime = "2023-10-06T12:00:00Z",
                isFavorite = true,
                isExpanded = true,
                category = CategoryModel(
                    id = "finance",
                    name = "Finance",
                    iconId = 3,
                    colorHex = "#00FF00"
                )
            ),
            PasswordUIModel(
                id = "44556",
                organization = "Example Organization 4",
                organizationLogo = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c1/Google_%22G%22_logo.svg/1200px-Google_%22G%22_logo.svg.png",
                title = "Example Title 4",
                password = "examplePassword012",
                additionalFields = listOf(
                    AdditionalFieldModel(
                        id = "7",
                        title = "exampleField7",
                        value = "exampleValue7"
                    ),
                    AdditionalFieldModel(id = "8", title = "exampleField8", value = "exampleValue8")
                ),
                createdTime = "2023-10-07T12:00:00Z",
                modifiedTime = "2023-10-08T12:00:00Z",
                isFavorite = false,
                category = CategoryModel(
                    id = "social",
                    name = "Social",
                    iconId = 4,
                    colorHex = "#FF0000"
                )
            )
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = listState
        ) {
            item {
                Spacer(modifier = Modifier.padding(top = paddingValues.calculateTopPadding()))
            }
            items(passwordsList) {
                PasswordItem(
                    item = it,
                    onClick = {
                        expanded = !expanded
                    },
                    isExpanded = it.isExpanded,
                    onDeleteClick = {},
                    onCopyClick = {},
                    onEditClick = {},
                    onCopySensitiveClick = {},
                    onFavoriteClick = {},
                    isFavorite = it.isFavorite
                )
            }
            item {
                Spacer(
                    modifier = Modifier.padding(
                        top = paddingValues.calculateBottomPadding().plus(
                            Size68
                        )
                    )
                )
            }
        }
    }
}
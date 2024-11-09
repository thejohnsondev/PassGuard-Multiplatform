import com.thejohnsondev.domain.ToggleOpenedItemUseCaseImpl
import com.thejohnsondev.model.vault.CategoryModel
import com.thejohnsondev.model.vault.PasswordUIModel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ToggleOpenedItemUseCaseImplTest {

    private val useCase = ToggleOpenedItemUseCaseImpl()

    @Test
    fun itemWithMatchingIdIsExpanded() = runTest {
        val list = listOf(
            PasswordUIModel(
                id = "1",
                isExpanded = false,
                title = "title",
                isFavorite = false,
                organization = "Org",
                password = "Pass",
                organizationLogo = "",
                createdTime = "",
                modifiedTime = "",
                category = CategoryModel(name = "test", colorHex = "#FFFFFF", iconId = 0)
            ),
            PasswordUIModel(
                id = "2", isExpanded = false,
                title = "title",
                isFavorite = false,
                organization = "Org",
                password = "Pass",
                organizationLogo = "",
                createdTime = "",
                modifiedTime = "",
                category = CategoryModel(name = "test", colorHex = "#FFFFFF", iconId = 0)
            )
        )
        val result = useCase.invoke("1", list)
        assertEquals(true, result[0].isExpanded)
        assertEquals(false, result[1].isExpanded)
    }

    @Test
    fun itemWithMatchingIdIsCollapsed() = runBlocking {
        val list = listOf(
            PasswordUIModel(
                id = "1", isExpanded = true,
                title = "title",
                isFavorite = false,
                organization = "Org",
                password = "Pass",
                organizationLogo = "",
                createdTime = "",
                modifiedTime = "",
                category = CategoryModel(name = "test", colorHex = "#FFFFFF", iconId = 0)
            ),
            PasswordUIModel(
                id = "2", isExpanded = false,
                title = "title",
                isFavorite = false,
                organization = "Org",
                password = "Pass",
                organizationLogo = "",
                createdTime = "",
                modifiedTime = "",
                category = CategoryModel(name = "test", colorHex = "#FFFFFF", iconId = 0)
            )
        )
        val result = useCase.invoke("1", list)
        assertEquals(false, result[0].isExpanded)
        assertEquals(false, result[1].isExpanded)
    }

    @Test
    fun noItemIsExpandedWhenIdIsNull() = runBlocking {
        val list = listOf(
            PasswordUIModel(
                id = "1", isExpanded = false,
                title = "title",
                isFavorite = false,
                organization = "Org",
                password = "Pass",
                organizationLogo = "",
                createdTime = "",
                modifiedTime = "",
                category = CategoryModel(name = "test", colorHex = "#FFFFFF", iconId = 0)
            ),
            PasswordUIModel(
                id = "2", isExpanded = false,
                title = "title",
                isFavorite = false,
                organization = "Org",
                password = "Pass",
                organizationLogo = "",
                createdTime = "",
                modifiedTime = "",
                category = CategoryModel(name = "test", colorHex = "#FFFFFF", iconId = 0)
            )
        )
        val result = useCase.invoke(null, list)
        assertEquals(false, result[0].isExpanded)
        assertEquals(false, result[1].isExpanded)
    }

    @Test
    fun noItemIsExpandedWhenIdDoesNotMatch() = runBlocking {
        val list = listOf(
            PasswordUIModel(
                id = "1", isExpanded = false,
                title = "title",
                isFavorite = false,
                organization = "Org",
                password = "Pass",
                organizationLogo = "",
                createdTime = "",
                modifiedTime = "",
                category = CategoryModel(name = "test", colorHex = "#FFFFFF", iconId = 0)
            ),
            PasswordUIModel(
                id = "2", isExpanded = false,
                title = "title",
                isFavorite = false,
                organization = "Org",
                password = "Pass",
                organizationLogo = "",
                createdTime = "",
                modifiedTime = "",
                category = CategoryModel(name = "test", colorHex = "#FFFFFF", iconId = 0)
            )
        )
        val result = useCase.invoke("3", list)
        assertEquals(false, result[0].isExpanded)
        assertEquals(false, result[1].isExpanded)
    }
}

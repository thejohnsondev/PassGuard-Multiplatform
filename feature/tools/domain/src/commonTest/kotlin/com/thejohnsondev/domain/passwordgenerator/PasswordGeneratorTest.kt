import com.thejohnsondev.domain.passwordgenerator.PasswordGenerator
import com.thejohnsondev.domain.passwordgenerator.PasswordType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PasswordGeneratorTest {

    private val passwordGenerator = PasswordGenerator(emptySet()) // No common passwords provided

    @Test
    fun `generate random password with default settings`() {
        val generated = passwordGenerator.generatePassword(PasswordType.RANDOM)
        assertEquals(12, generated.password.length)
    }

    @Test
    fun `generate random password with only lowercase`() {
        val generated = passwordGenerator.generatePassword(
            PasswordType.RANDOM,
            length = 10,
            includeLower = true,
            includeUpper = false,
            includeDigits = false,
            includeSpecial = false
        )
        assertTrue(generated.password.all { it.isLowerCase() })
    }

    @Test
    fun `generate random password with only uppercase`() {
        val generated = passwordGenerator.generatePassword(
            PasswordType.RANDOM,
            length = 10,
            includeLower = false,
            includeUpper = true,
            includeDigits = false,
            includeSpecial = false
        )
        assertTrue(generated.password.all { it.isUpperCase() })
    }

    @Test
    fun `generate random password with only digits`() {
        val generated = passwordGenerator.generatePassword(
            PasswordType.RANDOM,
            length = 10,
            includeLower = false,
            includeUpper = false,
            includeDigits = true,
            includeSpecial = false
        )
        assertTrue(generated.password.all { it.isDigit() })
    }

    @Test
    fun `generate random password with only special characters`() {
        val generated = passwordGenerator.generatePassword(
            PasswordType.RANDOM,
            length = 10,
            includeLower = false,
            includeUpper = false,
            includeDigits = false,
            includeSpecial = true
        )
        assertTrue(generated.password.all { "!@#\$%^&*()-_=+[]{}|;:'\",.<>?/".contains(it) })
    }

    @Test
    fun `generate human-readable password`() {
        val generated = passwordGenerator.generatePassword(PasswordType.HUMAN, length = 16)
        assertTrue(generated.password.isNotBlank())
        assertTrue(generated.password.contains("-")) // Expecting words joined by dashes
    }

    @Test
    fun `generate UUID password`() {
        val generated = passwordGenerator.generatePassword(PasswordType.UUID)
        assertEquals(36, generated.password.length)
        assertTrue(generated.password.count { it == '-' } == 4) // UUID format check
    }

    @Test
    fun `evaluate strength - very weak short password`() {
        val strength = passwordGenerator.evaluateStrength("abc")
        assertEquals(1, strength.level)
        assertEquals(
            "Extremely weak! Use at least 8 characters with mixed cases and symbols.",
            strength.suggestion
        )
    }

    @Test
    fun `evaluate strength - weak password`() {
        val strength = passwordGenerator.evaluateStrength("abcdef")
        assertTrue(strength.level in 2..3)
        assertEquals(
            "Too weak, add more characters and mix uppercase, lowercase, and numbers.",
            strength.suggestion
        )
    }

    @Test
    fun `evaluate strength - moderate password`() {
        val strength = passwordGenerator.evaluateStrength("Abc12345")
        assertTrue(strength.level in 4..6)
    }

    @Test
    fun `evaluate strength - good password`() {
        val strength = passwordGenerator.evaluateStrength("Abc12345!")
        assertTrue(strength.level in 7..8)
        assertEquals("Strong. Ensure it's not a common phrase.", strength.suggestion)
    }

    @Test
    fun `evaluate strength - very strong password`() {
        val strength = passwordGenerator.evaluateStrength("A1b2C3d4E5F6G@H!")
        assertEquals(10, strength.level)
        assertEquals("Extremely strong! This password is highly secure.", strength.suggestion)
    }

    @Test
    fun `empty character set returns empty password`() {
        val generated = passwordGenerator.generatePassword(
            PasswordType.RANDOM,
            length = 10,
            includeLower = false,
            includeUpper = false,
            includeDigits = false,
            includeSpecial = false
        )
        assertEquals("", generated.password)
    }
}
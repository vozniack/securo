package dev.vozniack.securo.core.utils

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class RegexExtensionTest {

    @Test
    fun `match email regex`() {
        assertTrue("john.doe@securo.com".matchesEmailRegex())
        assertFalse("john.doe@securo".matchesEmailRegex())
        assertFalse("securo.com".matchesEmailRegex())
    }

    @Test
    fun `match password regex`() {
        assertTrue("pasSword1!".matchesPasswordRegex())
        assertFalse("password1!".matchesPasswordRegex())
        assertFalse("pAssword!".matchesPasswordRegex())
        assertFalse("pAssword1".matchesPasswordRegex())
    }

    @Test
    fun `match language regex`() {
        assertTrue("en_EN".matchesLanguageRegex())
        assertFalse("en.EN".matchesLanguageRegex())
        assertFalse("en".matchesLanguageRegex())
    }
}

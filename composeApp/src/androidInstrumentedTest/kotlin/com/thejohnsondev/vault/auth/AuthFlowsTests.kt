package com.thejohnsondev.vault.auth

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.thejohnsondev.platform.di.AndroidPlatformDependency
import com.thejohnsondev.platform.di.PlatformModuleProvider
import com.thejohnsondev.vault.auth.selectvaulttype.SelectVaultTypeRobot
import com.thejohnsondev.vault.auth.welcome.WelcomeScreenRobot
import com.thejohnsondev.vault.vault.VaultRobot
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.thejohnsondev.vault.MainActivity
import org.thejohnsondev.vault.di.demoModules

class AuthFlowsTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        stopKoin()
        startKoin {
            androidContext(composeTestRule.activity)
            androidLogger()
            modules(
                demoModules + PlatformModuleProvider(
                    AndroidPlatformDependency(composeTestRule.activity)
                ).generatePlatformModule()
            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun testLocalVaultCreation() {
        with(WelcomeScreenRobot(composeTestRule)) {
            assertWelcomeScreenContent()
            clickGetStartedButton()
        }
        with(SelectVaultTypeRobot(composeTestRule)) {
            assertSelectVaultScreen()
            clickCloudVaultOption()
            assertCloudVaultOption()
            clickLocalVaultOption()
            assertLocalVaultOption()
            clickCreateLocalVaultButton()
        }
        with(VaultRobot(composeTestRule)) {
            assertVaultScreen()
        }
    }
}
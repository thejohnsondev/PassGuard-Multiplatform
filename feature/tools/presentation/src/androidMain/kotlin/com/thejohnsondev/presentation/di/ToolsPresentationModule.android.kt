package com.thejohnsondev.presentation.di

import com.thejohnsondev.presentation.passwordgenerator.PasswordGeneratorViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

actual val toolsPresentationModule = module {
    viewModelOf(::PasswordGeneratorViewModel)
}
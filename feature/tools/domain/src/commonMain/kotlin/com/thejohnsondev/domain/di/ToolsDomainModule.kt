package com.thejohnsondev.domain.di

import com.thejohnsondev.domain.passwordgenerator.PasswordGenerator
import org.koin.dsl.module

val toolsDomainModule = module {
    single { PasswordGenerator(emptySet()) }
}
package com.thejohnsondev.domain

import com.thejohnsondev.common.navigation.Routes

interface GetFirstScreenRouteUseCase {
    suspend operator fun invoke(): Routes
}
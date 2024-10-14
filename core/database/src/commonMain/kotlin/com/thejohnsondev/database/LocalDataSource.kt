package com.thejohnsondev.database


interface LocalDataSource {
    suspend fun logout(): Boolean
}
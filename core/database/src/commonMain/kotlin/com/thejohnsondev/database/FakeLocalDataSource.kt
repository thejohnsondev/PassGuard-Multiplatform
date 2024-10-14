package com.thejohnsondev.database

class FakeLocalDataSource : LocalDataSource {

    override suspend fun logout(): Boolean {
        return true
    }

}
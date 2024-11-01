package com.thejohnsondev.network

data class NetworkEngineConfig(
    val followRedirects: Boolean,
    val connectTimeout: Long,
    val connectionRequestTimeout: Long
) {
    companion object {
        val default = NetworkEngineConfig(
            followRedirects = true,
            connectTimeout = 10_000L,
            connectionRequestTimeout = 20_000L
        )
    }
}
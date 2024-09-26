package org.thejohnsondev.vaultdex

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
package org.thejohnsondev.vault

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
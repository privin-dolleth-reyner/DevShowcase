package com.cmp.showcase

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
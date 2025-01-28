package com.cmp.showcase

interface PlatformHandler{
    fun handleUrl(url: String)
    fun handleEmail(email: String)
}

expect fun getPlatformHandler(): PlatformHandler
package com.cmp.showcase

import android.content.Context
import android.content.Intent
import android.net.Uri

actual fun getPlatformHandler(): PlatformHandler {
    return AndroidPlatformHandler(ShowCaseApp.instance!!.applicationContext)
}

class AndroidPlatformHandler(private val context: Context) : PlatformHandler {

    override fun handleUrl(url: String) {
        println("in handler: $url")
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    override fun handleEmail(email: String) {
        println("in handler: $email")
        val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

}
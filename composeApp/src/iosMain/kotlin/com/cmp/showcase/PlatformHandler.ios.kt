package com.cmp.showcase

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

class IOSPlatformHandler : PlatformHandler {
    override fun handleUrl(url: String) {
        try {
            val nsUrl = NSURL(string = url)

            if (UIApplication.sharedApplication.canOpenURL(nsUrl)) {
                UIApplication.sharedApplication.openURL(nsUrl,options = emptyMap<Any?, Any>(), completionHandler = null)
            } else {
                println("No app available to handle URL")
            }
        } catch (e: Exception) {
            println("Error opening url: ${e.message}")
        }
    }

    override fun handleEmail(email: String) {
        try {
            val mailtoUrl = "mailto:$email"
            val url = NSURL(string = mailtoUrl)

            if (UIApplication.sharedApplication.canOpenURL(url)) {
                UIApplication.sharedApplication.openURL(url)
            } else {
                println("No email app available")
            }
        } catch (e: Exception) {
            println("Error opening email: ${e.message}")
        }
    }
}
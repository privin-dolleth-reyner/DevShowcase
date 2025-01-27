package com.cmp.showcase

import android.app.Application
import com.cmp.showcase.di.initKoin
import org.koin.android.ext.koin.androidContext

class ShowCaseApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@ShowCaseApp)
        }
    }
}
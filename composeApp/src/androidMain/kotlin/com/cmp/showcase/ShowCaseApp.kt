package com.cmp.showcase

import android.app.Application
import android.util.Log
import com.cmp.showcase.di.initKoin
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module

class ShowCaseApp: Application() {

    override fun onCreate() {
        super.onCreate()
        val firebaseApp = FirebaseApp.initializeApp(applicationContext)
        initKoin {
            androidContext(this@ShowCaseApp)
            koin.loadModules(
                modules = listOf(
                    module {
                        single {
                            AndroidPlatformHandler(this@ShowCaseApp)
                        }.bind<PlatformHandler>()
                    }
                )
            )

        }
        if (firebaseApp == null) {
            Log.e("Firebase", "FirebaseApp initialization failed")
        } else {
            Log.d("Firebase", "FirebaseApp initialized successfully")
        }
    }
}
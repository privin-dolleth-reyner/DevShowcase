package com.cmp.showcase

import android.app.Application
import android.util.Log
import com.cmp.showcase.di.initKoin
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext

class ShowCaseApp: Application() {

    companion object{
        var instance: ShowCaseApp? = null
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        val firebaseApp = FirebaseApp.initializeApp(applicationContext)
        initKoin {
            androidContext(this@ShowCaseApp)
        }
        if (firebaseApp == null) {
            Log.e("Firebase", "FirebaseApp initialization failed")
        } else {
            Log.d("Firebase", "FirebaseApp initialized successfully")
        }
    }
}
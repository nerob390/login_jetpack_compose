package com.mostafizur.loginwithjetpackcompose.di

import android.app.Application
import com.mostafizur.loginwithjetpackcompose.utils.AuthUser
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AuthUser.init(applicationContext)
        // Any additional setup if needed
    }
}
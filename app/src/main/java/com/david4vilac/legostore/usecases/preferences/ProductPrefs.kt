package com.david4vilac.legostore.usecases.preferences
import android.app.Application

class ProductPrefs: Application() {

    companion object{
        lateinit var prefs: Prefs
    }

    override fun onCreate() {
        prefs = Prefs(applicationContext)
        super.onCreate()
    }
}
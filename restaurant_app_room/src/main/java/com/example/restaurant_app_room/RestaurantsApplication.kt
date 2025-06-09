package com.example.restaurant_app_room

import android.app.Application
import android.content.Context

class RestaurantsApplication: Application() {
    init{app = this}
    companion object {
        private lateinit var app:RestaurantsApplication
        fun getAppContext(): Context = app.applicationContext
    }
}
package com.example.restaurant_app_clean_architecture.data

import com.example.restaurant_app_clean_architecture.RestaurantsApplication
import com.example.restaurant_app_clean_architecture.data.local.RestaurantsDb
import com.example.restaurant_app_clean_architecture.data.remote.RestaurantsApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestaurantsRepository {
    private var restInterface: RestaurantsApiService =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://restaurants-db-default-rtdb.firebaseio.com/")
            .build()
            .create(RestaurantsApiService::class.java)
    private var restaurantsDao = RestaurantsDb.getDaoInstance(
        RestaurantsApplication.getAppContext()
    )





}
package com.example.restaurant_app_room.network

import com.example.restaurant_app_room.db.Restaurant
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantsApiService {

    @GET("restaurants.json")
    suspend fun getRestaurants(): List<Restaurant>

    @GET("restaurants.json?orderBy=\"r_id\"")
    suspend fun getRestaurant(@Query("equalTo") id: Int):Map<String,Restaurant>
}
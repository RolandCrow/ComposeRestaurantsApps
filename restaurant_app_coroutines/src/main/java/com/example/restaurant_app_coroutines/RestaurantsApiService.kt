package com.example.restaurant_app_coroutines

import retrofit2.http.GET

interface RestaurantsApiService {
    @GET("restaurants.json")
    suspend fun getRestaurants(): List<Restaurant>
}
package com.example.restaurant_app_retrofit

import retrofit2.Call
import retrofit2.http.GET

interface RestaurantsApiService {
    @GET("restaurants.json")
    fun getRestaurants(): Call<List<Restaurant>>
}
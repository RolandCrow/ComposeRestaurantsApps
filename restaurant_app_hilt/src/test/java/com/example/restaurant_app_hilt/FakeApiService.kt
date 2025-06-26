package com.example.restaurant_app_hilt

import com.example.restaurant_app_hilt.data.remote.RemoteRestaurant
import com.example.restaurant_app_hilt.data.remote.RestaurantsApiService
import kotlinx.coroutines.delay

class FakeApiService: RestaurantsApiService {
    override suspend fun getRestaurants(): List<RemoteRestaurant> {
        delay(1000)
        return DummyContent.getRemoteRestaurants()
    }

    override suspend fun getRestaurant(id: Int): Map<String, RemoteRestaurant> {
        TODO("Not yet implemented")
    }

}
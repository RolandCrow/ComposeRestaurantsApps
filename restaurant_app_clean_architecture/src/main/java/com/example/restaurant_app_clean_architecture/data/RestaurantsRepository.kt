package com.example.restaurant_app_clean_architecture.data

import com.example.restaurant_app_clean_architecture.RestaurantsApplication
import com.example.restaurant_app_clean_architecture.data.local.LocalRestaurant
import com.example.restaurant_app_clean_architecture.data.local.PartialLocalRestaurant
import com.example.restaurant_app_clean_architecture.data.local.RestaurantsDb
import com.example.restaurant_app_clean_architecture.data.remote.RestaurantsApiService
import com.example.restaurant_app_clean_architecture.domain.Restaurant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.UnknownHostException

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

    suspend fun toggleFavoriteRestaurant(
        id: Int,
        value: Boolean
    ) = withContext(Dispatchers.IO) {
        restaurantsDao.update(
            PartialLocalRestaurant(id, isFavorite = value)
        )
    }

    suspend fun getRestaurants(): List<Restaurant> {
        return withContext(Dispatchers.IO) {
            return@withContext restaurantsDao.getAll().map {
                Restaurant(it.id,it.title,it.description, it.isFavorite)
            }
        }
    }

    suspend fun loadRestaurants() {
        return withContext(Dispatchers.IO) {
            try {
                refreshCache()
            } catch (e: Exception) {
                when(e) {
                is UnknownHostException,
                is ConnectException,
                is HttpException -> {
                    if(restaurantsDao.getAll().isEmpty()) {
                        throw Exception(
                            "Something went wrong. " +
                                    "We have no data."
                        )
                    }
                }
                }
            }
        }
    }

    private suspend fun refreshCache() {
        val removeRestaurants = restInterface.getRestaurants()
        val favoriteRestaurants = restaurantsDao.getAllFavored()
        restaurantsDao.addAll(removeRestaurants.map {
            LocalRestaurant(it.id, it.title,it.description, false)
        })
        restaurantsDao.updateAll(
            favoriteRestaurants.map {
                PartialLocalRestaurant(id=it.id, isFavorite = true)
            }
        )
    }



}
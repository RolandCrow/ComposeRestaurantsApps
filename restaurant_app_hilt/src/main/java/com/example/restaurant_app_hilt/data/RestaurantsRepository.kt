package com.example.restaurant_app_hilt.data

import com.example.restaurant_app_hilt.data.di.IoDispatcher
import com.example.restaurant_app_hilt.data.local.LocalRestaurant
import com.example.restaurant_app_hilt.data.local.PartialLocalRestaurant
import com.example.restaurant_app_hilt.data.local.RestaurantsDao
import com.example.restaurant_app_hilt.data.remote.RestaurantsApiService
import com.example.restaurant_app_hilt.domain.Restaurant
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestaurantsRepository @Inject constructor(
    private val restInterface: RestaurantsApiService,
    private val restaurantsDao: RestaurantsDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend fun toggleFavoriteRestaurant(
        id: Int,
        value: Boolean
    ) = withContext(dispatcher) {
        restaurantsDao.update(
            PartialLocalRestaurant(id, isFavorite = value)
        )
    }

    suspend fun getRestaurants(): List<Restaurant> {
        return withContext(dispatcher) {
            return@withContext restaurantsDao.getAll().map {
                Restaurant(it.id,it.title,it.description, it.isFavorable)
            }
        }
    }

    suspend fun loadRestaurants() {
        return withContext(dispatcher) {
            try {
                refreshCache()
            } catch (e: Exception) {
                when(e) {
                    is UnknownHostException,
                    is ConnectException,
                    is HttpException -> {
                        if(restaurantsDao.getAll().isEmpty()) {
                            "Something went wrong. " +
                                    "We have no data."
                        }
                    }
                }
            }
        }
    }

    private suspend fun refreshCache() {
        val remoteRestaurants = restInterface.getRestaurants()
        val favoriteRestaurants = restaurantsDao.getAllFavored()
        restaurantsDao.addAll(remoteRestaurants.map {
            LocalRestaurant(it.id, it.title, it.description, false)
        })
        restaurantsDao.updateAll(
            favoriteRestaurants.map {
                PartialLocalRestaurant(id = it.id, isFavorite = true)
            }
        )
    }
}
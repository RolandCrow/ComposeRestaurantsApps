package com.example.restaurant_app_retrofit

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestaurantsViewModel(private val stateHandle: SavedStateHandle): ViewModel() {
    private var resInterface: RestaurantsApiService
    private lateinit var restaurantCall: Call<List<Restaurant>>
    val state = mutableStateOf(emptyList<Restaurant>())

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://restaurants-db-default-rtdb.firebaseio.com/")
            .build()

        resInterface = retrofit.create(RestaurantsApiService::class.java)
        getRestaurants()
    }

    fun toggleFavorite(id: Int) {
        val restaurants = state.value.toMutableStateList()
        val itemIndex = restaurants.indexOfFirst { it.id == id }
        val item = restaurants[itemIndex]
        restaurants[itemIndex] = item.copy(isFavorite = !item.isFavorite)
        storeSelection(restaurants[itemIndex])
        state.value = restaurants
    }

    private fun storeSelection(item: Restaurant) {
        val savedToggled = stateHandle.get<List<Int>?>(FAVORITES)
            .orEmpty().toMutableList()
        if (item.isFavorite) savedToggled.add(item.id)
        else savedToggled.remove(item.id)
        stateHandle[FAVORITES] = savedToggled
    }

    private fun getRestaurants() {
        restaurantCall = resInterface.getRestaurants()
        restaurantCall.enqueue(
            object: Callback<List<Restaurant>> {
                override fun onResponse(
                    call: Call<List<Restaurant>>,
                    response: Response<List<Restaurant>>
                ) {
                    response.body()?.let { restaurants->
                        state.value = restaurants.restoreSelection()
                    }
                }

                override fun onFailure(p0: Call<List<Restaurant>>, p1: Throwable) {
                    p1.printStackTrace()
                }
            }
        )
    }

    private fun List<Restaurant>.restoreSelection(): List<Restaurant> {
        stateHandle.get<List<Int>?>(FAVORITES)?.let {selectIds ->
            val restaurantMap = this.associateBy { it.id }
            selectIds.forEach { id->
                restaurantMap[id]?.isFavorite = true
            }
            return restaurantMap.values.toList()
        }
        return this
    }


    override fun onCleared() {
        super.onCleared()
        restaurantCall.cancel()
    }


    companion object {
        const val FAVORITES = "favorites"
    }
}
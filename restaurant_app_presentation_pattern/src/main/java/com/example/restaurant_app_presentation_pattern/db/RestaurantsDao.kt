package com.example.restaurant_app_presentation_pattern.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.restaurant_app_presentation_pattern.model.PartialRestaurant
import com.example.restaurant_app_presentation_pattern.model.Restaurant

@Dao
interface RestaurantsDao {
    @Query("SElECT * FROM restaurants")
    suspend fun getAll(): List<Restaurant>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(restaurants: List<Restaurant>)

    @Update(entity = Restaurant::class)
    suspend fun update(partialRestaurant: PartialRestaurant)

    @Update(entity = Restaurant::class)
    suspend fun updateAll(partialRestaurant: List<PartialRestaurant>)

    @Query("SELECT * FROM restaurants WHERE is_favorite=1")
    suspend fun getAllFavored(): List<Restaurant>
}
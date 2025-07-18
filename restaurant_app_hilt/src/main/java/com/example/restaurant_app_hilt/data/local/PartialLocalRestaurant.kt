package com.example.restaurant_app_hilt.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
class PartialLocalRestaurant(
    @ColumnInfo(name = "r_id")
    val id: Int,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean
)
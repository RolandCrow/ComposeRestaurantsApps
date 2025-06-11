package com.example.restaurant_app_room.db

import androidx.room.ColumnInfo

class PartialRestaurant(
    @ColumnInfo(name = "r_id")
    val id: Int,

    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean
)
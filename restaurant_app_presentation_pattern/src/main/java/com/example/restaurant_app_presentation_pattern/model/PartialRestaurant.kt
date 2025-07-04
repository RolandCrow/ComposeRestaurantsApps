package com.example.restaurant_app_presentation_pattern.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class PartialRestaurant(
    @ColumnInfo(name = "r_id")
    val id: Int,

    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean
)

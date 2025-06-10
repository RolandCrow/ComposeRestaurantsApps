package com.example.restaurant_app_room

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.ContentAlpha
import androidx.wear.compose.material.LocalContentAlpha
import com.example.restaurant_app_room.bd.Restaurant
import com.example.restaurant_app_room.viewmodel.RestaurantsViewModel
import androidx.compose.foundation.lazy.items

@Composable
fun RestaurantsScreen(onItemClick: (id: Int) -> Unit) {
    val viewModel: RestaurantsViewModel = viewModel()
    LazyColumn(
        contentPadding = PaddingValues(
            vertical = 8.dp,
            horizontal = 8.dp
        )
    ) {
        items(viewModel.state.value) { restaurant ->
            RestaurantItem(restaurant,
                onFavoriteClick = { id, oldValue ->
                    viewModel.toggleFavorite(id, oldValue) },
                onItemClick = { id -> onItemClick(id) })
        }
    }
}

@Composable
fun RestaurantItem(
    item: Restaurant,
    onFavoriteClick: (id: Int, oldValue: Boolean)-> Unit,
    onItemClick: (id: Int) -> Unit
) {
    val icon = if(item.isFavorite)
        Icons.Filled.Favorite
    else
        Icons.Filled.FavoriteBorder
    Card(
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        modifier = Modifier
            .padding(8.dp)
            .clickable { onItemClick(item.id) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            RestaurantIcon(Icons.Filled.Place, Modifier.weight(0.15f))
            RestaurantDetails(item.title, item.description,Modifier.weight(0.7f))
            RestaurantIcon(icon, Modifier.weight(0.15f)) {
                onFavoriteClick(item.id,item.isFavorite)
            }
        }
    }
}


@Composable
fun RestaurantIcon(icon: ImageVector, modifier: Modifier, onClick: () -> Unit = {}) {
    Image(
        imageVector = icon,
        contentDescription = "Restaurant icon",
        modifier = modifier
            .padding(8.dp)
            .clickable { onClick() }
    )
}

@Composable
fun RestaurantDetails(
    title: String,
    description: String,
    modifier: Modifier,
    horizontal: Alignment.Horizontal = Alignment.Start
) {
    Column(
        modifier = modifier,
        horizontalAlignment = horizontal
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall
        )
        CompositionLocalProvider(
            LocalContentAlpha provides ContentAlpha.medium
        ) {
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
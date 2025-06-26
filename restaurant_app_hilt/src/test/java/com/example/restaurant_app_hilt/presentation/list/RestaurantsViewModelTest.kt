package com.example.restaurant_app_hilt.presentation.list

import com.example.restaurant_app_hilt.DummyContent
import com.example.restaurant_app_hilt.FakeApiService
import com.example.restaurant_app_hilt.FakeRoomDao
import com.example.restaurant_app_hilt.data.RestaurantsRepository
import com.example.restaurant_app_hilt.domain.GetInitialRestaurantsUseCase
import com.example.restaurant_app_hilt.domain.GetSortedRestaurantsUseCase
import com.example.restaurant_app_hilt.domain.ToggleRestaurantUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class RestaurantsViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Test
    fun initialState_isProduced() = scope.runTest {
        val viewModel = getViewModel()
        val initialState = viewModel.state.value
        assert(
            initialState == RestaurantsScreenState(
                restaurants = emptyList(),
                isLoading = true,
                error = null
            )
        )
    }

    @Test
    fun stateWithContent_isProduced() = scope.runTest {
        val testVm = getViewModel()
        advanceUntilIdle()
        val currentState = testVm.state.value
        assert(
            currentState == RestaurantsScreenState(
                restaurants = DummyContent.getDomainRestaurants(),
                isLoading = false,
                error = null
            )
        )
    }

    private fun getViewModel(): RestaurantsViewModel {
        val restaurantsRepository = RestaurantsRepository(FakeApiService(), FakeRoomDao(), dispatcher)
        val getSortedRestaurantsUseCase = GetSortedRestaurantsUseCase(restaurantsRepository)
        val getInitialRestaurantsUseCase = GetInitialRestaurantsUseCase(restaurantsRepository,getSortedRestaurantsUseCase)
        val toggleRestaurantsUseCase = ToggleRestaurantUseCase(restaurantsRepository, getSortedRestaurantsUseCase)

        return RestaurantsViewModel(
            getInitialRestaurantsUseCase,
            toggleRestaurantsUseCase,
            dispatcher
        )
    }
}
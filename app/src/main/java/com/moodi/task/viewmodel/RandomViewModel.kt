package com.moodi.task.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moodi.domain.usecase.RandomGiphyUseCase
import com.moodi.domain.util.ERROR_NETWORK
import com.moodi.domain.util.Resource
import com.moodi.task.dispatcher.PeriodicDispatcher
import com.moodi.task.state.random.RandomState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * This viewmodel is used to generate random gif periodically
 * It uses [PeriodicDispatcher] is responsible for generating random gif periodically
 * It uses [RandomGiphyUseCase] to get the random gif
 *
 *
 * @property useCase [RandomGiphyUseCase]
 * @property dispatcher [PeriodicDispatcher]
 *
 * This view model holds stateful data and it is used to handle the UI changes based on the state
 * It transform the data from the repository to [RandomState]
 *
 *
 */
@HiltViewModel
class RandomViewModel @Inject constructor(
    private val useCase: RandomGiphyUseCase,
    private val dispatcher: PeriodicDispatcher
) : ViewModel() {

    private val _dataState = MutableStateFlow<RandomState>(RandomState.Empty)
    val dataState = _dataState.asStateFlow()

    /**
     * This method is used to generate random gif periodically respect to the INTERVAL set in PeriodicDispatcher class
     */
    fun generateRandomGif() {
        dispatcher.runPeriodicTask(viewModelScope) {
            randomGif()
        }
    }

    /**
     * This method is used to get the random gif from the repository.
     */
    fun randomGif() {
        viewModelScope.launch {
            useCase().collect { result ->
                when (result) {
                    is Resource.Failure ->
                        _dataState.value =
                            RandomState.NetworkError(result.errorCode ?: ERROR_NETWORK)

                    is Resource.Loading -> _dataState.value = RandomState.Loading
                    is Resource.Success -> {
                        _dataState.value = RandomState.Success(result.data)
                    }
                }
            }
        }
    }

    /**
     * This method is used to stop the task when the viewmodel is destroyed.
     */
    override fun onCleared() {
        super.onCleared()
        dispatcher.stop()
    }
}

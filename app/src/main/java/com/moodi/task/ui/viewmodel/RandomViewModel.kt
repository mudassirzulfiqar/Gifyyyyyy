package com.moodi.task.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moodi.task.data.ERROR_NETWORK
import com.moodi.task.data.Resource
import com.moodi.task.data.repository.GiphyRepository
import com.moodi.task.ui.dispatcher.PeriodicDispatcher
import com.moodi.task.ui.sate.RandomState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This viewmodel is used to generate random gif periodically
 * It uses [PeriodicDispatcher] is responsible for generating random gif periodically
 * It uses [GiphyRepository] to get the random gif
 *
 *
 * @property giphyRepository
 * @property dispatcher
 *
 * This view model holds stateful data and it is used to handle the UI changes based on the state
 * It transform the data from the repository to [RandomState]
 *
 *
 */
@HiltViewModel
class RandomViewModel @Inject constructor(
    private val giphyRepository: GiphyRepository,
    private val dispatcher: PeriodicDispatcher
) : ViewModel() {

    private val _dataState = MutableStateFlow<RandomState>(RandomState.Empty)
    val dataState = _dataState.asStateFlow()

    init {
        generateRandomGif()
    }

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
            giphyRepository.getRandomGif().collect { result ->
                when (result) {
                    is Resource.Failure -> _dataState.value =
                        RandomState.NetworkError(result.errorCode ?: ERROR_NETWORK)

                    is Resource.Loading -> _dataState.value = RandomState.Loading
                    is Resource.Success -> {
                        _dataState.value = RandomState.Success(result.data!!)
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
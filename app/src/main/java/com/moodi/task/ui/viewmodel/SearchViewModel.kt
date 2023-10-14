package com.moodi.task.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moodi.task.data.Resource
import com.moodi.task.ui.sate.search.SearchState
import com.moodi.task.ui.sate.search.UiEffect
import com.moodi.task.ui.sate.search.UiEvent
import com.moodi.task.usecase.SearchGifUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This viewmodel is used to search gif based on the query
 * It uses GiphyRepository to get the search results
 * It holds stateful data and it is used to handle the UI changes based on the state
 *
 * Used in [SearchFragment] and in [HomeActivity]
 *
 * @property giphyRepository
 *
 */
@HiltViewModel
class SearchViewModel
@Inject constructor(
    private val searchGifUseCase: SearchGifUseCase
) :
    ViewModel() {

    private val _dataState = MutableStateFlow(SearchState())
    val dataState = _dataState.asStateFlow()


    private val _uiEffect = MutableSharedFlow<UiEffect>()
    val uiEffectState = _uiEffect.asSharedFlow()


    fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.Search -> search(event.searchQuery)
            is UiEvent.ClearSearch -> clearSearch()
            is UiEvent.NavigateToDetail -> TODO()
        }
    }


    /**
     * This method is used to search gif based on the query
     * @param query String
     */
    private fun search(query: String) {
        viewModelScope.launch {
            if (query.trim().isEmpty()) {
                _uiEffect.emit(UiEffect.ShowSnackBar("Please enter a search query"))
                return@launch
            }
            if (query.trim().length > 2) {
                searchGifUseCase(query).onEach {
                    when (it) {
                        is Resource.Failure -> {
                            _dataState.value.apply { copy(error = it.errorCode.toString()) }
                            _uiEffect.emit(UiEffect.ShowSnackBar(it.errorCode.toString()))
                        }

                        is Resource.Loading -> _dataState.value.apply {
                            copy(loading = true)
                        }

                        is Resource.Success -> _dataState.value.apply {
                            copy(data = it.data!!)
                        }
                    }
                }.launchIn(this)
            }
        }

    }

    /**
     * This method shared SearchState.Empty and on SearchFragment it is used to clear the search results as per requirments.
     */
    private fun clearSearch() {
        _dataState.value.apply {
            copy(data = emptyList())
        }
    }


}
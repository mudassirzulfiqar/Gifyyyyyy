package com.moodi.task.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moodi.domain.usecase.SearchGiphyUseCase
import com.moodi.domain.util.Resource
import com.moodi.task.sate.search.SearchState
import com.moodi.task.sate.search.UiEffect
import com.moodi.task.sate.search.UiEvent
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
    private val searchUseCase: SearchGiphyUseCase
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
                searchUseCase(query, 20).onEach {
                    when (it) {
                        is Resource.Failure -> {
                            _dataState.value.apply { copy(error = it.errorCode.toString()) }
                            _uiEffect.emit(UiEffect.ShowSnackBar(it.errorCode.toString()))
                        }

                        is Resource.Loading -> {
                            _dataState.value = _dataState.value.copy(loading = true)
                        }

                        is Resource.Success -> {
                            _dataState.value = _dataState.value.copy(data = it.data!!)
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
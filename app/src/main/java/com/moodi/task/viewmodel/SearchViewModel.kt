package com.moodi.task.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moodi.common.DispatcherProvider
import com.moodi.domain.usecase.SearchGiphyUseCase
import com.moodi.domain.util.Resource
import com.moodi.task.state.search.SearchState
import com.moodi.task.state.search.UiEffect
import com.moodi.task.state.search.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * This [SearchViewModel] is used to search gif based on the query
 * It uses GiphyRepository to get the search results
 * It holds stateful data and it is used to handle the UI changes based on the state
 *
 * @property searchUseCase [SearchGiphyUseCase]
 * @property defaultDispatcher [DispatcherProvider]
 *
 */
@HiltViewModel
class SearchViewModel
@Inject constructor(
    private val searchUseCase: SearchGiphyUseCase,
    private val defaultDispatcher: DispatcherProvider
) : ViewModel() {

    private val _dataState = MutableStateFlow(SearchState())
    val dataState = _dataState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<UiEffect>()
    val uiEffectState = _uiEffect.asSharedFlow()

    fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.Search -> search(event.searchQuery)
            is UiEvent.ClearSearch -> clearSearch()
            is UiEvent.ItemClicked -> performEffect(UiEffect.NavigateToDetail(event.appModel))
        }
    }

    private fun performEffect(event: UiEffect) {
        viewModelScope.launch {
            _uiEffect.emit(event)
        }
    }

    /**
     * This method is used to search gif based on the query
     * @param query String
     */
    private fun search(query: String) {
        viewModelScope.launch(defaultDispatcher.main) {
            if (query.trim().isEmpty()) {
                performEffect(UiEffect.ShowSnackBar("Please enter a search query"))
                return@launch
            }
            if (query.trim().length > 2) {
                searchUseCase(query, 20).collect {
                    when (it) {
                        is Resource.Failure -> {
                            _dataState.value =
                                _dataState.value.copy(error = it.errorCode.toString())
                            _uiEffect.emit(UiEffect.ShowSnackBar(it.errorCode.toString()))
                        }

                        is Resource.Loading -> {
                            _dataState.value = _dataState.value.copy(loading = true)
                        }

                        is Resource.Success -> {
                            it.data?.let { data ->
                                _dataState.value = _dataState.value.copy(data = data)
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * This method shared SearchState.Empty and on SearchFragment it is used to clear the search results as per requirements.
     */
    private fun clearSearch() {
        _dataState.value = _dataState.value.copy(data = emptyList())
    }
}

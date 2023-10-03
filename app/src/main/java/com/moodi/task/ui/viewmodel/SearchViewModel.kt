package com.moodi.task.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moodi.task.data.ERROR_NETWORK
import com.moodi.task.data.Resource
import com.moodi.task.data.remote.api.WebApi.Companion.SEARCH_LIMIT_SIZE
import com.moodi.task.data.repository.GiphyRepository
import com.moodi.task.ui.sate.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
class SearchViewModel @Inject constructor(private val giphyRepository: GiphyRepository) :
    ViewModel() {

    private val _dataState = MutableStateFlow<SearchState>(SearchState.Empty)
    val dataState = _dataState.asStateFlow()

    /**
     * This method is used to search gif based on the query
     * @param query String
     */
    fun search(query: String) {

        viewModelScope.launch {
            if (query.trim().isEmpty()) {
                _dataState.value = SearchState.Empty
            }
            if (query.trim().length < 3) {
                return@launch
            }
            giphyRepository.searchGif(query, SEARCH_LIMIT_SIZE).collect { result ->
                when (result) {
                    is Resource.Failure -> _dataState.value =
                        SearchState.NetworkError(result.errorCode ?: ERROR_NETWORK)

                    is Resource.Loading -> _dataState.value = SearchState.Loading
                    is Resource.Success -> {
                        if (result.data.isNullOrEmpty()) {
                            _dataState.value = SearchState.NoSearchResults
                        } else {
                        }
                        _dataState.value = SearchState.Success(result.data!!)
                    }

                }
            }
        }
    }

    /**
     * This method shared SearchState.Empty and on SearchFragment it is used to clear the search results as per requirments.
     */
    fun clearSearch() {
        _dataState.value = SearchState.Empty
    }


}
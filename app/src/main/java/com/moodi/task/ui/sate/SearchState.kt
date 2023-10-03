package com.moodi.task.ui.sate

import com.moodi.task.data.local.GiphyAppModel

/**
 * This class is used to define the state of the SearchFragment
 * The state is used to handle the UI changes based on the state
 */
sealed class SearchState {
    /**
     * Empty state is used to show the empty view or start state or initial state.
     */
    object Empty : SearchState()

    /**
     * NoSearchResults state is used to show the no search results view.
     * possible use to show a message if no search results found.
     */
    object NoSearchResults : SearchState()

    /**
     * Loading state is used to show the loading view.
     */
    object Loading : SearchState()

    /**
     * Success data class holds the state of data.
     * It holds the list of GiphyAppModel
     * @param data List<GiphyAppModel>
     */
    data class Success(val data: List<GiphyAppModel>) : SearchState()

    /**
     * NetworkError data class holds the state of network error with parameter code as Int.
     * @param code Int
     */
    data class NetworkError(val code: Int) : SearchState()

}

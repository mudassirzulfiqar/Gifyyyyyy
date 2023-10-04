package com.moodi.task.ui.sate.search

import com.moodi.task.data.local.GiphyAppModel

/**
 * This class is used to define the state of the SearchFragment
 * The state is used to handle the UI changes based on the state
 */
data class SearchState(
    val loading: Boolean = false,
    val data: List<GiphyAppModel> = emptyList(),
    val error: String = ""
)

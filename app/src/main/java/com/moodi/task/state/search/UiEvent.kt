package com.moodi.task.state.search

import com.moodi.domain.model.GiphyAppModel

/**
 * This class represents the events that are triggered from the UI by User
 */
sealed class UiEvent {
    class ItemClicked(val appModel: GiphyAppModel) : UiEvent()
    class Search(val searchQuery: String) : UiEvent()
    object ClearSearch : UiEvent()
}

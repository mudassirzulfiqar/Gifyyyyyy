package com.moodi.task.ui.sate.search

sealed class UiEvent {
    object NavigateToDetail : UiEvent()
    class Search(val searchQuery: String) : UiEvent()
    object ClearSearch : UiEvent()

}
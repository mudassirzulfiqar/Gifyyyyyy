package com.moodi.task.sate.search

sealed class UiEvent {
    object NavigateToDetail : UiEvent()
    class Search(val searchQuery: String) : UiEvent()
    object ClearSearch : UiEvent()
}

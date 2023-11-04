package com.moodi.task.state.search

sealed class UiEvent {
    object NavigateToDetail : UiEvent()
    class Search(val searchQuery: String) : UiEvent()
    object ClearSearch : UiEvent()
}

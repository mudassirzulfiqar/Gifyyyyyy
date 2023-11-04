package com.moodi.task.state.search

sealed class UiEffect {
    object NavigateToDetail : UiEffect()
    class ShowSnackBar(val message: String) : UiEffect()
}

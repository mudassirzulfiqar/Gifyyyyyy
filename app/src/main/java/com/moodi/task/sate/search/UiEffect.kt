package com.moodi.task.sate.search

sealed class UiEffect {
    object NavigateToDetail : UiEffect()
    class ShowSnackBar(val message: String) : UiEffect()
}

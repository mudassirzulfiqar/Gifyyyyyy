package com.moodi.task.state.search

import com.moodi.domain.model.GiphyAppModel

/**
 * This class represents the side effects that are triggered from the UI once.
 */
sealed class UiEffect {
    class NavigateToDetail(val giphyAppModel: GiphyAppModel) : UiEffect()
    class ShowSnackBar(val message: String) : UiEffect()
}

package com.moodi.task.sate.random

import com.moodi.domain.model.GiphyAppModel


/**
 * This class is used to define the state of the RandomFragment
 * The state is used to handle the UI changes based on the state
 */
sealed class RandomState {
    /**
     * Empty state is used to show the empty view or start state.
     * It is used to clear the data from the recyclerview on initial state.
     */
    object Empty : RandomState()

    /**
     * Loading state is used to show the loading view.
     */
    object Loading : RandomState()

    /**
     * Success data class holds the state of data.
     * It holds the GiphyAppModel
     * @param data GiphyAppModel
     */
    data class Success(val data: GiphyAppModel) : RandomState()

    /**
     * NetworkError data class holds the state of network error with parameter message as Int.
     * Reason to use Int is to show the different error messages based on the error code.
     * @param errorCode Int
     */
    data class NetworkError(val errorCode: Int) : RandomState()
}
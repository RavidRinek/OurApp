package com.our.app.base

sealed class LoadingStatus {

    /**
     * Means that we are currently loading data from a data source.
     */
    data class Loading(val data: DisplayProgressTypes = DisplayProgressTypes.NONE) : LoadingStatus()

    /**
     * Means that data loading which was initiated previously is failed.
     */
    object LoadingSuccess : LoadingStatus()

    /**
     * Means that data loading which was initiated previously is successfully finished.
     */
    data class LoadingError(
        val errorMessage: String? = null,
        val throwable: Throwable
    ) : LoadingStatus()
}
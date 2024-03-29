package com.example.moviesimdb.ui.models

import com.example.moviesimdb.ui.RVItem

sealed interface MoviesCastState {

    object Loading : MoviesCastState

    data class Content(
        val fullTitle: String,
        // Поменяли тип ячеек на более общий
        val items: List<RVItem>,
    ) : MoviesCastState

    data class Error(
        val message: String,
    ) : MoviesCastState

}
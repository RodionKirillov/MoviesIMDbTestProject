package com.example.moviesimdb.ui.models

import com.example.moviesimdb.domain.models.Result

sealed interface NameState {
    object Loading : NameState

    data class Content(
        val names: List<Result>
    ) : NameState

    data class Error(
        val errorMessage: String
    ) : NameState

    data class Empty(
        val message: String
    ) : NameState
}
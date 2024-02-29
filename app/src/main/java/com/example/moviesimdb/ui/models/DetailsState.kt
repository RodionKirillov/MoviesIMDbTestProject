package com.example.moviesimdb.ui.models

import com.example.moviesimdb.domain.models.Movie
import com.example.moviesimdb.domain.models.MovieDetails

sealed interface DetailsState {

    data class Content(
        val details: MovieDetails
    ) : DetailsState

    data class Error(
        val errorMessage: String
    ) : DetailsState
}
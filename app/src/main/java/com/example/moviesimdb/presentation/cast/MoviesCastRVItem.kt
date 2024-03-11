package com.example.moviesimdb.presentation.cast

import com.example.moviesimdb.domain.models.MovieCastPerson
import com.example.moviesimdb.ui.RVItem

sealed interface MoviesCastRVItem: RVItem {

    data class HeaderItem(
        val headerText: String,
    ) : MoviesCastRVItem

    data class PersonItem(
        val data: MovieCastPerson,
    ) : MoviesCastRVItem

}
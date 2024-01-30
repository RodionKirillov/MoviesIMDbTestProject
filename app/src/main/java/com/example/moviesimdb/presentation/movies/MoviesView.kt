package com.example.moviesimdb.presentation.movies

import com.example.moviesimdb.ui.models.MoviesState

interface MoviesView {

    // Методы, меняющие внешний вид экрана

    fun render(state: MoviesState)

    // Методы «одноразовых событий»

    fun showToast(additionalMessage: String)

}
package com.example.moviesimdb.presentation.movies

import com.example.moviesimdb.ui.models.MoviesState
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface MoviesView : MvpView {

    // Методы, меняющие внешний вид экрана
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun render(state: MoviesState)

    // Методы «одноразовых событий»
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showToast(additionalMessage: String)

}
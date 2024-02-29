package com.example.moviesimdb.presentation.poster

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesimdb.domain.api.MoviesInteractor
import com.example.moviesimdb.domain.models.MovieDetails
import com.example.moviesimdb.ui.models.DetailsState

class AboutViewModel(
    private val movieId: String,
    private val moviesInteractor: MoviesInteractor,
) : ViewModel() {

    init {
        moviesInteractor.getMovieDetails(
            movieId,
            object : MoviesInteractor.MoviesDetailsConsumer {

                override fun consume(movieDetails: MovieDetails?, errorMessage: String?) {
                    if (movieDetails != null) {
                        renderState(DetailsState.Content(movieDetails))
                    } else {
                        renderState(DetailsState.Error(errorMessage ?: "Что-то поошло не так"))
                    }
                }
            })
    }

    private val stateLiveData = MutableLiveData<DetailsState>()
    fun observeState(): LiveData<DetailsState> = stateLiveData

    private fun renderState(state: DetailsState) {
        stateLiveData.postValue(state)
    }


}
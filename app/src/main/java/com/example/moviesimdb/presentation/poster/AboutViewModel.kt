package com.example.moviesimdb.presentation.poster

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesimdb.domain.api.MoviesInteractor
import com.example.moviesimdb.domain.models.MovieDetails
import com.example.moviesimdb.ui.models.DetailsState
import kotlinx.coroutines.launch

class AboutViewModel(
    private val movieId: String,
    private val moviesInteractor: MoviesInteractor,
) : ViewModel() {

    init {
        viewModelScope.launch {
            moviesInteractor.getMovieDetails(movieId)
                .collect { pair ->

                    if (pair.first != null) {
                        renderState(DetailsState.Content(pair.first!!))
                    } else {
                        renderState(DetailsState.Error(pair.second ?: "Что-то поошло не так"))
                    }
                }
        }
    }

    private val stateLiveData = MutableLiveData<DetailsState>()
    fun observeState(): LiveData<DetailsState> = stateLiveData

    private fun renderState(state: DetailsState) {
        stateLiveData.postValue(state)
    }


}
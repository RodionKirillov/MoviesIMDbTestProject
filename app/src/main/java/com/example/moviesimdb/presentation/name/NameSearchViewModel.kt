package com.example.moviesimdb.presentation.name

import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesimdb.domain.api.MoviesInteractor
import com.example.moviesimdb.domain.models.Result
import com.example.moviesimdb.ui.models.NameState
import kotlinx.coroutines.launch

class NameSearchViewModel(private val interactor: MoviesInteractor) : ViewModel() {

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private val SEARCH_REQUEST_TOKEN = Any()

    }

    private val handler = Handler(Looper.getMainLooper())

    private val stateLiveData = MutableLiveData<NameState>()
    fun observeState(): LiveData<NameState> = stateLiveData

    private var latestSearchText: String? = null

    override fun onCleared() {
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
    }

    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }

        this.latestSearchText = changedText
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)

        val searchRunnable = Runnable { searchRequest(changedText) }

        val postTime = SystemClock.uptimeMillis() + SEARCH_DEBOUNCE_DELAY
        handler.postAtTime(
            searchRunnable,
            SEARCH_REQUEST_TOKEN,
            postTime
        )
    }

    private fun searchRequest(searchText: String) {
        if (searchText.isNotEmpty()) {
            renderState(NameState.Loading)

            viewModelScope.launch {
                interactor
                    .searchName(searchText)
                    .collect { pair ->
                        processResult(pair.first, pair.second)
                    }
            }
        }
    }

    private fun processResult(foundNames: List<Result>?, errorMessage: String?) {
        val persons = mutableListOf<Result>()

        if (foundNames != null) {
            persons.addAll(foundNames)
        }

        when {
            errorMessage != null -> {
                renderState(
                    NameState.Error(
                        errorMessage = "Что-то пошло не так"
                    )
                )
            }

            persons.isEmpty() -> {
                renderState(
                    NameState.Empty(
                        message = "Ничего не нашлось"
                    )
                )
            }

            else -> {
                renderState(
                    NameState.Content(
                        names = persons
                    )
                )
            }
        }
    }

    private fun renderState(state: NameState) {
        stateLiveData.postValue(state)
    }
}
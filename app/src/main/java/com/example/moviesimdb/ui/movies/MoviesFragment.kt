package com.example.moviesimdb.ui.movies

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesimdb.R
import com.example.moviesimdb.databinding.FragmentMoviesBinding
import com.example.moviesimdb.domain.models.Movie
import com.example.moviesimdb.presentation.movies.MoviesSearchViewModel
import com.example.moviesimdb.ui.details.fragments.DetailsFragment
//import com.example.moviesimdb.ui.details.DetailsActivity
import com.example.moviesimdb.ui.models.MoviesState
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment: Fragment() {

    private lateinit var binding: FragmentMoviesBinding
    private lateinit var textWatcher: TextWatcher

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private val adapter = MoviesAdapter(
        object : MoviesAdapter.MovieClickListener {
            override fun onMovieClick(movie: Movie) {
                if (clickDebounce()) {
                    parentFragmentManager.commit {
                        replace(
                            R.id.rootFragmentContainerView,
                            DetailsFragment.newInstance(movie.id, movie.image),
                            DetailsFragment.TAG
                        )
                        addToBackStack(DetailsFragment.TAG)
                    }
                }
            }

            override fun onFavoriteToggleClick(movie: Movie) {
                viewModel.toggleFavorite(movie)
            }
        }
    )

    private var isClickAllowed = true

    private val handler = Handler(Looper.getMainLooper())

    private val viewModel by viewModel<MoviesSearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Здесь пришлось поправить использование Context
        binding.moviesList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.moviesList.adapter = adapter

        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchDebounce(
                    changedText = s?.toString() ?: ""
                )
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        textWatcher?.let { binding.queryInput.addTextChangedListener(it) }

        // Здесь пришлось заменить LifecycleOwner на ViewLifecycleOwner
        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        // Здесь пришлось заменить LifecycleOwner на ViewLifecycleOwner
        viewModel.observeShowToast().observe(viewLifecycleOwner) {
            if (it != null) {
                showToast(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        textWatcher?.let { binding.queryInput.removeTextChangedListener(it) }
    }

    private fun showToast(additionalMessage: String) {
        // Здесь пришлось поправить использование Context
        Toast.makeText(requireContext(), additionalMessage, Toast.LENGTH_LONG).show()
    }

    private fun render(state: MoviesState) {
        when (state) {
            is MoviesState.Content -> showContent(state.movies)
            is MoviesState.Empty -> showEmpty(state.message)
            is MoviesState.Error -> showError(state.errorMessage)
            is MoviesState.Loading -> showLoading()
        }
    }

    private fun showLoading() {
        binding.moviesList.visibility = View.GONE
        binding.placeholderMessage.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showError(errorMessage: String) {
        binding.moviesList.visibility = View.GONE
        binding.placeholderMessage.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE

        binding.placeholderMessage.text = errorMessage
    }

    private fun showEmpty(emptyMessage: String) {
        showError(emptyMessage)
    }

    private fun showContent(movies: List<Movie>) {
        binding.moviesList.visibility = View.VISIBLE
        binding.placeholderMessage.visibility = View.GONE
        binding.progressBar.visibility = View.GONE

        adapter.movies.clear()
        adapter.movies.addAll(movies)
        adapter.notifyDataSetChanged()
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }
}
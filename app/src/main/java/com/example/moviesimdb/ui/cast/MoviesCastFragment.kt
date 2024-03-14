package com.example.moviesimdb.ui.cast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesimdb.databinding.FragmentMoviesCastBinding
import com.example.moviesimdb.presentation.cast.MoviesCastViewModel
import com.example.moviesimdb.ui.models.MoviesCastState
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MoviesCastFragment : Fragment() {

    private lateinit var binding: FragmentMoviesCastBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesCastBinding.inflate(layoutInflater)
        return binding.root
    }

    // Добавили адаптер для RecyclerView
    private val adapter = ListDelegationAdapter(
        movieCastHeaderDelegate(),
        movieCastPersonDelegate(),
    )

    private val moviesCastViewModel: MoviesCastViewModel by viewModel {
        parametersOf(requireArguments().getString(ARGS_MOVIE_ID))
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Привязываем адаптер и LayoutManager к RecyclerView
        binding.moviesCastRecyclerView.adapter = adapter
        binding.moviesCastRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Наблюдаем за UiState из ViewModel
        moviesCastViewModel.observeState().observe(viewLifecycleOwner) {
            // В зависимости от UiState экрана показываем
            // разные состояния экрана
            when (it) {
                is MoviesCastState.Content -> showContent(it)
                is MoviesCastState.Error -> showError(it)
                is MoviesCastState.Loading -> showLoading()
            }
        }
    }

    private fun showLoading() {
        binding.contentContainer.isVisible = false
        binding.errorMessageTextView.isVisible = false

        binding.progressBar.isVisible = true
    }

    private fun showError(state: MoviesCastState.Error) {
        binding.contentContainer.isVisible = false
        binding.progressBar.isVisible = false

        binding.errorMessageTextView.isVisible = true
        binding.errorMessageTextView.text = state.message
    }

    private fun showContent(state: MoviesCastState.Content) {
        binding.progressBar.isVisible = false
        binding.errorMessageTextView.isVisible = false

        binding.contentContainer.isVisible = true

        // Меняем привязку стейта к UI-элементам
        binding.movieTitle.text = state.fullTitle
        adapter.items = state.items

        adapter.notifyDataSetChanged()
    }
    companion object {
        private const val ARGS_MOVIE_ID = "movie_id"

        const val TAG = "MoviesCastFragment"

        fun newInstance(movieId: String): Fragment {
            return MoviesCastFragment().apply {
                arguments = bundleOf(
                    ARGS_MOVIE_ID to movieId
                )
            }
        }
    }
}

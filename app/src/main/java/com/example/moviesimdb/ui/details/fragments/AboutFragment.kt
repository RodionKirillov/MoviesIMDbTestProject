package com.example.moviesimdb.ui.details.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.moviesimdb.R
import com.example.moviesimdb.databinding.FragmentDetailsBinding
import com.example.moviesimdb.domain.models.MovieDetails
import com.example.moviesimdb.presentation.poster.AboutViewModel
import com.example.moviesimdb.ui.cast.MoviesCastFragment
import com.example.moviesimdb.ui.models.DetailsState
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AboutFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var movieDetails: String

    private val viewModel: AboutViewModel by viewModel {
        parametersOf(movieDetails)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieDetails = requireArguments().getString(DETAILS, "")

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        binding.showCastButton.setOnClickListener {
            parentFragment?.parentFragmentManager?.commit {
                replace(
                    R.id.rootFragmentContainerView,
                    MoviesCastFragment.newInstance(movieDetails),
                    MoviesCastFragment.TAG
                )
                addToBackStack(MoviesCastFragment.TAG)
            }
        }
    }

    private fun render(state: DetailsState) {
        when (state) {
            is DetailsState.Content -> showContent(state.details)
            is DetailsState.Error -> showError(state.errorMessage)
        }
    }

    private fun showContent(movieDetails: MovieDetails) {
        binding.apply {
            details.visibility = View.VISIBLE
            errorMessage.visibility = View.GONE
            title.text = movieDetails.title
            ratingValue.text = movieDetails.imDbRating
            yearValue.text = movieDetails.year
            countryValue.text = movieDetails.countries
            genreValue.text = movieDetails.genres
            directorValue.text = movieDetails.directors
            writerValue.text = movieDetails.writers
            castValue.text = movieDetails.stars
            plot.text = movieDetails.plot
        }
    }

    private fun showError(errorMessage: String) {
        binding.details.visibility = View.GONE
        binding.errorMessage.visibility = View.VISIBLE
        binding.errorMessage.text = errorMessage
    }

    companion object {
        private const val DETAILS = "DETAILS"
        fun newInstance(movieId: String) = AboutFragment().apply {
            arguments = Bundle().apply {
                putString(DETAILS, movieId)
            }
        }
    }
}
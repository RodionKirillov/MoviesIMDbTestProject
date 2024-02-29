package com.example.moviesimdb.ui.details.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.moviesimdb.databinding.FragmentPosterBinding
import com.example.moviesimdb.presentation.poster.PosterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PosterFragment : Fragment() {

    private lateinit var binding: FragmentPosterBinding
    private lateinit var posterUrl: String

    private val viewModel: PosterViewModel by viewModel() {
        parametersOf(requireArguments().getString(POSTER_URL, ""))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPosterBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeUrl().observe(viewLifecycleOwner) {
            showPoster(it)
        }
    }

    private fun showPoster(url: String) {
        context?.let {
            Glide.with(it)
                .load(url)
                .into(binding.imageView)
        }
    }

    companion object {
        private const val POSTER_URL = "POSTER_URL"
        fun newInstance(posterUrl: String) = PosterFragment().apply {
            arguments = Bundle().apply {
                putString(POSTER_URL, posterUrl)
            }
        }
    }
}
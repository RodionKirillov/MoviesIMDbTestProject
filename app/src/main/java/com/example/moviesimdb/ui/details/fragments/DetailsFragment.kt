package com.example.moviesimdb.ui.details.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.moviesimdb.databinding.FragmentMainDetailsBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailsFragment: Fragment() {

    private lateinit var binding: FragmentMainDetailsBinding
    private lateinit var tabMediator: TabLayoutMediator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Поменяли использование intent на arguments
        val posterUrl = requireArguments().getString(ARGS_POSTER_URL) ?: ""
        val movieId = requireArguments().getString(ARGS_MOVIE_ID) ?: ""

        binding.viewPager.adapter = DetailsViewPagerAdapter(
            fragmentManager = childFragmentManager,
            lifecycle = lifecycle,
            posterUrl = posterUrl,
            movieId = movieId,
        )

        tabMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Постер"
                1 -> tab.text = "О фильме"
            }
        }
        tabMediator.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tabMediator.detach()
    }

    companion object {

        private const val ARGS_MOVIE_ID = "movie_id"
        private const val ARGS_POSTER_URL = "poster_url"

        // Тег для использования во FragmentManager
        const val TAG = "DetailsFragment"

        fun newInstance(movieId: String, posterUrl: String): Fragment {
            return DetailsFragment().apply {
                // Пробрасываем аргументы в Bundle
                arguments = bundleOf(
                    ARGS_MOVIE_ID to movieId,
                    ARGS_POSTER_URL to posterUrl
                )
            }
        }
    }
}
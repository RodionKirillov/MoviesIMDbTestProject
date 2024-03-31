package com.example.moviesimdb.ui.name

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesimdb.databinding.FragmentNamesBinding
import com.example.moviesimdb.domain.models.Movie
import com.example.moviesimdb.domain.models.Result
import com.example.moviesimdb.presentation.name.NameSearchViewModel
import com.example.moviesimdb.ui.models.MoviesState
import com.example.moviesimdb.ui.models.NameState
import org.koin.androidx.viewmodel.ext.android.viewModel

class NamesFragment : Fragment() {

    private val adapter = NamesAdapter()
    private val viewModel by viewModel<NameSearchViewModel>()

    private var _binding: FragmentNamesBinding? = null
    private val binding get() = _binding!!

    private lateinit var textWatcher: TextWatcher

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNamesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.locations.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.locations.adapter = adapter

        textWatcher = object :TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.searchDebounce(p0?.toString() ?: "")
            }

            override fun afterTextChanged(p0: Editable?) {}
        }
        textWatcher.let { binding.queryInput.addTextChangedListener(it) }

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
    }

    private fun render(state: NameState) {
        when (state) {
            is NameState.Content -> showContent(state.names)
            is NameState.Empty -> showEmpty(state.message)
            is NameState.Error -> showError(state.errorMessage)
            is NameState.Loading -> showLoading()
        }
    }

    private fun showLoading() {
        binding.locations.visibility = View.GONE
        binding.placeholderMessage.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showError(errorMessage: String) {
        binding.locations.visibility = View.GONE
        binding.placeholderMessage.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE

        binding.placeholderMessage.text = errorMessage
    }

    private fun showEmpty(emptyMessage: String) {
        showError(emptyMessage)
    }

    private fun showContent(names: List<Result>) {
        binding.locations.visibility = View.VISIBLE
        binding.placeholderMessage.visibility = View.GONE
        binding.progressBar.visibility = View.GONE


        adapter.names.clear()
        adapter.names.addAll(names)
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        textWatcher.let { binding.queryInput.removeTextChangedListener(it) }
    }
}
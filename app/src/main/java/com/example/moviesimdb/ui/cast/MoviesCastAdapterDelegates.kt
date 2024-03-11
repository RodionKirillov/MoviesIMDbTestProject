package com.example.moviesimdb.ui.cast

import com.example.moviesimdb.databinding.ListItemHeaderBinding
import com.example.moviesimdb.presentation.cast.MoviesCastRVItem
import com.example.moviesimdb.ui.RVItem
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

// Делегат для заголовков на экране состава участников
fun movieCastHeaderDelegate() = adapterDelegateViewBinding<MoviesCastRVItem.HeaderItem, RVItem, ListItemHeaderBinding>(
    { layoutInflater, root -> ListItemHeaderBinding.inflate(layoutInflater, root, false) }
) {
    bind {
        binding.headerTextView.text = item.headerText
    }
}
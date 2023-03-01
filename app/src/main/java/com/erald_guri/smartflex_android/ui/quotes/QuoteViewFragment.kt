package com.erald_guri.smartflex_android.ui.quotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.adapters.QuoteDetailAdapter
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.databinding.FragmentQuoteViewBinding
import com.erald_guri.smartflex_android.view_models.QuotesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuoteViewFragment : BaseFragment<FragmentQuoteViewBinding>(
    FragmentQuoteViewBinding::inflate
) {

    private val viewModel by viewModels<QuotesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = QuoteViewFragmentArgs.fromBundle(requireArguments()).position

        viewModel.getQuotes()
        viewModel.quotes.observe(viewLifecycleOwner) {
            val quoteContents = it[position].contents
            val quoteDetailAdapter = QuoteDetailAdapter(quoteContents)
            val linearLayoutManager = LinearLayoutManager(requireContext())
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            binding.includeRecycler.recycler.apply {
                layoutManager = linearLayoutManager
                adapter = quoteDetailAdapter
            }
        }
    }

}
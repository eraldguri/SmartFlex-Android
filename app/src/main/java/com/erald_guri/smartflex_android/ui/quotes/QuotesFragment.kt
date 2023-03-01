package com.erald_guri.smartflex_android.ui.quotes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.erald_guri.smartflex_android.adapters.QuoteAdapter
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.data.model.QuotesModel
import com.erald_guri.smartflex_android.databinding.FragmentQuotesBinding
import com.erald_guri.smartflex_android.interfaces.OnItemClickListener
import com.erald_guri.smartflex_android.view_models.QuotesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuotesFragment : BaseFragment<FragmentQuotesBinding>(
    FragmentQuotesBinding::inflate
) {

    private val viewModel by viewModels<QuotesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getQuotes()
        viewModel.quotes.observe(viewLifecycleOwner) {
            val quoteAdapter = QuoteAdapter(it, onItemClickListener)
            binding.includeRecycler.recycler.apply {
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = quoteAdapter
            }
        }
    }

    private val onItemClickListener = object: OnItemClickListener<QuotesModel> {
        override fun onItemClick(position: Int, item: QuotesModel) {
            val action = QuotesFragmentDirections.actionNavQuotesToQuoteViewFragment(position)
            findNavController().navigate(action)
        }

    }

}
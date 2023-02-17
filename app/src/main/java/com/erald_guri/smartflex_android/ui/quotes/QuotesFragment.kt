package com.erald_guri.smartflex_android.ui.quotes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.erald_guri.smartflex_android.BaseFragment
import com.erald_guri.smartflex_android.adapters.CardAdapter
import com.erald_guri.smartflex_android.data.model.CardModel
import com.erald_guri.smartflex_android.databinding.FragmentQuotesBinding
import com.erald_guri.smartflex_android.view_models.QuotesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuotesFragment : BaseFragment<FragmentQuotesBinding>(
    FragmentQuotesBinding::inflate
) {

    private val viewModel by viewModels<QuotesViewModel>()

    private var cardAdapter: CardAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchQuotes()

    }

    private fun fetchQuotes() {
        viewModel.getQuotes()
        viewModel.quotes.observe(viewLifecycleOwner) {
            setupUI(it)
        }
    }

    private fun setupUI(items: ArrayList<CardModel>) {
        cardAdapter = CardAdapter(items, CardAdapter.TYPE_SIMPLE_CARD)
        binding.includeRecycler.recycler.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = cardAdapter
        }
    }

}
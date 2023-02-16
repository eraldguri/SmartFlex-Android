package com.erald_guri.smartflex_android.ui.quotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.adapters.CardAdapter
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.data.model.CardModel
import com.erald_guri.smartflex_android.databinding.FragmentQuotesBinding
import com.erald_guri.smartflex_android.interfaces.OnRecyclerItemClickListener
import com.google.android.material.snackbar.Snackbar

class QuotesFragment : BaseFragment<FragmentQuotesBinding>(
    FragmentQuotesBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardAdapter = CardAdapter(requireContext(), onRecyclerItemClickListener)

        val color = arrayOf(
            ContextCompat.getColor(requireContext(), R.color.pink),
            ContextCompat.getColor(requireContext(), R.color.blue),
            ContextCompat.getColor(requireContext(), R.color.purple),
            ContextCompat.getColor(requireContext(), R.color.green)
        )

        val items = ArrayList<CardModel>()
        val chineseIdiomsCard = CardModel(1, "Chinese Idioms", "成语(chéngyǔ)", color[0])
        val motivationalQuotesCard = CardModel(2, "Motivational Quotes", "Motivate yourself", color[1])
        val loveQuotesCard = CardModel(3, "Love Quotes", "Love yourself first", color[2])
        val darkQuotesCard = CardModel(4, "Dark Quotes", "Embrace the darkness", color[3])

        items.add(chineseIdiomsCard)
        items.add(motivationalQuotesCard)
        items.add(loveQuotesCard)
        items.add(darkQuotesCard)

        cardAdapter.setItems(items)
        cardAdapter.setListener(onRecyclerItemClickListener)

        binding.includeRecycler.recycler.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = cardAdapter
        }

    }

    private val onRecyclerItemClickListener = object : OnRecyclerItemClickListener<CardModel> {
        override fun onItemClick(item: CardModel) {
            Snackbar.make(binding.root, "${item.title} clicked", Snackbar.LENGTH_SHORT).show()
        }

    }
}
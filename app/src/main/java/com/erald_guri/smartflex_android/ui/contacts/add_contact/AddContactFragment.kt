package com.erald_guri.smartflex_android.ui.contacts.add_contact

import android.os.Bundle
import android.view.View
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.databinding.FragmentAddContactBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddContactFragment : BaseFragment<FragmentAddContactBinding>(
    FragmentAddContactBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
    }

    private fun setupViewPager() {
        val pagerAdapter = AddContactPagerAdapter(requireActivity())
        binding.viewPager.adapter = pagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            val tabNames = listOf("Contact Info", "Address Info", "Description")
            tab.text = tabNames[position]
        }.attach()
    }

    override fun onFabButton(fabButton: FloatingActionButton?) {
        fabButton?.hide()
    }

}
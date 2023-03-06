package com.erald_guri.smartflex_android.ui.contacts.add_contact.pages

import android.os.Bundle
import android.view.View
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.databinding.FragmentDescriptionInfoBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DescriptionInfoFragment : BaseFragment<FragmentDescriptionInfoBinding>(
    FragmentDescriptionInfoBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onFabButton(fabButton: FloatingActionButton?) {
        fabButton?.hide()
    }

}
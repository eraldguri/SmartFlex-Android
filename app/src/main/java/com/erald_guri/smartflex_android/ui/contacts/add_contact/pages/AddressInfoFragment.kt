package com.erald_guri.smartflex_android.ui.contacts.add_contact.pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.databinding.FragmentAddresInfosBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressInfoFragment : BaseFragment<FragmentAddresInfosBinding>(
    FragmentAddresInfosBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onFabButton(fabButton: FloatingActionButton?) {
        fabButton?.hide()
    }

}
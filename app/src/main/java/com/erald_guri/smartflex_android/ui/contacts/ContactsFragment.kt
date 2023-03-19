package com.erald_guri.smartflex_android.ui.contacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.adapters.ContactListAdapter
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.databinding.FragmentContactsBinding
import com.erald_guri.smartflex_android.view_models.ContactViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactsFragment : BaseFragment<FragmentContactsBinding>(
    FragmentContactsBinding::inflate
) {

    private val viewModel by viewModels<ContactViewModel>()
    private lateinit var contactAdapter: ContactListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeContacts()
    }

    private fun observeContacts() {
        viewModel.fetchContacts()
        viewModel.contacts.observe(viewLifecycleOwner) {
            contactAdapter = ContactListAdapter(requireContext(), it)
            binding.includeRecycler.recycler.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = contactAdapter
            }
        }
    }

    override fun onFabButton(fabButton: FloatingActionButton?) {
        fabButton?.show()
        fabButton?.setOnClickListener {
            val action = ContactsFragmentDirections.actionNavContactsToAddContactFragment()
            findNavController().navigate(action)
        }
    }

}
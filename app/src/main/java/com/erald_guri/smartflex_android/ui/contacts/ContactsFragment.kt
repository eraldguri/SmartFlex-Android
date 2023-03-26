package com.erald_guri.smartflex_android.ui.contacts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.erald_guri.smartflex_android.adapters.ContactListAdapter
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.databinding.FragmentContactsBinding
import com.erald_guri.smartflex_android.interfaces.OnContactListener
import com.erald_guri.smartflex_android.view_models.ContactViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
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
            contactAdapter = ContactListAdapter(requireContext(), it, onContactListener)
            binding.includeRecycler.recycler.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = contactAdapter
            }
        }
    }

    private val onContactListener = object : OnContactListener {
        override fun onDetailView(contactId: Int?) {

        }

        override fun onEditContact(contactId: Int?) {
            val action = ContactsFragmentDirections.actionNavContactsToAddContactFragment(true, contactId!!)
            findNavController().navigate(action)
        }

        override fun onDeleteContact() {

        }

        override fun onFavorite() {

        }

        override fun onCall() {

        }

        override fun onMessage() {

        }

    }

    override fun onFabButton(fabButton: FloatingActionButton?) {
        fabButton?.show()
        fabButton?.setOnClickListener {
            val action = ContactsFragmentDirections.actionNavContactsToAddContactFragment(false, -1)
            findNavController().navigate(action)
        }
    }

}
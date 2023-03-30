package com.erald_guri.smartflex_android.ui.contacts

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.adapters.ContactListAdapter
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.data.model.ContactModel
import com.erald_guri.smartflex_android.databinding.FragmentContactsBinding
import com.erald_guri.smartflex_android.interfaces.OnContactListener
import com.erald_guri.smartflex_android.view_models.ContactViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

const val CALL_PERMISSION_REQUEST = 110

@AndroidEntryPoint
class ContactsFragment : BaseFragment<FragmentContactsBinding>(
    FragmentContactsBinding::inflate
), EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {

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
            val action = ContactsFragmentDirections.actionNavContactsToContactViewFragment(contactId!!)
            findNavController().navigate(action)
        }

        override fun onEditContact(contactId: Int?) {
            val action = ContactsFragmentDirections.actionNavContactsToAddContactFragment(true, contactId!!)
            findNavController().navigate(action)
        }

        override fun onDeleteContact() {

        }

        override fun onFavorite(contact: ContactModel, isFavorite: Boolean) {
            if (isFavorite) {
                contact.isFavorite = 1
                viewModel.addToFavorites(contact)
            } else {
                contact.isFavorite = 0
                viewModel.addToFavorites(contact)
            }
        }

        override fun onCall(phone: String) {
            if (phone.isNotEmpty()) {
                callPermission(phone)
            }
        }

        override fun onMessage() {

        }

    }

    private fun hasCallPermission(): Boolean {
        return EasyPermissions.hasPermissions(requireContext(), Manifest.permission.CALL_PHONE)
    }

    private fun callPermission(phone: String) {
        if (hasCallPermission()) {
            val intentCall = Intent(Intent.ACTION_CALL)
            intentCall.data = Uri.parse("tel:$phone")
            startActivity(intentCall)
        } else {
            EasyPermissions.requestPermissions(requireActivity(),
                "SmartFlex needs camera permissions",
                CALL_PERMISSION_REQUEST,
                Manifest.permission.CALL_PHONE
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this@ContactsFragment)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this@ContactsFragment, perms)) {
            AppSettingsDialog.Builder(requireActivity()).build().show()
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            Toast.makeText(
                requireContext(),
                if (hasCallPermission()) "Yes" else "No",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onRationaleAccepted(requestCode: Int) {
        Log.d(TAG, "onRationaleAccepted:" + requestCode)
    }

    override fun onRationaleDenied(requestCode: Int) {
        Log.d(TAG, "onRationaleDenied:" + requestCode)
    }

    override fun onFabButton(fabButton: FloatingActionButton?) {
        fabButton?.show()
        fabButton?.setOnClickListener {
            val action = ContactsFragmentDirections.actionNavContactsToAddContactFragment(false, -1)
            findNavController().navigate(action)
        }
    }

    override fun onToolbar(toolbar: Toolbar?) {
        val menu = toolbar?.menu
        val editMenuItem = menu?.findItem(R.id.action_edit)
        editMenuItem?.isVisible = false
    }

    companion object {
        private val TAG = ContactsFragment::class.java.canonicalName
    }

}
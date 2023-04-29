package com.erald_guri.smartflex_android.ui.contacts

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.base.FlexAdapter
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
    private var contactAdapter = FlexAdapter<ContactModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        observeContacts()
    }

//    private fun observeContacts() {
//        viewModel.fetchContacts()
//        viewModel.contacts.observe(viewLifecycleOwner) {
//            contactAdapter.items = it.toMutableList()
//
//            contactAdapter.expressionViewHolderBinding = { item, viewBinding ->
//                val view = viewBinding as LayoutContactListItemBinding
//                setupRecyclerViewHolder(item, view)
//            }
//
//            contactAdapter.setOnClickListener(onItemClickListener)
//
//            contactAdapter.expressionOnCreateViewHolder = { viewGroup ->
//                LayoutContactListItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
//            }
//
//            binding.includeRecycler.recycler.apply {
//                layoutManager = LinearLayoutManager(requireContext())
//                adapter = contactAdapter
//            }
//        }
//    }

//    private fun setupRecyclerViewHolder(item: ContactModel, viewBinding: LayoutContactListItemBinding) {
//        viewBinding.apply {
//            tvFullName.text = "${item.firstName} ${item.lastName}"
//            tvTitle.text = item.title
//
//            val imageUri = Uri.parse(item.photoPath)
//            imageView.imageTintMode = null
//            if (item.photoPath.isNotEmpty()) {
//                Glide.with(requireContext())
//                    .load("file:" + imageUri)
//                    .override(500, 500)
//                    .into(imageView)
//            } else {
//                Glide.with(requireContext())
//                    .load(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_emoji_emotions_24))
//                    .override(500, 500)
//                    .into(imageView)
//            }
//
//            var isFavorite = false
//            isFavorite = if (item.isFavorite == 0) {
//                btnFavorite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_favorite_outline))
//                false
//            } else {
//                btnFavorite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_favorite_filled))
//                true
//            }
//            btnFavorite.setOnClickListener {
//                if (isFavorite) {
//                    btnFavorite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_favorite_outline))
//                    onContactListener.onFavorite(item, false)
//                } else {
//                    btnFavorite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_favorite_filled))
//                    onContactListener.onFavorite(item, true)
//                }
//            }
//        }
//    }


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

        }
    }

    override fun onToolbar(toolbar: Toolbar?) {
        val menu = toolbar?.menu
//        val editMenuItem = menu?.findItem(R.id.action_edit)
//        editMenuItem?.isVisible = false
    }

    companion object {
        private val TAG = ContactsFragment::class.java.canonicalName
    }

}
package com.erald_guri.smartflex_android.ui.contacts.view.tabs.activities.pages

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.adapters.ItemMenuAdapter
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.data.model.ContactModel
import com.erald_guri.smartflex_android.data.model.ItemMenuModel
import com.erald_guri.smartflex_android.databinding.FragmentAllActivitiesFragmentBinding
import com.erald_guri.smartflex_android.ui.contacts.view.ContactViewFragment
import com.erald_guri.smartflex_android.ui.contacts.view.OnContactDetailsListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

const val CALL_PERMISSION_REQUEST = 110

class AllActivitiesFragmentFragment : BaseFragment<FragmentAllActivitiesFragmentBinding>(
    FragmentAllActivitiesFragmentBinding::inflate
), EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuItems = ArrayList<ItemMenuModel>()
        menuItems.add(ItemMenuModel(1, R.drawable.ic_baseline_edit_note_24, "Notes", 10))
        menuItems.add(ItemMenuModel(2, R.drawable.ic_baseline_task_alt_24, "Tasks", 7))
        menuItems.add(ItemMenuModel(3, R.drawable.ic_baseline_edit_calendar_24, "Meeting", 2))
        menuItems.add(ItemMenuModel(4, R.drawable.ic_baseline_call_log, "Call logs", 5))

        val menuAdapter = ItemMenuAdapter(menuItems)
        binding.includeRecycler.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = menuAdapter
        }

        ContactViewFragment.setContactDetailsListener(onContactDetailsListener)
    }

    private val onContactDetailsListener = object : OnContactDetailsListener {
        override fun getContact(contact: ContactModel) {
            if (contact.phone.isNotEmpty()) {
                callPermission(contact.phone)
            }
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
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this@AllActivitiesFragmentFragment)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this@AllActivitiesFragmentFragment, perms)) {
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
        fabButton?.hide()
    }

    override fun onToolbar(toolbar: Toolbar?) {

    }

    companion object {
        private val TAG = AllActivitiesFragmentFragment::class.java.canonicalName
    }

}
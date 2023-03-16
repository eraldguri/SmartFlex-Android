package com.erald_guri.smartflex_android.ui.contacts.add_contact

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.databinding.DialogPhotoChoserBinding
import com.erald_guri.smartflex_android.databinding.FragmentAddContactBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

const val CAMERA_PERMISSION_REQUEST = 100
const val READ_EXTERNAL_STORAGE_REQUEST = 101
const val WRITE_EXTERNAL_STORAGE_REQUEST = 101

@AndroidEntryPoint
class AddContactFragment : BaseFragment<FragmentAddContactBinding>(
    FragmentAddContactBinding::inflate
), EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnSelectPhoto.setOnClickListener { photoChooserDialog() }
        }
    }

    private fun photoChooserDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())
        val photoChooserBinding = DialogPhotoChoserBinding.inflate(layoutInflater)
        alertDialog.setView(photoChooserBinding.root)
        val dialog = alertDialog.create()

        photoChooserBinding.apply {
            btnCamera.setOnClickListener {
                dialog.dismiss()
                cameraPermission()
                storagePermission()
            }
            btnGallery.setOnClickListener { storagePermission() }
        }

        dialog.show()
    }

    override fun onFabButton(fabButton: FloatingActionButton?) {
        fabButton?.hide()
    }

    private fun hasCameraPermission(): Boolean {
        return EasyPermissions.hasPermissions(requireContext(), Manifest.permission.CAMERA)
    }

    private fun hasReadExternalStoragePermission(): Boolean {
        return EasyPermissions.hasPermissions(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun hasWriteExternalStoragePermission(): Boolean {
        return EasyPermissions.hasPermissions(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    private fun cameraPermission() {
        if (hasCameraPermission()) {
            val action = AddContactFragmentDirections.actionAddContactFragmentToPreviewFragment()
            findNavController().navigate(action)
        } else {
            EasyPermissions.requestPermissions(requireActivity(),
                "SmartFlex needs camera permissions",
                CAMERA_PERMISSION_REQUEST,
                Manifest.permission.CAMERA
            )
        }
    }

    private fun storagePermission() {
        if (hasReadExternalStoragePermission() && hasWriteExternalStoragePermission()) {
            //TODO
        } else {
            EasyPermissions.requestPermissions(requireActivity(),
                "SmartFlex needs to access internal storage",
                READ_EXTERNAL_STORAGE_REQUEST,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            EasyPermissions.requestPermissions(requireActivity(),
                "SmartFlex needs to access internal storage",
                WRITE_EXTERNAL_STORAGE_REQUEST,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this@AddContactFragment)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this@AddContactFragment, perms)) {
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
                if (hasCameraPermission()) "Yes" else "No",
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

    companion object {
        private val TAG = AddContactFragment::class.java.canonicalName
    }

}
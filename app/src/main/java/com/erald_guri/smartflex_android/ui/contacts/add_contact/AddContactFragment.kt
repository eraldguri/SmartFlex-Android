package com.erald_guri.smartflex_android.ui.contacts.add_contact

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.data.model.ContactModel
import com.erald_guri.smartflex_android.databinding.DialogPhotoChoserBinding
import com.erald_guri.smartflex_android.databinding.FragmentAddContactBinding
import com.erald_guri.smartflex_android.view_models.ContactViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.File


const val CAMERA_PERMISSION_REQUEST = 100
const val READ_EXTERNAL_STORAGE_REQUEST = 101
const val WRITE_EXTERNAL_STORAGE_REQUEST = 102

@AndroidEntryPoint
class AddContactFragment : BaseFragment<FragmentAddContactBinding>(
    FragmentAddContactBinding::inflate
), EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {

    private val viewModel by viewModels<ContactViewModel>()
    private lateinit var photoDialog: AlertDialog
    private lateinit var imagePath: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnSelectPhoto.setOnClickListener { photoChooserDialog() }
            btnCreateContact.setOnClickListener { createContact() }
        }
    }

    private fun photoChooserDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())
        val photoChooserBinding = DialogPhotoChoserBinding.inflate(layoutInflater)
        alertDialog.setView(photoChooserBinding.root)
        photoDialog = alertDialog.create()

        photoChooserBinding.apply {
            btnCamera.setOnClickListener {
                photoDialog.dismiss()
                cameraPermission()
                storagePermission()
            }
            btnGallery.setOnClickListener { storagePermission() }
        }

        photoDialog.show()
    }

    private fun createContact() {
        binding.apply {
            val firstName = edFirstName.text.toString()
            val lastName = edLastName.text.toString()
            val email = edEmail.text.toString()
            val title = edTitle.text.toString()
            val accountName = edAccountName.text.toString()
            val vendorName = edVendorName.text.toString()
            val leadSource = edLeadSource.text.toString()
            val dateOfBirth = edBirthday.text.toString()
            val phone = edPhone.text.toString()
            val otherPhone = edOtherPhone.text.toString()
            val mobile = edMobile.text.toString()
            val secondaryEmail = edSecondaryEmail.text.toString()
            val street = edStreet.text.toString()
            val otherStreet = edOtherStreet.text.toString()
            val city = edCity.text.toString()
            val otherCity = edOtherCity.text.toString()
            val state = edState.text.toString()
            val otherState = edOtherState.text.toString()
            val country = edCountry.text.toString()
            val otherCountry = edOtherCountry.text.toString()
            val zipCode = edZip.text.toString()
            val otherZipCode = edOtherZip.text.toString()
            val description = edDescription.text.toString()
            var selectedFilePath = ""
            selectedFilePath = imagePath.ifEmpty {
                ""
            }
            val contact = ContactModel(
                firstName, lastName, email, title, accountName, vendorName, leadSource, dateOfBirth,
                phone, otherPhone, mobile, secondaryEmail, street, otherStreet,
                city, otherCity, state, otherState, country, otherCountry, zipCode, otherZipCode,
                description, selectedFilePath
            )
            if (firstName.isNotEmpty() && lastName.isNotEmpty()) {
                viewModel.insertContact(contact)
                viewModel.success.observe(viewLifecycleOwner) {
                    if (!it.error) {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

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
            pickImageFromGallery()
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

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultLauncher.launch(intent)
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

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Display selected image from gallery
            val imageUri: Uri? = result.data?.data
            Glide.with(requireContext())
                .load(imageUri)
                .override(500, 500)
                .into(binding.imageView)

            val imageFile = File(imageUri.toString())
            imagePath = imageFile.absolutePath

            photoDialog.dismiss()
            binding.btnSelectPhoto.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_edit_24))
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
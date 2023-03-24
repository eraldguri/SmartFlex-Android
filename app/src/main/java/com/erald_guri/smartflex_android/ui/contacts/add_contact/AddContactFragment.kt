package com.erald_guri.smartflex_android.ui.contacts.add_contact

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.adapters.SocialAccountAdapter
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.data.model.ContactModel
import com.erald_guri.smartflex_android.data.model.SocialLinkAccountModel
import com.erald_guri.smartflex_android.databinding.DialogPhotoChoserBinding
import com.erald_guri.smartflex_android.databinding.FragmentAddContactBinding
import com.erald_guri.smartflex_android.databinding.LayoutNewSocialLinkBinding
import com.erald_guri.smartflex_android.interfaces.OnTaskListener
import com.erald_guri.smartflex_android.view_models.ContactViewModel
import com.erald_guri.smartflex_android.view_models.SocialLinkViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.*
import kotlin.collections.ArrayList

const val CAMERA_PERMISSION_REQUEST = 100
const val READ_EXTERNAL_STORAGE_REQUEST = 101
const val WRITE_EXTERNAL_STORAGE_REQUEST = 102

@AndroidEntryPoint
class AddContactFragment : BaseFragment<FragmentAddContactBinding>(
    FragmentAddContactBinding::inflate
), EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {

    private val viewModel by viewModels<ContactViewModel>()
    private val linkViewModel by viewModels<SocialLinkViewModel>()
    private lateinit var photoDialog: AlertDialog
    private var imagePath: String? = null
    private lateinit var selectedDate: String

    private lateinit var socialLinkAdapter: SocialAccountAdapter
    private lateinit var socialLinkAccountModel: SocialLinkAccountModel
    private var socialLinkAccountListPosition: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        linkViewModel.fetchAll()
        linkViewModel.links.observe(viewLifecycleOwner) {
            val linkList = ArrayList<SocialLinkAccountModel>(it)
            socialLinkAdapter = SocialAccountAdapter(linkList, onSocialTaskListener)
            binding.includeSocials.includeRecycler.recycler.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = socialLinkAdapter
            }
        }

        binding.apply {
            includeContactInfo.edBirthday.setOnClickListener { datePickerDialog() }
            btnSelectPhoto.setOnClickListener { photoChooserDialog() }
            btnCreateContact.setOnClickListener { createContact() }
            includeSocials.btnAddAccount.setOnClickListener { socialLinksDialog(false) }
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

    private fun datePickerDialog() {
        val now = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog.newInstance(
            onDateSet,
            now.get(Calendar.YEAR),
            now.get(Calendar.MONTH),
            now.get(Calendar.DAY_OF_MONTH)
        )
        childFragmentManager.let { datePickerDialog.show(it, "Datepickerdialog") };
    }

    private val onDateSet = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        selectedDate = "$dayOfMonth/$monthOfYear/$year"
        binding.includeContactInfo.edBirthday.setText(selectedDate)
    }

    private fun createContact() {
        binding.apply {
            val firstName       = includeContactInfo.edFirstName.text.toString()
            val lastName        = includeContactInfo.edLastName.text.toString()
            val email           = includeContactInfo.edEmail.text.toString()
            val title           = includeContactInfo.edTitle.text.toString()
            val company         = includeContactInfo.edCompany.text.toString()
            val accountName     = includeContactInfo.edAccountName.text.toString()
            val vendorName      = includeContactInfo.edVendorName.text.toString()
            val leadSource      = includeContactInfo.edLeadSource.text.toString()
            val dateOfBirth     = includeContactInfo.edBirthday.text.toString()
            val phone           = includeContactInfo.edPhone.text.toString()
            val otherPhone      = includeContactInfo.edOtherPhone.text.toString()
            val mobile          = includeContactInfo.edMobile.text.toString()
            val secondaryEmail  = includeContactInfo.edSecondaryEmail.text.toString()
            val street          = includeAddressInfo.edStreet.text.toString()
            val otherStreet     = includeAddressInfo.edOtherStreet.text.toString()
            val city            = includeAddressInfo.edCity.text.toString()
            val otherCity       = includeAddressInfo.edOtherCity.text.toString()
            val state           = includeAddressInfo.edState.text.toString()
            val otherState      = includeAddressInfo.edOtherState.text.toString()
            val country         = includeAddressInfo.edCountry.text.toString()
            val otherCountry    = includeAddressInfo.edOtherCountry.text.toString()
            val zipCode         = includeAddressInfo.edZip.text.toString()
            val otherZipCode    = includeAddressInfo.edOtherZip.text.toString()
            val description     = edDescription.text.toString()
            var selectedFilePath: String? = ""
            selectedFilePath = if (imagePath != null) {
                imagePath
            } else {
                ""
            }
            val contact = ContactModel(
                firstName, lastName, email, title, company, accountName, vendorName, leadSource, dateOfBirth,
                phone, otherPhone, mobile, secondaryEmail, street, otherStreet,
                city, otherCity, state, otherState, country, otherCountry, zipCode, otherZipCode,
                description, selectedFilePath!!
            )
            val isInputEmpty = validateUserInput()
            if (isInputEmpty) {
                viewModel.insertContact(contact)
                viewModel.success.observe(viewLifecycleOwner) {
                    if (!it.error) {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Snackbar.make(binding.root, "Some fields are required", Snackbar.LENGTH_SHORT).show()
            }
        }

    }

    private fun validateUserInput(): Boolean {
        var isAnyFieldEmpty = false
        binding.apply {
            if (includeContactInfo.edFirstName.text.toString().isNotEmpty() || includeContactInfo.edFirstName.text.toString().equals("", ignoreCase = true)) {
                includeContactInfo.edFirstName.error = "First Name is required"
                isAnyFieldEmpty = true
            } else {
                includeContactInfo.edFirstName.error = null
                isAnyFieldEmpty = false
            }

            if (includeContactInfo.edLastName.text.toString().isNotEmpty() || includeContactInfo.edLastName.text.toString().equals("", ignoreCase = true)) {
                includeContactInfo.edLastName.error = "Last Name is required"
                isAnyFieldEmpty = true
            } else {
                includeContactInfo.edLastName.error = null
                isAnyFieldEmpty = false
            }

            if (includeContactInfo.edEmail.text.toString().isNotEmpty() || includeContactInfo.edEmail.text.toString().equals("", ignoreCase = true)) {
                includeContactInfo.edEmail.error = "Email is required"
                isAnyFieldEmpty = true
            } else {
                includeContactInfo.edEmail.error = null
                isAnyFieldEmpty = false
            }

            if (includeContactInfo.edTitle.text.toString().isNotEmpty() || includeContactInfo.edTitle.text.toString().equals("", ignoreCase = true)) {
                includeContactInfo.edTitle.error = "Title Name is required"
                isAnyFieldEmpty = true
            } else {
                includeContactInfo.edTitle.error = null
                isAnyFieldEmpty = false
            }

            if (includeContactInfo.edPhone.text.toString().isNotEmpty() || includeContactInfo.edPhone.text.toString().equals("", ignoreCase = true)) {
                includeContactInfo.edPhone.error = "Phone is required"
                isAnyFieldEmpty = true
            } else {
                includeContactInfo.edPhone.error = null
                isAnyFieldEmpty = false
            }

            if (includeAddressInfo.edCity.text.toString().isNotEmpty() || includeAddressInfo.edCity.text.toString().equals("", ignoreCase = true)) {
                includeAddressInfo.edCity.error = "City is required"
                isAnyFieldEmpty = true
            } else {
                includeAddressInfo.edCity.error = null
                isAnyFieldEmpty = false
            }

            if (includeAddressInfo.edCountry.text.toString().isNotEmpty() || includeAddressInfo.edCountry.text.toString().equals("", ignoreCase = true)) {
                includeAddressInfo.edCountry.error = "Country is required"
                isAnyFieldEmpty = true
            } else {
                includeAddressInfo.edCountry.error = null
                isAnyFieldEmpty = false
            }
        }

        return isAnyFieldEmpty
    }

    private fun socialLinksDialog(isEditMode: Boolean) {
        val socialLinkBottomSheetDialog = BottomSheetDialog(requireContext())
        val socialLinkBinding = LayoutNewSocialLinkBinding.inflate(layoutInflater)
        socialLinkBottomSheetDialog.setContentView(socialLinkBinding.root)

        socialLinkBinding.apply {

            if (isEditMode) {
                edName.setText(socialLinkAccountModel.title)
                edLink.setText(socialLinkAccountModel.link)
                btnSave.text = getString(R.string.edit)

                btnSave.setOnClickListener {
                    if (edLink.text.toString().isNotEmpty() || edLink.text.toString().equals("", ignoreCase = true)) {
                        val name = edName.text.toString()
                        val link = edLink.text.toString()
                        val socialLinkModel = SocialLinkAccountModel(name, link)
                        socialLinkModel.id = socialLinkAccountModel.id

                        linkViewModel.updateLink(socialLinkModel)
                        linkViewModel.success.observe(viewLifecycleOwner) {
                            if (!it.error) {
                                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                                socialLinkBottomSheetDialog.dismiss()
                                socialLinkAdapter.updateLink(socialLinkAccountListPosition, socialLinkModel)
                            } else {
                                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                btnSave.setOnClickListener {
                    if (edLink.text.toString().isNotEmpty() || edLink.text.toString().equals("", ignoreCase = true)) {
                        val name = edName.text.toString()
                        val link = edLink.text.toString()
                        val socialLinkModel = SocialLinkAccountModel(name, link)
                        linkViewModel.insertLink(socialLinkModel)
                        linkViewModel.success.observe(viewLifecycleOwner) {
                            if (!it.error) {
                                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                                socialLinkBottomSheetDialog.dismiss()
                                socialLinkAdapter.addLink(socialLinkModel)
                            } else {
                                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

        socialLinkBottomSheetDialog.show()
    }

    private val onSocialTaskListener = object: OnTaskListener<SocialLinkAccountModel> {
        override fun onItemClick(task: SocialLinkAccountModel) {

        }

        override fun onEdit(position: Int, task: SocialLinkAccountModel) {
            socialLinkAccountModel = task
            socialLinkAccountListPosition = position
            socialLinksDialog(true)
        }

        override fun onDelete(position: Int, task: SocialLinkAccountModel) {
            linkViewModel.deleteLink(task)
            linkViewModel.success.observe(viewLifecycleOwner) {
                if (!it.error) {
                    socialLinkAdapter.removeLink(task)
                } else {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
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

            imagePath = getRealPathFromURI(imageUri!!, requireContext())

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

    private fun getRealPathFromURI(uri: Uri, context: Context): String? {
        val returnCursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
        val nameIndex: Int = returnCursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        val sizeIndex: Int = returnCursor.getColumnIndex(OpenableColumns.SIZE)
        returnCursor.moveToFirst()
        val name: String = returnCursor.getString(nameIndex)
        val size = returnCursor.getLong(sizeIndex).toString()
        val file: File = File(context.filesDir, name)
        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(file)
            var read = 0
            val maxBufferSize = 1 * 1024 * 1024
            val bytesAvailable: Int = inputStream!!.available()

            //int bufferSize = 1024;
            val bufferSize = bytesAvailable.coerceAtMost(maxBufferSize)
            val buffers = ByteArray(bufferSize)
            while (inputStream.read(buffers).also { read = it } != -1) {
                outputStream.write(buffers, 0, read)
            }
            Log.e("File Size", "Size " + file.length())
            inputStream.close()
            outputStream.close()
            returnCursor.close()
            Log.e("File Path", "Path " + file.path)
            Log.e("File Size", "Size " + file.length())
        } catch (e: Exception) {
            Log.e("Exception", e.message!!)
        }
        return file.path
    }

}
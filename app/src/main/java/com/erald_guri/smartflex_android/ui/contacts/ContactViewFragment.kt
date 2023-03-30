package com.erald_guri.smartflex_android.ui.contacts

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.databinding.FragmentContactViewBinding
import com.erald_guri.smartflex_android.view_models.ContactViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactViewFragment : BaseFragment<FragmentContactViewBinding>(
    FragmentContactViewBinding::inflate
) {

    private var contactId: Int = -1

    private val viewModel by viewModels<ContactViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contactId = ContactViewFragmentArgs.fromBundle(requireArguments()).contactId
        fetchContact(contactId)
    }

    @SuppressLint("SetTextI18n")
    private fun fetchContact(contactId: Int) {
        val italicTypeface = Typeface.defaultFromStyle(Typeface.ITALIC)
        binding.apply {
            viewModel.getContactById(contactId)
            viewModel.contact.observe(viewLifecycleOwner) {
                tvName.text = "${it.firstName} ${it.lastName}"
                if (it.email.isNotEmpty()) {
                    tvEmail.text = it.email
                } else {
                    tvEmail.typeface = italicTypeface
                    tvEmail.text = getString(R.string.email_not_set)
                }

                if (it.phone.isNotEmpty()) {
                    tvPhone.text = it.phone
                } else {
                    tvPhone.typeface = italicTypeface
                    tvPhone.text = getString(R.string.phone_not_set)
                }

                if (it.title.isNotEmpty()) {
                    tvTitleValue.text = it.title
                } else {
                    tvTitleValue.typeface = italicTypeface
                    tvTitleValue.text = getString(R.string.title_not_set)
                }

                if (it.company.isNotEmpty()) {
                    tvCompanyValue.text = it.company
                } else {
                    tvCompanyValue.typeface = italicTypeface
                    tvCompanyValue.text = getString(R.string.company_not_set)
                }

                if (it.accountName.isNotEmpty()) {
                    tvAccountNameValue.text = it.accountName
                } else {
                    tvAccountNameValue.typeface = italicTypeface
                    tvAccountNameValue.text = getString(R.string.account_name_not_set)
                }

                if (it.accountName.isNotEmpty()) {
                    tvVendorNameValue.text = it.accountName
                } else {
                    tvVendorNameValue.typeface = italicTypeface
                    tvVendorNameValue.text = getString(R.string.vendor_name_not_set)
                }

                if (it.leadSource.isNotEmpty()) {
                    tvVendorLeadSource.text = it.accountName
                } else {
                    tvVendorLeadSource.typeface = italicTypeface
                    tvVendorLeadSource.text = getString(R.string.lead_source_not_set)
                }

                if (it.dateOfBirth.isNotEmpty()) {
                    tvBirthdayValue.text = it.accountName
                } else {
                    tvBirthdayValue.typeface = italicTypeface
                    tvBirthdayValue.text = getString(R.string.birthday_not_set)
                }

                if (it.phone.isNotEmpty()) {
                    tvPhoneValue.text = it.phone
                } else {
                    tvPhoneValue.typeface = italicTypeface
                    tvPhoneValue.text = getString(R.string.phone_not_set)
                }

                if (it.otherPhone.isNotEmpty()) {
                    tvOtherPhoneValue.text = it.otherPhone
                } else {
                    tvOtherPhoneValue.typeface = italicTypeface
                    tvOtherPhoneValue.text = getString(R.string.phone_not_set)
                }

                if (it.mobile.isNotEmpty()) {
                    tvMobileValue.text = it.mobile
                } else {
                    tvMobileValue.typeface = italicTypeface
                    tvMobileValue.text = getString(R.string.phone_not_set)
                }

                if (it.secondaryEmail.isNotEmpty()) {
                    tvSecondaryEmailValue.text = it.secondaryEmail
                } else {
                    tvSecondaryEmailValue.typeface = italicTypeface
                    tvSecondaryEmailValue.text = getString(R.string.phone_not_set)
                }

            }
        }
    }

    override fun onFabButton(fabButton: FloatingActionButton?) {
        fabButton?.hide()
    }

    override fun onToolbar(toolbar: Toolbar?) {
        toolbar?.title = "Profile"
        val menu = toolbar?.menu
        val editMenuItem = menu?.findItem(R.id.action_edit)
        editMenuItem?.setOnMenuItemClickListener {
            val action = ContactViewFragmentDirections.actionContactViewFragmentToAddContactFragment(true, contactId)
            findNavController().navigate(action)
            false
        }
    }

}
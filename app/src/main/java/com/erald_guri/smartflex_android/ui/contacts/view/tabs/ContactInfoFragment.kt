package com.erald_guri.smartflex_android.ui.contacts.view.tabs

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.data.model.ContactModel
import com.erald_guri.smartflex_android.databinding.FragmentContactInfoBinding
import com.erald_guri.smartflex_android.ui.contacts.view.ContactViewFragment
import com.erald_guri.smartflex_android.ui.contacts.view.OnContactDetailsListener
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ContactInfoFragment : BaseFragment<FragmentContactInfoBinding>(
    FragmentContactInfoBinding::inflate
) {

    val headers = arrayOf("Title", "Company", "Account Name", "Vendor Name", "Lead Source", "Birthdate",
        "Phone", "Other Phone", "Mobile", "Secondary Email")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ContactViewFragment.setContactDetailsListener(onContactDetailsListener)

    }

    private val onContactDetailsListener = object : OnContactDetailsListener {
        override fun getContact(contact: ContactModel) {
            val values = arrayOf(
                contact.title, contact.company, contact.accountName, contact.vendorName, contact.leadSource,
                contact.dateOfBirth, contact.phone, contact.otherPhone, contact.mobile, contact.secondaryEmail
            )
            initTableView(values)
        }
    }

    private fun initTableView(values: Array<String>) {
        headers.forEachIndexed { index, value ->
            val tableRow = TableRow(requireContext())
            tableRow.id = index + 1
            tableRow.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)

            val textViewHeader = TextView(requireContext())
            textViewHeader.id = index + 1
            textViewHeader.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
            textViewHeader.text = value

            tableRow.addView(textViewHeader)

            val textViewValue = TextView(requireContext())
            textViewValue.id = index + 1
            textViewValue.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
            textViewValue.text = values[index]

            tableRow.addView(textViewValue)

            binding.tbContactInfo.addView(tableRow)
        }

    }

    override fun onFabButton(fabButton: FloatingActionButton?) {
        fabButton?.hide()
    }

    override fun onToolbar(toolbar: Toolbar?) {

    }

}
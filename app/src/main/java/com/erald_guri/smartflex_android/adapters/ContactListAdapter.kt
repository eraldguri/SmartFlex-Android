package com.erald_guri.smartflex_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erald_guri.smartflex_android.data.model.ContactModel
import com.erald_guri.smartflex_android.databinding.LayoutContactListItemBinding
import com.erald_guri.smartflex_android.holders.ContactListViewHolder

class ContactListAdapter(
    private val context: Context,
    private val contacts: List<ContactModel>
) : RecyclerView.Adapter<ContactListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutContactListItemBinding.inflate(inflater, parent, false)
        return ContactListViewHolder(context, binding)
    }

    override fun onBindViewHolder(holder: ContactListViewHolder, position: Int) {
        val contact = contacts[position]
        holder.onBind(contact)
    }

    override fun getItemCount(): Int = contacts.size
}
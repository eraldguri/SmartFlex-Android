package com.erald_guri.smartflex_android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erald_guri.smartflex_android.data.model.SocialLinkAccountModel
import com.erald_guri.smartflex_android.databinding.LayoutSocialLinkItemBinding
import com.erald_guri.smartflex_android.holders.SocialAccountViewHolder

class SocialAccountAdapter(
    private val links: ArrayList<SocialLinkAccountModel>
) : RecyclerView.Adapter<SocialAccountViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SocialAccountViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutSocialLinkItemBinding.inflate(inflater, parent, false)
        return SocialAccountViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SocialAccountViewHolder, position: Int) {
        val link = links[position]
        holder.onBind(link)
    }

    override fun getItemCount(): Int = links.size
}
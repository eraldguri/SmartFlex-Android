package com.erald_guri.smartflex_android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erald_guri.smartflex_android.data.model.SocialLinkAccountModel
import com.erald_guri.smartflex_android.databinding.LayoutSocialLinkItemBinding
import com.erald_guri.smartflex_android.holders.SocialAccountViewHolder
import com.erald_guri.smartflex_android.interfaces.OnTaskListener

class SocialAccountAdapter(
    private val links: ArrayList<SocialLinkAccountModel>,
    private val onTaskListener: OnTaskListener<SocialLinkAccountModel>
) : RecyclerView.Adapter<SocialAccountViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SocialAccountViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutSocialLinkItemBinding.inflate(inflater, parent, false)
        return SocialAccountViewHolder(binding, onTaskListener)
    }

    override fun onBindViewHolder(holder: SocialAccountViewHolder, position: Int) {
        val link = links[position]
        holder.onBind(link)
    }

    override fun getItemCount(): Int = links.size

    fun addLink(link: SocialLinkAccountModel) {
        if (!links.contains(link)) {
            links.add(link)
            notifyDataSetChanged()
        }
    }

    fun updateLink(position: Int, link: SocialLinkAccountModel) {
        links[position].title = link.title
        links[position].link = link.link
        notifyDataSetChanged()
    }

    fun removeLink(link: SocialLinkAccountModel) {
        links.remove(link)
        notifyDataSetChanged()
    }
}
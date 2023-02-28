package com.erald_guri.smartflex_android.holders

import com.erald_guri.smartflex_android.base.BaseViewHolder
import com.erald_guri.smartflex_android.data.model.NoteModel
import com.erald_guri.smartflex_android.databinding.LayoutNoteToolsBinding
import com.erald_guri.smartflex_android.interfaces.OnItemClickListener

class NoteToolsViewHolder(
    private val binding: LayoutNoteToolsBinding,
    private val onItemClickListener: OnItemClickListener<Int>
) : BaseViewHolder<Int>(binding.root) {

    override fun onBind(item: Int) {
        binding.img.setBackgroundResource(item)

        binding.img.setOnClickListener {
            onItemClickListener.onItemClick(adapterPosition, item)
        }
    }

}
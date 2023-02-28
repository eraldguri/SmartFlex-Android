package com.erald_guri.smartflex_android.holders

import com.erald_guri.smartflex_android.base.BaseViewHolder
import com.erald_guri.smartflex_android.data.model.NoteModel
import com.erald_guri.smartflex_android.databinding.LayoutNoteToolsBinding

class NoteToolsViewHolder(private val binding: LayoutNoteToolsBinding) : BaseViewHolder<Int>(binding.root) {

    override fun onBind(item: Int) {
        binding.img.setBackgroundResource(item)
    }

}
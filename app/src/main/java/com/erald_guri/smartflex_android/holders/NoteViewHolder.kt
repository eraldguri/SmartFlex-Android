package com.erald_guri.smartflex_android.holders

import com.erald_guri.smartflex_android.base.BaseViewHolder
import com.erald_guri.smartflex_android.data.model.NoteModel
import com.erald_guri.smartflex_android.databinding.LayoutNoteItemBinding
import com.erald_guri.smartflex_android.interfaces.OnTaskListener

class NoteViewHolder(
    private val binding: LayoutNoteItemBinding,
    private val taskListener: OnTaskListener<NoteModel>
) : BaseViewHolder<NoteModel>(binding.root) {

    override fun onBind(item: NoteModel) {
        binding.apply {
            tvTile.text = item.title
            tvDateTime.text = item.dateTime
            tvDescriptionPreview.text = item.description

            root.setOnClickListener { taskListener.onItemClick(item) }
            btnEdit.setOnClickListener { taskListener.onEdit(adapterPosition, item) }
            btnDelete.setOnClickListener { taskListener.onDelete(adapterPosition, item) }
        }
    }
}
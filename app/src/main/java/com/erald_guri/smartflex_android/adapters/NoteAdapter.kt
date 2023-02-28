package com.erald_guri.smartflex_android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erald_guri.smartflex_android.data.model.NoteModel
import com.erald_guri.smartflex_android.databinding.LayoutNoteItemBinding
import com.erald_guri.smartflex_android.holders.NoteViewHolder
import com.erald_guri.smartflex_android.interfaces.OnTaskListener

class NoteAdapter(
    private val notes: ArrayList<NoteModel>,
    private val taskListener: OnTaskListener<NoteModel>
) : RecyclerView.Adapter<NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutNoteItemBinding.inflate(inflater, parent, false)
        return NoteViewHolder(binding, taskListener)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.onBind(note)
    }

    override fun getItemCount(): Int = notes.size

    fun addNote(note: NoteModel) {
        if (!notes.contains(note)) {
            notes.add(note)
            notifyDataSetChanged()
        }
    }

    fun updateNote(position: Int, note: NoteModel) {
        notes[position].title = note.title
        notes[position].description = note.description
        notifyDataSetChanged()
    }

    fun removeNote(note: NoteModel) {
        notes.remove(note)
        notifyDataSetChanged()
    }
}
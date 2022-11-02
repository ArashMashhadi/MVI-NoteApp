package com.arash.mvinote.adapter

import androidx.recyclerview.widget.DiffUtil
import com.arash.mvinote.data.model.NoteEntity

object DiffUtilsNoteAdapter : DiffUtil.ItemCallback<NoteEntity>() {
    override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
        return oldItem == newItem
    }
}
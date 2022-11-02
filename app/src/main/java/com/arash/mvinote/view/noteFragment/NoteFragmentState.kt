package com.arash.mvinote.view.noteFragment

import com.arash.mvinote.data.model.NoteEntity

sealed class NoteFragmentState {
    object Empty : NoteFragmentState()
    data class LoadNote(val list: MutableList<NoteEntity>) : NoteFragmentState()
    data class DeleteNote(val unit: Unit) : NoteFragmentState()
    data class GoToDetail(val id: Int) : NoteFragmentState()

}

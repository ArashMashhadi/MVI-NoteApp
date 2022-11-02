package com.arash.mvinote.view.noteFragment

import com.arash.mvinote.data.model.NoteEntity

sealed class NoteFragmentIntent {
    object LoadAllNote : NoteFragmentIntent()
    data class SearchNote(val search: String) : NoteFragmentIntent()
    data class FilterNote(val filter: String) : NoteFragmentIntent()
    data class DeleteNote(val noteEntity: NoteEntity) : NoteFragmentIntent()
    data class ClickToDetail(val id: Int) : NoteFragmentIntent()
}

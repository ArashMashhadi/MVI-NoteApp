package com.arash.mvinote.view.addFragment

import com.arash.mvinote.data.model.NoteEntity

sealed class AddFragmentIntent {
    object SpinnerList : AddFragmentIntent()
    data class SaveNote(val noteEntity: NoteEntity) : AddFragmentIntent()
    data class UpdateNote(val noteEntity: NoteEntity) : AddFragmentIntent()
    data class NoteDetail(val id: Int) : AddFragmentIntent()
}

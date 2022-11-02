package com.arash.mvinote.view.addFragment

import com.arash.mvinote.data.model.ModelSpinner
import com.arash.mvinote.data.model.NoteEntity

sealed class AddFragmentState {
    object Idle : AddFragmentState()
    data class SpinnerData(
        val categoryList: MutableList<ModelSpinner>,
        val priorityList: MutableList<ModelSpinner>,
    ) : AddFragmentState()

    data class Error(val message: String) : AddFragmentState()
    data class SaveNote(val unit: Unit) : AddFragmentState()
    data class UpdateNote(val unit: Unit) : AddFragmentState()
    data class NoteDetail(val noteEntity: NoteEntity) : AddFragmentState()

}

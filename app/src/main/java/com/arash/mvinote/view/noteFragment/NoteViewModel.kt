package com.arash.mvinote.view.noteFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arash.mvinote.data.model.NoteEntity
import com.arash.mvinote.data.repository.noteRepository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val noteRepository: NoteRepository) : ViewModel() {
    private val _state = MutableStateFlow<NoteFragmentState>(NoteFragmentState.Empty)
    val state: StateFlow<NoteFragmentState> get() = _state

    fun handleIntent(noteFragmentIntent: NoteFragmentIntent) =
        viewModelScope.launch(Dispatchers.IO) {
            when (noteFragmentIntent) {
                is NoteFragmentIntent.LoadAllNote -> loadAllNoteList()
                is NoteFragmentIntent.SearchNote -> searchNote(noteFragmentIntent.search)
                is NoteFragmentIntent.FilterNote -> filterNote(noteFragmentIntent.filter)
                is NoteFragmentIntent.DeleteNote -> deleteNote(noteFragmentIntent.noteEntity)
                is NoteFragmentIntent.ClickToDetail -> goToDetailPage(noteFragmentIntent.id)
            }
        }

    private fun loadAllNoteList() = viewModelScope.launch(Dispatchers.IO) {
        val data = noteRepository.getAll()
        data.collect {
            _state.value = if (it.isNotEmpty()) {
                NoteFragmentState.LoadNote(it)
            } else {
                NoteFragmentState.Empty
            }
        }
    }

    private fun searchNote(string: String) = viewModelScope.launch(Dispatchers.IO) {
        val data = noteRepository.searchNote(string)
        data.collect {
            _state.value = if (it.isNotEmpty()) {
                NoteFragmentState.LoadNote(it)
            } else {
                NoteFragmentState.Empty
            }
        }
    }

    private fun filterNote(string: String) = viewModelScope.launch(Dispatchers.IO) {
        val data = noteRepository.filterNote(string)
        data.collect {
            _state.value = if (it.isNotEmpty()) {
                NoteFragmentState.LoadNote(it)
            } else {
                NoteFragmentState.Empty
            }
        }
    }

    private fun deleteNote(noteEntity: NoteEntity) = viewModelScope.launch(Dispatchers.IO) {
        _state.value = NoteFragmentState.DeleteNote(noteRepository.delete(noteEntity))
    }

    private fun goToDetailPage(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        _state.value = NoteFragmentState.GoToDetail(id)
    }
}


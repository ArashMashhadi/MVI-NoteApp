package com.arash.mvinote.view.addFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arash.mvinote.R
import com.arash.mvinote.data.model.ModelSpinner
import com.arash.mvinote.data.model.NoteEntity
import com.arash.mvinote.data.repository.addRepository.AddRepository
import com.arash.mvinote.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(private val addRepository: AddRepository) : ViewModel() {
    val addIntent = Channel<AddFragmentIntent>()
    private val _state = MutableStateFlow<AddFragmentState>(AddFragmentState.Idle)
    val state: StateFlow<AddFragmentState> get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() = viewModelScope.launch(Dispatchers.IO) {
        addIntent.consumeAsFlow().collect {
            when (it) {
                is AddFragmentIntent.SpinnerList -> spinnerList()
                is AddFragmentIntent.SaveNote -> saveData(it.noteEntity)
                is AddFragmentIntent.UpdateNote -> updateData(it.noteEntity)
                is AddFragmentIntent.NoteDetail -> noteDetail(it.id)
            }
        }
    }

    private fun saveData(noteEntity: NoteEntity) = viewModelScope.launch(Dispatchers.IO) {
        _state.value = try {
            AddFragmentState.SaveNote(addRepository.insert(noteEntity))
        } catch (e: Exception) {
            AddFragmentState.Error(e.message.toString())
        }
    }

    private fun updateData(noteEntity: NoteEntity) = viewModelScope.launch(Dispatchers.IO) {
        _state.value = try {
            AddFragmentState.UpdateNote(addRepository.update(noteEntity))
        } catch (e: Exception) {
            AddFragmentState.Error(e.message.toString())
        }
    }

    private fun spinnerList() = viewModelScope.launch(Dispatchers.IO) {
        val modelCategorySpinner = mutableListOf(
            ModelSpinner(WORK, R.drawable.ic_work),
            ModelSpinner(HOME, R.drawable.ic_home),
            ModelSpinner(EDUCATION, R.drawable.ic_education),
            ModelSpinner(HEALTH, R.drawable.ic_health)
        )
        val modelPrioritySpinner = mutableListOf(
            ModelSpinner(LOW, R.color.yellow),
            ModelSpinner(NORMAL, R.color.green),
            ModelSpinner(HIGH, R.color.red),
        )
        _state.value = AddFragmentState.SpinnerData(modelCategorySpinner, modelPrioritySpinner)
    }

    private fun noteDetail(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        addRepository.getNot(id).collect {
            _state.value = AddFragmentState.NoteDetail(it)
        }
    }
}


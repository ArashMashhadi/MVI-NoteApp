package com.arash.mvinote.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.arash.mvinote.data.model.NoteEntity
import com.arash.mvinote.databinding.FragmentAddBinding
import com.arash.mvinote.utils.*
import com.arash.mvinote.view.addFragment.AddFragmentIntent
import com.arash.mvinote.view.addFragment.AddFragmentState
import com.arash.mvinote.view.addFragment.AddViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AddFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private var categoryString = ""
    private var priorityString = ""
    private var categoryList: Array<String> = arrayOf(WORK, HOME, EDUCATION, HEALTH)
    private var priorityList: Array<String> = arrayOf(LOW, NORMAL, HIGH)
    private var noteId = 0
    private var type = ""
    private val addViewModel: AddViewModel by viewModels()

    @Inject
    lateinit var noteEntity: NoteEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Bundle And Type
        getBundleAndType()
        //Close
        closeImg()
        //Send Data On Channel
        sendData()
        //Get Data
        getData()
        //Save
        saveAndUpdate()
    }

    private fun getBundleAndType() {
        //Bundle
        noteId = arguments?.getInt(BUNDLE_ID) ?: 0
        //Type
        type = if (noteId > 0) EDIT else NEW
    }

    private fun closeImg() {
        binding.closeImg.setOnClickListener {
            dismiss()
        }
    }

    private fun sendData() {
        lifecycleScope.launch {
            //Spinner
            addViewModel.addIntent.send(AddFragmentIntent.SpinnerList)
            //NoteDetail
            if (type == EDIT) {
                addViewModel.addIntent.send(AddFragmentIntent.NoteDetail(noteId))
            }
        }
    }

    private fun getData() {
        binding.apply {
            lifecycleScope.launch {
                addViewModel.state.collect {
                    when (it) {
                        is AddFragmentState.Idle -> {}
                        is AddFragmentState.SpinnerData -> {
                            //Category
                            categoriesSpinner.setupListWithAdapter(it.categoryList) { stringCategory ->
                                categoryString = stringCategory
                            }
                            //Priority
                            prioritySpinner.setupListWithAdapter(it.priorityList) { stringPriority ->
                                priorityString = stringPriority
                            }
                        }
                        is AddFragmentState.SaveNote -> {
                            Toast.makeText(requireActivity(), "Save Success", Toast.LENGTH_SHORT)
                                .show()
                            dismiss()
                        }
                        is AddFragmentState.Error -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                        is AddFragmentState.NoteDetail -> {
                            titleEdt.setText(it.noteEntity.title)
                            descEdt.setText(it.noteEntity.description)
                            categoriesSpinner.setSelection(categoryList.getIndexFromList(
                                categoryList,
                                it.noteEntity.category))
                            prioritySpinner.setSelection(priorityList.getIndexFromList(priorityList,
                                it.noteEntity.priority))
                        }
                        is AddFragmentState.UpdateNote -> {
                            dismiss()
                            Toast.makeText(requireContext(), "Update Success", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun saveAndUpdate() {
        binding.apply {
            saveNot.setOnClickListener {
                val title = titleEdt.text.toString()
                val description = descEdt.text.toString()
                noteEntity.id = noteId
                noteEntity.title = title
                noteEntity.description = description
                noteEntity.category = categoryString
                noteEntity.priority = priorityString

                lifecycleScope.launch {
                    if (type == NEW) {
                        addViewModel.addIntent.send(AddFragmentIntent.SaveNote(noteEntity))
                    } else {
                        addViewModel.addIntent.send(AddFragmentIntent.UpdateNote(noteEntity))
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
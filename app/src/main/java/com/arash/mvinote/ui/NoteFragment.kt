package com.arash.mvinote.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.arash.mvinote.R
import com.arash.mvinote.adapter.NoteAdapter
import com.arash.mvinote.data.model.NoteEntity
import com.arash.mvinote.databinding.FragmentNoteBinding
import com.arash.mvinote.utils.*
import com.arash.mvinote.view.noteFragment.NoteFragmentIntent
import com.arash.mvinote.view.noteFragment.NoteFragmentState
import com.arash.mvinote.view.noteFragment.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NoteFragment : Fragment() {
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private val noteAdapter: NoteAdapter by lazy { NoteAdapter() }
    private val noteViewModel: NoteViewModel by viewModels()
    private var selectItem = 0

    @Inject
    lateinit var entity: NoteEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        //Open BottomShit
        openBottomShit()
        //Call Intent
        callIntent()
        //Get Data
        getData()
        //Click
        clickListenerAdapter()
    }

    private fun callIntent() {
        noteViewModel.handleIntent(NoteFragmentIntent.LoadAllNote)
    }

    private fun openBottomShit() {
        binding.add.setOnClickListener {
            AddFragment().show(childFragmentManager, AddFragment().tag)
        }
    }

    private fun getData() {
        binding.apply {
            lifecycleScope.launch {
                noteViewModel.state.collect {
                    when (it) {
                        is NoteFragmentState.Empty -> {
                            emptyLay.isVisible = true
                            noteListRecycler.isVisible = false
                        }
                        is NoteFragmentState.LoadNote -> {
                            emptyLay.isVisible = false
                            noteListRecycler.isVisible = true
                            noteAdapter.submitList(it.list)
                            noteListRecycler.apply {
                                layoutManager =
                                    StaggeredGridLayoutManager(2,
                                        StaggeredGridLayoutManager.VERTICAL)
                                adapter = noteAdapter
                            }
                        }
                        is NoteFragmentState.DeleteNote -> {
                            Toast.makeText(requireActivity(), "Delete success", Toast.LENGTH_SHORT)
                                .show()
                        }
                        is NoteFragmentState.GoToDetail -> {
                            val addFragment = AddFragment()
                            val bundle = Bundle()
                            bundle.putInt(BUNDLE_ID, it.id)
                            addFragment.arguments = bundle
                            addFragment.show(childFragmentManager, AddFragment().tag)
                        }
                    }
                }
            }
        }
    }

    private fun clickListenerAdapter() {
        noteAdapter.setOnItemClickListener { noteEntity, state ->
            when (state) {
                EDIT -> {
                    noteViewModel.handleIntent(NoteFragmentIntent.ClickToDetail(noteEntity.id))
                }
                DELETE -> {
                    entity.id = noteEntity.id
                    entity.title = noteEntity.title
                    entity.description = noteEntity.description
                    entity.category = noteEntity.category
                    entity.priority = noteEntity.priority
                    noteViewModel.handleIntent(NoteFragmentIntent.DeleteNote(entity))
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionFilter -> {
                filterByPriority()
            }
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    private fun filterByPriority() {
        val builder = AlertDialog.Builder(requireContext())
        val priority = arrayOf(ALL, HIGH, NORMAL, LOW)
        builder.setSingleChoiceItems(priority, selectItem) { dialog, item ->
            when (item) {
                0 -> noteViewModel.handleIntent(NoteFragmentIntent.LoadAllNote)
                in 1..3 -> noteViewModel.handleIntent(NoteFragmentIntent.FilterNote(priority[item]))
            }
            selectItem = item
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item, menu)
        val search = menu.findItem(R.id.actionSearch)
        val searchView = search.actionView as SearchView
        searchView.queryHint = "Search ...."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                noteViewModel.handleIntent(NoteFragmentIntent.SearchNote(newText))
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
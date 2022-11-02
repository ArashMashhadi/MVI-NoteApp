package com.arash.mvinote.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arash.mvinote.R
import com.arash.mvinote.data.model.NoteEntity
import com.arash.mvinote.databinding.RecyclerItemBinding
import com.arash.mvinote.utils.*

class NoteAdapter : ListAdapter<NoteEntity, NoteAdapter.ViewHolder>(DiffUtilsNoteAdapter) {
    private lateinit var binding: RecyclerItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = RecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, parent.context, onItemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(currentList[position])
    }

    class ViewHolder(
        private val binding: RecyclerItemBinding,
        private val context: Context,
        private var onItemClickListener: ((NoteEntity, String) -> Unit)? = null,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setData(item: NoteEntity) {
            binding.apply {
                titleTxt.text = item.title
                descTxt.text = item.description
                //Priority
                when (item.priority) {
                    HIGH -> viewColor.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.red
                        )
                    )
                    NORMAL -> viewColor.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.green
                        )
                    )
                    LOW -> viewColor.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.yellow
                        )
                    )
                }
                //Category
                when (item.category) {
                    HOME -> iconImg.setImageResource(R.drawable.ic_home)
                    WORK -> iconImg.setImageResource(R.drawable.ic_work)
                    EDUCATION -> iconImg.setImageResource(R.drawable.ic_education)
                    HEALTH -> iconImg.setImageResource(R.drawable.ic_health)
                }
                //Menu
                menuImg.setOnClickListener {
                    val popupMenu = PopupMenu(context, it)
                    popupMenu.menuInflater.inflate(R.menu.menu_item1, popupMenu.menu)
                    popupMenu.show()
                    //Click
                    popupMenu.setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.itemEdit -> {
                                onItemClickListener?.let {
                                    it(item, EDIT)
                                }
                            }
                            R.id.itemDelete -> {
                                onItemClickListener?.let {
                                    it(item, DELETE)
                                }
                            }
                        }
                        return@setOnMenuItemClickListener true
                    }
                }
            }
        }
    }

    private var onItemClickListener: ((NoteEntity, String) -> Unit)? = null

    fun setOnItemClickListener(listener: (NoteEntity, String) -> Unit) {
        onItemClickListener = listener
    }
}
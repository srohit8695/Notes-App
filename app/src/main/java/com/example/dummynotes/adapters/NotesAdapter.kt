package com.example.dummynotes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.dummynotes.R
import com.example.dummynotes.database.NotesEntity
import com.example.dummynotes.viewModels.NotesViewModel
import kotlinx.android.synthetic.main.recyclerview_items.view.*

class NotesRecyclerAdapter(val notesViewModel: NotesViewModel, val arrayList: ArrayList<NotesEntity>, val context: Context) : RecyclerView.Adapter<NotesRecyclerAdapter.NotesViewHolder>(){


    fun swapItems(fromPosition: Int, toPosition: Int) {
        try {
            if (fromPosition < toPosition) {
                for (i in fromPosition..toPosition - 1) {
                    arrayList[i] = arrayList.set(i+1, arrayList[i])
                }
            } else {
                for (i in fromPosition..toPosition + 1) {
                    arrayList[i] = arrayList.set(i-1, arrayList[i])
                }
            }

            notifyItemMoved(fromPosition, toPosition)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    inner class NotesViewHolder(private val binding: View) : RecyclerView.ViewHolder(binding){
        fun bind(notes : NotesEntity){
            binding.description.text = notes.notes
            binding.title.text = notes.title
            binding.delete.setOnClickListener {
                notesViewModel.removeNotes(notes)
                notifyItemRemoved(arrayList.indexOf(notes))
//                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesRecyclerAdapter.NotesViewHolder {
        return NotesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_items, parent, false))
    }

    override fun onBindViewHolder(holder: NotesRecyclerAdapter.NotesViewHolder, position: Int) {
        holder.bind(arrayList.get(position))
    }

    override fun getItemCount() = arrayList.size

}
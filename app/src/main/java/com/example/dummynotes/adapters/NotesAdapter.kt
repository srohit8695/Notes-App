package com.example.dummynotes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dummynotes.R
import com.example.dummynotes.model.Notes
import com.example.dummynotes.viewModels.NotesViewModel
import kotlinx.android.synthetic.main.recyclerview_items.view.*

class NotesRecyclerAdapter (val notesViewModel: NotesViewModel, val arrayList: ArrayList<Notes>, val context: Context) : RecyclerView.Adapter<NotesRecyclerAdapter.NotesViewHolder>(){



    inner class NotesViewHolder(private val binding: View) : RecyclerView.ViewHolder(binding){
        fun bind(notes : Notes){
            binding.description.text = notes.description
            binding.delete.setOnClickListener {
                notesViewModel.removeNotes(notes)
                notifyItemRemoved(arrayList.indexOf(notes))
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
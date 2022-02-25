package com.example.dummynotes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.dummynotes.R
import com.example.dummynotes.database.NotesEntity
import com.example.dummynotes.model.Notes
import com.example.dummynotes.viewModels.NotesViewModel
import kotlinx.android.synthetic.main.recyclerview_items.view.*
import java.util.*
import kotlin.collections.ArrayList

class NotesRecyclerAdapter( val context: Context, private val mListener: (NotesEntity) -> Unit) : RecyclerView.Adapter<NotesRecyclerAdapter.NotesViewHolder>(){
    private val arrayList = ArrayList<NotesEntity>()

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
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesRecyclerAdapter.NotesViewHolder {
        return NotesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_items, parent, false))
    }

    override fun onBindViewHolder(holder: NotesRecyclerAdapter.NotesViewHolder, position: Int) {
        holder.bind(arrayList[position])
        holder.itemView.setOnClickListener {
            mListener.invoke(arrayList[position])
        }
    }

    override fun getItemCount() = arrayList.size

    fun updateList(newList: List<NotesEntity>) {
        arrayList.clear()
        arrayList.addAll(newList)
        notifyDataSetChanged()
    }

    fun noteFromPosition(position : Int) : NotesEntity{
        return arrayList[position]
    }

}




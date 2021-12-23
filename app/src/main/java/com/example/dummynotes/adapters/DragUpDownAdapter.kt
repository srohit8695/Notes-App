package com.example.dummynotes.adapters

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class DragUpDownAdapter(adapter: NotesRecyclerAdapter, dragDirections: Int, swipeDirections: Int) : ItemTouchHelper.SimpleCallback(dragDirections, swipeDirections)
{
    var notesAdapter = adapter
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        notesAdapter.swapItems(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

    }

}
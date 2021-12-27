package com.example.dummynotes


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.dummynotes.activities.AddNotes
import com.example.dummynotes.adapters.DeleteIconInterface
import com.example.dummynotes.adapters.DragUpDownAdapter
import com.example.dummynotes.adapters.NotesRecyclerAdapter
import com.example.dummynotes.database.NotesEntity
import com.example.dummynotes.databinding.ActivityMainBinding
import com.example.dummynotes.viewModels.NotesViewModel

class MainActivity : AppCompatActivity(), DeleteIconInterface {

    private lateinit var viewModel: NotesViewModel
    private lateinit var mainBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        try {
            viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
            val dataAdapter = NotesRecyclerAdapter(this,  this)
            mainBinding.recyclerView.adapter = dataAdapter

            viewModel.list.observe(
                this, {
                    dataAdapter.updateList(it)
                }
            )

            val callback = DragUpDownAdapter(dataAdapter,
                ItemTouchHelper.UP.or(ItemTouchHelper.DOWN), 0)
            val helper = ItemTouchHelper(callback)
            helper.attachToRecyclerView(mainBinding.recyclerView)

            mainBinding.addNotesFab.setOnClickListener {
                val intent = Intent(this, AddNotes::class.java )
                startActivity(intent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }




    }

    override fun onDeleteIconClick(note: NotesEntity) {
        viewModel.removeNotes(note)
    }


}


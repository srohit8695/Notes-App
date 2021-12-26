package com.example.dummynotes


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.dummynotes.activities.AddNotes
import com.example.dummynotes.adapters.DragUpDownAdapter
import com.example.dummynotes.adapters.NotesRecyclerAdapter
import com.example.dummynotes.database.NotesEntity
import com.example.dummynotes.databinding.ActivityMainBinding
import com.example.dummynotes.viewModels.NotesViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: NotesViewModel
    private lateinit var mainBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        viewModel.list.observe(
            this, {
                   val adapterData = NotesRecyclerAdapter(viewModel,
                    it as ArrayList<NotesEntity>, this)
                mainBinding.recyclerView.adapter = adapterData

                val callback = DragUpDownAdapter(adapterData,
                    ItemTouchHelper.UP.or(ItemTouchHelper.DOWN), 0)
                val helper = ItemTouchHelper(callback)
                helper.attachToRecyclerView(mainBinding.recyclerView)
            }
        )



        /*mainBinding.addNotes.setOnClickListener {
            addData()
        }*/

        mainBinding.addNotesFab.setOnClickListener {
            val intent = Intent(this, AddNotes::class.java )
            startActivity(intent)
        }

    }


    /*fun addData(){
        try {
            if (mainBinding.notes.text!!.isNotEmpty()) {
                 val notes = NotesEntity(mainBinding.notes.text.toString())
                viewModel.addNotes(notes)
                mainBinding.notes!!.text!!.clear()
                viewModel.totalNotes()
                    ?.let { mainBinding.recyclerView.adapter?.notifyItemInserted(it.value!!.size) }
            } else {
                Toast.makeText(this, "Enter Notes", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }*/


}


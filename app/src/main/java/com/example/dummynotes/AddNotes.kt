package com.example.dummynotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.dummynotes.database.NotesEntity
import com.example.dummynotes.databinding.ActivityAddNotesBinding
import com.example.dummynotes.databinding.ActivityMainBinding
import com.example.dummynotes.viewModels.NotesViewModel
import kotlinx.android.synthetic.main.activity_main.*

class AddNotes : AppCompatActivity() {

    private lateinit var viewModel: NotesViewModel
    private lateinit var addNotesBinding: ActivityAddNotesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addNotesBinding = ActivityAddNotesBinding.inflate(layoutInflater)
        setContentView(addNotesBinding.root)

        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        addNotesBinding.saveNotes.setOnClickListener {

            addNotes()

        }

    }

    fun addNotes(){
        try {
            if (addNotesBinding.title.text!!.isNotEmpty()&&addNotesBinding.description.text!!.isNotEmpty()) {
                val notes = NotesEntity(addNotesBinding.title.text.toString(),addNotesBinding.description.text.toString())
                viewModel.addNotes(notes)
                addNotesBinding.title!!.text!!.clear()
                addNotesBinding.description!!.text!!.clear()
                /*viewModel.totalNotes()
                    ?.let { mainBinding.recyclerView.adapter?.notifyItemInserted(it.value!!.size) }*/
            } else {
                Toast.makeText(this, "Enter Notes", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}
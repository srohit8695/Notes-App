package com.example.dummynotes.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dummynotes.database.NotesEntity
import com.example.dummynotes.databinding.ActivityAddNotesBinding
import com.example.dummynotes.viewModels.NotesViewModel
import com.google.android.material.snackbar.Snackbar

class AddNotes : AppCompatActivity() {

    private lateinit var viewModel: NotesViewModel
    private lateinit var addNotesBinding: ActivityAddNotesBinding
    private lateinit var notesString: String
    private lateinit var titleString: String
    private lateinit var positionID: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            addNotesBinding = ActivityAddNotesBinding.inflate(layoutInflater)
            setContentView(addNotesBinding.root)

            viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

            notesString = intent.getStringExtra("message")?:"no data"
            titleString = intent.getStringExtra("title")?:"no data"
            positionID = intent.getStringExtra("id")?:"new"

            if (!positionID.equals("new")) {
                addNotesBinding.title.setText(titleString)
                addNotesBinding.description.setText(notesString)
            }

            addNotesBinding.saveNotes.setOnClickListener {
                addNotes()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun addNotes(){
        try {
            if (addNotesBinding.title.text!!.isNotEmpty()) {
                if (addNotesBinding.description.text!!.isNotEmpty()) {

                    if (positionID == "new") {

                        viewModel.addNotes(NotesEntity(addNotesBinding.title.text.toString(),addNotesBinding.description.text.toString()))
                        addNotesBinding.title.text!!.clear()
                        addNotesBinding.description.text!!.clear()
                        Toast.makeText(baseContext,"Saved Successfully", Toast.LENGTH_SHORT).show()

                    } else {

                        val updatedNote = NotesEntity(addNotesBinding.title.text.toString(),addNotesBinding.description.text.toString(), positionID.toInt() )
                        viewModel.updateNotes(updatedNote)
                        Toast.makeText(baseContext,"Updated Successfully", Toast.LENGTH_SHORT).show()

                    }

                } else {
                    Toast.makeText(this, "Enter Description", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Enter Title", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}
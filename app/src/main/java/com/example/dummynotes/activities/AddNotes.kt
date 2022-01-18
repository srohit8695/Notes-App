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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            addNotesBinding = ActivityAddNotesBinding.inflate(layoutInflater)
            setContentView(addNotesBinding.root)

            viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

            addNotesBinding.saveNotes.setOnClickListener {
                addNotes()
            }

            notesString = intent.getStringExtra("title").toString()
            titleString = intent.getStringExtra("message").toString()

            if (!notesString.equals("null")) {
                addNotesBinding.title.setText(titleString)
                addNotesBinding.description.setText(notesString)
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun addNotes(){
        try {
            if (addNotesBinding.title.text!!.isNotEmpty()) {
                if (addNotesBinding.description.text!!.isNotEmpty()) {
                    val insertData = viewModel.addNotes(NotesEntity(addNotesBinding.title.text.toString(),addNotesBinding.description.text.toString()))

//                    if(insertData == dataSize){
                        addNotesBinding.title!!.text!!.clear()
                        addNotesBinding.description!!.text!!.clear()
                    Snackbar.make(View(this@AddNotes),"Saved Successfully",Snackbar.LENGTH_LONG).show()
                   /* }else{
                        Toast.makeText(this, "Issue in saving data", Toast.LENGTH_SHORT).show()
                    }*/

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
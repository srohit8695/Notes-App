package com.example.dummynotes.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dummynotes.database.NotesEntity
import com.example.dummynotes.databinding.ActivityAddNotesBinding
import com.example.dummynotes.viewModels.NotesViewModel
import com.google.android.material.slider.LabelFormatter


class AddNotes : AppCompatActivity() {

    private lateinit var viewModel: NotesViewModel
    private lateinit var addNotesBinding: ActivityAddNotesBinding
    private lateinit var notesString: String
    private lateinit var titleString: String
    private lateinit var positionID: String
    private var priorityLevelCount = 1.0f
    val priorityLevel = "Priority Level : "



    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            addNotesBinding = ActivityAddNotesBinding.inflate(layoutInflater)
            setContentView(addNotesBinding.root)

            viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

            notesString = intent.getStringExtra("message")?:"no data"
            titleString = intent.getStringExtra("title")?:"no data"
            positionID = intent.getStringExtra("id")?:"new"
            try {
                if (intent.hasExtra("priority")) {
                    if (true) {
                        priorityLevelCount = intent.getIntExtra("priority", 1).toFloat()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (!positionID.equals("new")) {
                addNotesBinding.title.setText(titleString)
                addNotesBinding.description.setText(notesString)
            }

            addNotesBinding.saveNotes.setOnClickListener {
                addNotes()
            }

            addNotesBinding.priorityText.text = priorityLevel+"Low & not important"
            addNotesBinding.prioritySlider.addOnChangeListener { slider, value, fromUser ->
                setPriorityText(addNotesBinding.prioritySlider.value)
            }

            if (priorityLevelCount>1.0f){
                if(priorityLevelCount == 2.0f){
                    addNotesBinding.prioritySlider.value = 2.0f
                }else if(priorityLevelCount == 3.0f){
                    addNotesBinding.prioritySlider.value = 3.0f
                }else if(priorityLevelCount == 4.0f){
                    addNotesBinding.prioritySlider.value = 4.0f
                }
                setPriorityText(priorityLevelCount)
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun setPriorityText(value : Float){
        when(value){
            1.0f -> addNotesBinding.priorityText.text = priorityLevel+"Low priority & not important"
            2.0f -> addNotesBinding.priorityText.text = priorityLevel+"Low priority & important"
            3.0f -> addNotesBinding.priorityText.text = priorityLevel+"High priority & not important"
            4.0f -> addNotesBinding.priorityText.text = priorityLevel+"High priority & important"
        }
    }

    private fun addNotes(){
        try {
            if (addNotesBinding.title.text!!.isNotEmpty()) {
                if (addNotesBinding.description.text!!.isNotEmpty()) {

                    if (positionID == "new") {

                        viewModel.addNotes(NotesEntity(addNotesBinding.title.text.toString(),addNotesBinding.description.text.toString(), addNotesBinding.prioritySlider.value.toInt()))
                        addNotesBinding.title.text!!.clear()
                        addNotesBinding.description.text!!.clear()
                        Toast.makeText(baseContext,"Saved Successfully", Toast.LENGTH_SHORT).show()

                    } else {

                        val updatedNote = NotesEntity(addNotesBinding.title.text.toString(),addNotesBinding.description.text.toString(), addNotesBinding.prioritySlider.value.toInt(), positionID.toInt())
                        viewModel.updateNotes(updatedNote)
                        Toast.makeText(baseContext,"Updated Successfully", Toast.LENGTH_SHORT).show()

                    }

                    finish()

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
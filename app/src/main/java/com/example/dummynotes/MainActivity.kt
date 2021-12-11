package com.example.dummynotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dummynotes.adapters.NotesRecyclerAdapter
import com.example.dummynotes.model.Notes
import com.example.dummynotes.viewModels.NotesViewModel
import com.example.dummynotes.viewModels.NotesViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: NotesViewModel
    private lateinit var addData : Button
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        addData = findViewById(R.id.addNotes)

        viewModel.list.observe(
            this, {
                recyclerView.adapter = NotesRecyclerAdapter(viewModel, it, this)
            }
        )

        addData.setOnClickListener {
            addData()
        }

    }

    fun addData(){
        try {
            val textData = findViewById<EditText>(R.id.notes)
            if (textData.text.isNotEmpty()) {
                 val notes = Notes(textData.text.toString())
                viewModel.addNotes(notes)
                textData.text.clear()
                recyclerView.adapter?.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Enter Notes", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
package com.example.dummynotes.viewModels

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class NotesViewModelFactory(context: Application) : ViewModelProvider.Factory {
    private val context = context
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NotesViewModel::class.java)){
            return NotesViewModel(context) as T
        }
        throw IllegalArgumentException ("UnknownViewModel")
    }

}
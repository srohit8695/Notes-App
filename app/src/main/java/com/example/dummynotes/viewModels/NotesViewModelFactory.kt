package com.example.dummynotes.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class NotesViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NotesViewModel::class.java)){
            return NotesViewModel() as T
        }
        throw IllegalArgumentException ("UnknownViewModel")
    }

}
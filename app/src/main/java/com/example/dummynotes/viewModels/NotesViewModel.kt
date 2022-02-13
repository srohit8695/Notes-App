package com.example.dummynotes.viewModels

import android.app.Application
import android.content.Context
import android.provider.ContactsContract
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dummynotes.database.NotesEntity
import com.example.dummynotes.database.NotesRepository

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : NotesRepository = NotesRepository(application)
    val list : LiveData<List<NotesEntity>>

    init {
        list = repository.getAllData()
    }


    fun addNotes(notes : NotesEntity) : Long{
        return repository.insertData(notes)
    }

    fun removeNotes(notes: NotesEntity){
        repository.deleteData(notes)
    }

    fun deleteByID(id : Int){
        repository.deleteById(id)
    }

    fun updateNotes(notes : NotesEntity){
        repository.updateData(notes)
    }

    fun totalNotes() = list

}
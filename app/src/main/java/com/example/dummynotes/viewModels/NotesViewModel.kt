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
    /*var list = MutableLiveData<ArrayList<NotesEntity>>()
    var tempList = arrayListOf<NotesEntity>()*/


    private val repository : NotesRepository = NotesRepository(application)
    val list : LiveData<List<NotesEntity>>

    init {
        list = repository.getAllData()
    }


    fun addNotes(notes : NotesEntity){
        /*tempList.clear()
        NotesRepository(context).insertData(notes)
        val datas : List<NotesEntity> = NotesRepository(context).getAllData()
        tempList.addAll(datas)
        list.value = tempList*/

        repository.insertData(notes)

    }

    fun removeNotes(notes: NotesEntity){
        /*tempList.remove(notes)
        NotesRepository(context).deleteData(notes)
        list.value = tempList*/

        repository.deleteData(notes)

    }

    fun totalNotes() = list//list.value?.size

}
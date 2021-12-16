package com.example.dummynotes.database

import android.content.Context
import androidx.lifecycle.LiveData

class NotesRepository(context: Context) {

    var dbms : NotesDao = NotesDb.getInstance(context)?.notesDao()!!

    fun insertData(notesEntity: NotesEntity){
        dbms.insertData(notesEntity)
    }

    fun deleteData(notesEntity: NotesEntity){
        dbms.deleteData(notesEntity)
    }

    fun updateData(notesEntity: NotesEntity){
        dbms.updateData(notesEntity)
    }

    fun getAllData() : LiveData<List<NotesEntity>>{
        return dbms.showAll()
    }


}
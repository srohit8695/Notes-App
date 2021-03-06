package com.example.dummynotes.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class NotesRepository(context: Context) {

    var dbms : NotesDao = NotesDb.getInstance(context)?.notesDao()!!

    fun insertData(notesEntity: NotesEntity) : Long{
        return dbms.insertData(notesEntity)
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

    fun deleteById(id:Int){
        dbms.deleteIndividual(id)
    }

    fun getPriority1() : LiveData<List<NotesEntity>>{
        return dbms.showAllOfPriority1()
    }

    fun getPriority2() : LiveData<List<NotesEntity>>{
        return dbms.showAllOfPriority2()
    }

    fun getPriority3() : LiveData<List<NotesEntity>>{
        return dbms.showAllOfPriority3()
    }

    fun getPriority4() : LiveData<List<NotesEntity>>{
        return dbms.showAllOfPriority4()
    }

}
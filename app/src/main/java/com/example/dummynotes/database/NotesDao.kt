package com.example.dummynotes.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface NotesDao {

    @Insert
    fun insertData(notesEntity: NotesEntity) : Long

    @Delete
    fun deleteData(notesEntity: NotesEntity)

    @Update
    fun updateData(notesEntity: NotesEntity)

    @Query("Select * from notesTable")
    fun showAll(): LiveData<List<NotesEntity>>

    @Query("DELETE FROM notesTable WHERE id = :id")
    fun deleteIndividual(id : Int)

    @Query("Select * from notesTable WHERE priority = 1")
    fun showAllOfPriority1(): LiveData<List<NotesEntity>>

    @Query("Select * from notesTable WHERE priority = 2")
    fun showAllOfPriority2(): LiveData<List<NotesEntity>>

    @Query("Select * from notesTable WHERE priority = 3")
    fun showAllOfPriority3(): LiveData<List<NotesEntity>>

    @Query("Select * from notesTable WHERE priority = 4")
    fun showAllOfPriority4(): LiveData<List<NotesEntity>>

}
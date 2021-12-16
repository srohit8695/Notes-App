package com.example.dummynotes.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotesDao {

    @Insert
    fun insertData(notesEntity: NotesEntity)

    @Delete
    fun deleteData(notesEntity: NotesEntity)

    @Update
    fun updateData(notesEntity: NotesEntity)

    @Query("Select * from notesTable")
    fun showAll(): LiveData<List<NotesEntity>>

}
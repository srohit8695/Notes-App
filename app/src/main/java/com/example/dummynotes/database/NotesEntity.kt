package com.example.dummynotes.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notesTable")
data class NotesEntity(
    val title : String,
    val notes: String,
    val priority: Int?,
    @PrimaryKey(autoGenerate = true) val id: Int?= null
)

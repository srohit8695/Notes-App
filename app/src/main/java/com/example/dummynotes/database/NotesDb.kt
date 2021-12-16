package com.example.dummynotes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [NotesEntity::class], version = 1, exportSchema = false)
@TypeConverters
abstract class NotesDb : RoomDatabase() {

    abstract fun notesDao() : NotesDao

    companion object{

        private var instance : NotesDb ? = null

        fun getInstance(context: Context) : NotesDb?{
            if(instance == null){
                synchronized(NotesDb::class){
                    instance = Room.databaseBuilder(context.applicationContext, NotesDb::class.java, "notes.db").allowMainThreadQueries().build()
                }
            }
            return instance
        }

        fun destroyInstance(){
            instance = null
        }
    }

}
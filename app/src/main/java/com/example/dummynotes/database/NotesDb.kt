package com.example.dummynotes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [NotesEntity::class], version = 2)
@TypeConverters
abstract class NotesDb : RoomDatabase() {

    abstract fun notesDao() : NotesDao

    companion object{

        private var instance : NotesDb ? = null

        val migrate_1_2 : Migration = object : Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE notesTable ADD COLUMN priority TINIINT DEFAULT 1")
            }
        }

        fun getInstance(context: Context) : NotesDb?{
            if(instance == null){
                synchronized(NotesDb::class){
                    instance = Room.databaseBuilder(context.applicationContext, NotesDb::class.java, "notes.db")
                        .addMigrations(migrate_1_2)
                        .allowMainThreadQueries().build()
                }
            }
            return instance
        }

        fun destroyInstance(){
            instance = null
        }
    }

}
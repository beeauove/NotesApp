package com.example.notesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesapp.dao.NoteDao
import com.example.notesapp.entities.Notes

@Database(entities =[Notes:: class], version=1, exportSchema = false )
abstract class notesDatabase: RoomDatabase() {
    companion object {
        var notesdatabase: notesDatabase? = null

        @Synchronized
        fun getDatabase(context:Context):notesDatabase{
            if (notesdatabase == null) {
                notesdatabase = Room.databaseBuilder(
                    context, notesDatabase::class.java, "Notes.db"
                ).build()
            }
            return notesdatabase!!
        }
    }
    abstract fun notedao():NoteDao

}
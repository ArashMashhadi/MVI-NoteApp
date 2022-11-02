package com.arash.mvinote.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arash.mvinote.data.model.NoteEntity

@Database(entities = [NoteEntity::class], version = 4)
abstract class NoteDataBase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
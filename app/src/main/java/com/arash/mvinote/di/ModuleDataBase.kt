package com.arash.mvinote.di

import android.content.Context
import androidx.room.Room
import com.arash.mvinote.data.db.NoteDao
import com.arash.mvinote.data.db.NoteDataBase
import com.arash.mvinote.data.model.NoteEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleDataBase {

    const val NOTE_TABLE = "note_table"
    private const val DATABASE_TABLE = "database_table"

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): NoteDataBase = Room.databaseBuilder(
        context, NoteDataBase::class.java, DATABASE_TABLE)
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideNoteDao(db: NoteDataBase): NoteDao = db.noteDao()

    @Provides
    @Singleton
    fun provideNoteEntity() = NoteEntity()

}
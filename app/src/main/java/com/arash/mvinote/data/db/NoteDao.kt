package com.arash.mvinote.data.db

import androidx.room.*
import com.arash.mvinote.data.model.NoteEntity
import com.arash.mvinote.di.ModuleDataBase.NOTE_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert
    suspend fun save(noteEntity: NoteEntity)

    @Delete
    suspend fun delete(noteEntity: NoteEntity)

    @Update
    suspend fun update(noteEntity: NoteEntity)

    @Query("DELETE FROM $NOTE_TABLE")
    suspend fun deleteAll()

    @Query("SELECT * FROM $NOTE_TABLE WHERE id == :id")
    fun getId(id: Int): Flow<NoteEntity>

    @Query("SELECT * FROM $NOTE_TABLE")
    fun getAllNote(): Flow<MutableList<NoteEntity>>

    @Query("SELECT * FROM $NOTE_TABLE WHERE priority == :priority")
    fun filterNote(priority: String): Flow<MutableList<NoteEntity>>

    @Query("SELECT * FROM $NOTE_TABLE WHERE title LIKE '%' || :title || '%'")
    fun searchNote(title: String): Flow<MutableList<NoteEntity>>
}
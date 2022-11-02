package com.arash.mvinote.data.repository.noteRepository

import com.arash.mvinote.data.model.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun delete(entity: NoteEntity)
    suspend fun deleteAll()
    fun getAll(): Flow<MutableList<NoteEntity>>
    fun filterNote(priority: String): Flow<MutableList<NoteEntity>>
    fun searchNote(title: String): Flow<MutableList<NoteEntity>>
}
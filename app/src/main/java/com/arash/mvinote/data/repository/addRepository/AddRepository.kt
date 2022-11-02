package com.arash.mvinote.data.repository.addRepository

import com.arash.mvinote.data.model.NoteEntity
import kotlinx.coroutines.flow.Flow

interface AddRepository {
    suspend fun insert(entity: NoteEntity)
    suspend fun delete(entity: NoteEntity)
    suspend fun update(entity: NoteEntity)
    suspend fun getNot(id: Int): Flow<NoteEntity>
}
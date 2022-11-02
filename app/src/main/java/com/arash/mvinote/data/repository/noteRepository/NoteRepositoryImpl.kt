package com.arash.mvinote.data.repository.noteRepository

import com.arash.mvinote.data.db.NoteDao
import com.arash.mvinote.data.model.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val dao: NoteDao) : NoteRepository {

    override suspend fun delete(entity: NoteEntity) {
        return dao.delete(entity)
    }

    override suspend fun deleteAll() {
        return dao.deleteAll()
    }

    override fun getAll(): Flow<MutableList<NoteEntity>> {
        return dao.getAllNote()
    }

    override fun filterNote(priority: String): Flow<MutableList<NoteEntity>> {
        return dao.filterNote(priority)
    }

    override fun searchNote(title: String): Flow<MutableList<NoteEntity>> {
        return dao.searchNote(title)
    }
}
package com.arash.mvinote.data.repository.addRepository

import com.arash.mvinote.data.db.NoteDao
import com.arash.mvinote.data.model.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddRepositoryImpl @Inject constructor(private val dao: NoteDao) : AddRepository {
    override suspend fun insert(entity: NoteEntity) {
        return dao.save(entity)
    }

    override suspend fun delete(entity: NoteEntity) {
        return dao.delete(entity)
    }

    override suspend fun update(entity: NoteEntity) {
        return dao.update(entity)
    }

    override suspend fun getNot(id: Int): Flow<NoteEntity> {
        return dao.getId(id)
    }
}
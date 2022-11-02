package com.arash.mvinote.di

import com.arash.mvinote.data.repository.addRepository.AddRepository
import com.arash.mvinote.data.repository.addRepository.AddRepositoryImpl
import com.arash.mvinote.data.repository.noteRepository.NoteRepository
import com.arash.mvinote.data.repository.noteRepository.NoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideAddRepository(addRepositoryImpl: AddRepositoryImpl): AddRepository

    @Binds
    abstract fun provideNoteRepository(noteRepositoryImpl: NoteRepositoryImpl): NoteRepository
}

package com.sdsu.noteme.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdsu.noteme.data.db.NotesAppDatabase
import com.sdsu.noteme.data.model.AllNotesModel
import com.sdsu.noteme.data.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllSubscriptionViewModel (application: Application): AndroidViewModel(application){

    private val notesRepository: NotesRepository

    init {
        val notesDao = NotesAppDatabase.getDatabase(application, viewModelScope).notesDao()
        notesRepository = NotesRepository(notesDao)
    }

    fun getAllNotes(noteType: String): LiveData<List<AllNotesModel>> {
        return notesRepository.getAllNotes(noteType);
    }

    fun deleteNote(notesModel: AllNotesModel) = viewModelScope.launch(Dispatchers.IO)  {
        notesRepository.deleteNoteRepo(notesModel)
    }

    fun undoNoteVm(notesModel: AllNotesModel) = viewModelScope.launch(Dispatchers.IO) {
        notesRepository.undoNoteRepo(notesModel)
    }
}

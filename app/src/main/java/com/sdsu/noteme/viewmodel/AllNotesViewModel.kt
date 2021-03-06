package com.sdsu.noteme.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sdsu.noteme.data.db.NotesAppDatabase
import com.sdsu.noteme.data.model.AllNotesModel
import com.sdsu.noteme.data.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllNotesViewModel (application: Application): AndroidViewModel(application){

    private val notesRepository: NotesRepository
    val data: MutableLiveData<AllNotesModel> by lazy {
        MutableLiveData<AllNotesModel>()
    }

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

    fun addNoteVm(notesModel: AllNotesModel) = viewModelScope.launch(Dispatchers.IO) {
        notesRepository.addNoteRepo(notesModel)
    }

    fun undoNoteVm(notesModel: AllNotesModel) = viewModelScope.launch(Dispatchers.IO) {
        notesRepository.undoNoteRepo(notesModel)
    }

    fun setValue(thisRef: AllNotesModel) {
        data.value = thisRef
    }

    fun getValue(): MutableLiveData<AllNotesModel> {
        return data
    }
}
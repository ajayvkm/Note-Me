package com.sdsu.noteme.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdsu.noteme.data.db.NotesAppDatabase
import com.sdsu.noteme.data.model.AllNotesModel
import com.sdsu.noteme.data.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditNoteViewModel (application: Application): AndroidViewModel(application) {
    private val notesRepository: NotesRepository
    val data: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    init {
        val notesDao = NotesAppDatabase.getDatabase(application, viewModelScope).notesDao()
        notesRepository = NotesRepository(notesDao)
    }
    fun updateNoteVm(notesModel: AllNotesModel)=viewModelScope.launch (Dispatchers.IO){
        notesRepository.updateNoteRepo(notesModel)
    }

    fun setValue(thisRef: Boolean) {
        data.value = thisRef
    }

    fun getValue(): MutableLiveData<Boolean> {
        return data
    }
}

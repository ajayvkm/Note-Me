package com.sdsu.noteme.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sdsu.noteme.data.db.NotesAppDatabase
import com.sdsu.noteme.data.model.AllNotesModel
import com.sdsu.noteme.data.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddNoteViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel
    private val notesRepository: NotesRepository
    val data: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val datalist: MutableLiveData<Boolean> by lazy {
            MutableLiveData<Boolean>()
    }

    init {
        val notesDao = NotesAppDatabase.getDatabase(application, viewModelScope).notesDao()
        notesRepository = NotesRepository(notesDao)
    }

    fun addNoteVm(notesModel: AllNotesModel) = viewModelScope.launch(Dispatchers.IO) {
        notesRepository.addNoteRepo(notesModel)
    }

    fun setValue(thisRef: Boolean) {
        data.value = thisRef
    }

    fun getValue(): MutableLiveData<Boolean>{
        return data
    }

    fun setValueList(bool: Boolean) {
        datalist.value= bool
    }
    fun getValueList(): MutableLiveData<Boolean>{
        return datalist
    }

}

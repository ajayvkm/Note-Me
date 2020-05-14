package com.sdsu.noteme.data.repository

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sdsu.noteme.Base.NoteAppRoomStartup
import com.sdsu.noteme.data.db.NotesDao
import com.sdsu.noteme.data.model.AllNotesModel
import com.sdsu.noteme.viewmodel.AddNoteViewModel

class NotesRepository(private val notesDao: NotesDao) {

    fun getAllNotes(noteType: String): LiveData<List<AllNotesModel>> {
        return notesDao.getAllNotes(noteType.toString())
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addNoteRepo(notesModel: AllNotesModel) {
        notesDao.addNotes(notesModel)
    }

    fun getAllTypeNotes(): LiveData<List<AllNotesModel>> {
        return notesDao.getAllTypeNotes()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateNoteRepo(notesModel: AllNotesModel) {
        notesDao.updateNotes(notesModel)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteNoteRepo(notesModel: AllNotesModel) {
        notesDao.deleteNotes(notesModel)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addSubRepo(notesModel: AllNotesModel){
        notesDao.addSubscription(notesModel)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateSubRepo(notesModel: AllNotesModel) {
        notesDao.updateSub(notesModel)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateListRepo(notesModel: AllNotesModel) {
        notesDao.updateList(notesModel)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun undoNoteRepo(notesModel: AllNotesModel) {
        notesDao.undoNotes(notesModel)
    }


}
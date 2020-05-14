package com.sdsu.noteme.data.db.async

import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import com.sdsu.noteme.data.model.AllNotesModel
import com.sdsu.noteme.viewmodel.AllListViewModel

class DeleteListTask(
    var context: Context?,
    var viewModel: AllListViewModel,
    var notesModel: AllNotesModel
) : AsyncTask<Void, Void, Boolean>() {
    override fun doInBackground(vararg params: Void?): Boolean {
        viewModel.deleteNote(notesModel)
        return true
    }

    override fun onPostExecute(bool: Boolean?) {
        if (bool!!) {
            //Toast.makeText(context, "Updated to Database", Toast.LENGTH_LONG).show()
            viewModel.setValue(notesModel)
        }

    }

}
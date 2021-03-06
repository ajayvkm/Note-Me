package com.sdsu.noteme.data.db.async

import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import com.sdsu.noteme.data.model.AllNotesModel
import com.sdsu.noteme.viewmodel.EditNoteViewModel

class UpdateTask(var context: Context?, var viewModel: EditNoteViewModel, var allNotesModel: AllNotesModel) : AsyncTask<Void, Void, Boolean>() {
    override fun doInBackground(vararg params: Void?): Boolean {
        viewModel.updateNoteVm(allNotesModel)
        return true
    }

    override fun onPostExecute(bool: Boolean?) {
        if (bool!!) {
            //Toast.makeText(context, "Updated to Database", Toast.LENGTH_LONG).show()
            viewModel.setValue(bool)
        }
    }
}
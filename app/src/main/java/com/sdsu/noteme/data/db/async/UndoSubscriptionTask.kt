package com.sdsu.noteme.data.db.async

import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import com.sdsu.noteme.data.model.AllNotesModel
import com.sdsu.noteme.viewmodel.AllSubscriptionViewModel

class UndoSubscriptionTask(
    var context: Context?,
    var viewModel: AllSubscriptionViewModel,
    var allNotesModel: AllNotesModel
) : AsyncTask<Void, Void, Boolean>() {
    override fun doInBackground(vararg params: Void?): Boolean {
        viewModel.undoNoteVm(allNotesModel)
        return true
    }

    override fun onPostExecute(bool: Boolean?) {
        if (bool!!) {
            Toast.makeText(context, "Undo task done", Toast.LENGTH_LONG).show()
        }
    }
}
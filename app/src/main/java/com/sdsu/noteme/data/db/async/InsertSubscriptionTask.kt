package com.sdsu.noteme.data.db.async

import android.content.Context
import android.os.AsyncTask
import com.sdsu.noteme.data.model.AllNotesModel
import com.sdsu.noteme.viewmodel.AddSubscriptionViewModel

class InsertSubscriptionTask(
    var context: Context?,
    var viewModel: AddSubscriptionViewModel,
    var allNotesModel: AllNotesModel
) : AsyncTask<Void, Void, Boolean>() {
    override fun doInBackground(vararg params: Void?): Boolean {
        viewModel.addSubscriptionVm(allNotesModel)
        return true
    }

    override fun onPostExecute(bool: Boolean?) {
        if (bool!!) {
            // Toast.makeText(context, "Added to Database", Toast.LENGTH_LONG).show()
            viewModel.setValue(bool)
        }
    }
}
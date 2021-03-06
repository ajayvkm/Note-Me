package com.sdsu.noteme.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.sdsu.noteme.R
import com.sdsu.noteme.data.db.async.DeleteTask
import com.sdsu.noteme.data.db.async.UndoTask
import com.sdsu.noteme.data.model.AllNotesModel
import com.sdsu.noteme.viewmodel.AllNotesViewModel
import com.google.android.material.snackbar.Snackbar


class AllNotesAdapter(var allNotes: List<AllNotesModel>, private val listener: (AllNotesModel) -> Unit) :
    RecyclerView.Adapter<AllNotesAdapter.AllNotesHolder>() {

    private var removedPosition: Int = 0
    private lateinit var removedNote: AllNotesModel
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllNotesHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_all_type_notes, parent, false)
        context = parent.context
        return AllNotesHolder(view)
    }

    override fun getItemCount(): Int {
        Log.e("ADAPTER", allNotes.size.toString())
        return allNotes.size
    }

    override fun onBindViewHolder(holder: AllNotesHolder, position: Int) {
        val note = allNotes[position]
        Log.e("ADAPTER", "Adapter called")
        holder.bind(note, listener)
    }

    fun removeItem(
        position: Int,
        viewHolder: RecyclerView.ViewHolder,
        viewModel: AllNotesViewModel
    ) {
        removedNote = allNotes[position]
        removedPosition = position
        DeleteTask(context, viewModel, removedNote).execute()
    }

    inner class AllNotesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var allTypeNotes: AllNotesModel
        private val noteTitle: TextView = itemView.findViewById<TextView>(R.id.rvNoteTitle)
        private val noteDescription:TextView =
            itemView.findViewById(R.id.rvNoteDescription)
        private val createdOn: TextView = itemView.findViewById<TextView>(R.id.rvCreatedOn)
        private val card: CardView = itemView.findViewById(R.id.card_id)

        fun bind(
            notesModel: AllNotesModel,
            listener: (AllNotesModel) -> Unit
        ) {
            this.allTypeNotes = notesModel
            this.noteTitle.text = this.allTypeNotes.noteTitle
            this.noteDescription.text = this.allTypeNotes.noteDescription
            this.createdOn.text = this.allTypeNotes.createdOn
            this.card.setCardBackgroundColor(context.resources.getColor(R.color.colornote))
            this.card.radius = 15f

            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "Note Single clicked!", Toast.LENGTH_SHORT)
                    .show()
                //callbackInterface.passDataCallback(allTypeNotes)
                listener(allTypeNotes)
            }

            itemView.setOnLongClickListener {
                Toast.makeText(itemView?.context, "Note Long Press clicked!", Toast.LENGTH_SHORT)
                    .show()
                true
            }
        }

    }
}
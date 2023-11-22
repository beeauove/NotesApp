package com.example.notesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
//import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.entities.Notes
import kotlinx.android.synthetic.main.fragment_create_note.view.tvDateTime
import kotlinx.android.synthetic.main.item_rv_notes.view.tvDesc
import kotlinx.android.synthetic.main.item_rv_notes.view.tvTitle

class NotesAdapter :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    private var listener: OnItemClickListener? = null
    private var arrList = ArrayList<Notes>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv_notes, parent, false)
        )
    }
    override fun getItemCount(): Int {
        return arrList.size
    }
    fun setData(arrNotesList: List<Notes>) {
        arrList = arrNotesList as ArrayList<Notes>
    }
    fun setOnClickListener(listener1: OnItemClickListener){
    listener = listener1
}
    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {

        holder.itemView.tvTitle.text = arrList[position].title
        holder.itemView.tvDesc.text = arrList[position].noteText
        holder.itemView.tvDateTime.text = arrList[position].dateTime
    }

    class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    interface OnItemClickListener{
        fun onClicked(noteId:Int)
    }

}
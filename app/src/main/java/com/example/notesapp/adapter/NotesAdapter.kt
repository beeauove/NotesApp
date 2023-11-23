package com.example.notesapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.entities.Notes

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    private var listener: OnItemClickListener? = null
    private var arrList = ArrayList<Notes>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_notes, parent, false)
        return NotesViewHolder(view)
    }

    override fun getItemId(position: Int): Long {
        return arrList[position].id?.toLong()!!
    }

    override fun getItemCount(): Int {
        return arrList.size
    }

    fun setData(arrNotesList: List<Notes>) {
        arrList = arrNotesList as ArrayList<Notes>
    }

    fun setOnClickListener(listener1: OnItemClickListener) {
        Log.d("notes adapter:", "click listener")
        listener = listener1
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val item = arrList[position]
        holder.setTitle(item.title.toString())
        holder.setDesc(item.noteText.toString())
        holder.setDateTime(item.dateTime.toString())
        holder.setClickListener(listener!!)
    }

    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private var tvDesc: TextView = itemView.findViewById(R.id.tvDesc)
        private var tvDateTime: TextView = itemView.findViewById(R.id.tvDateTime)
        private var item: CardView = itemView.findViewById(R.id.cardView)

        fun setTitle(title: String) {
            tvTitle.text = title
        }

        fun setDesc(desc: String) {
            tvDesc.text = desc
        }

        fun setDateTime(dateTime: String) {
            tvDateTime.text = dateTime
        }

        fun setClickListener(listener: OnItemClickListener) {
            item.setOnClickListener {
                listener.onClicked(bindingAdapterPosition)
            }

        }
    }

    interface OnItemClickListener {
        fun onClicked(noteId: Int)
    }

}
package com.example.notesapp

//import android.content.Context
import android.annotation.SuppressLint
import android.media.Image
import android.os.Bundle
//import android.provider.ContactsContract.CommonDataKinds.Note
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
//import androidx.fragment.app.Fragment
import com.example.notesapp.database.notesDatabase
import com.example.notesapp.entities.Notes

import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

//import java.util.Date

class CreateNoteFragment : BaseFragment() {
    //var selectedColor = "#171C26"
    var currentDate: String? = null
    private var READ_STORAGE_PERM = 123
    private var REQUEST_CODE_IMAGE = 456
    private var noteId = -1

    private var etNoteTitle: EditText? = null
    var etNoteSubTitle: EditText? = null
    var etNoteDesc: EditText? = null
    var tvDateTime: TextView? = null
    var imgDone: ImageView? = null
    var imgBack: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteId = requireArguments().getInt("noteId", -1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_note, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CreateNoteFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etNoteTitle = view.findViewById(R.id.etNoteTitle)
        etNoteSubTitle = view.findViewById(R.id.etNoteSubTitle)
        etNoteDesc = view.findViewById(R.id.etNoteDesc)
        tvDateTime = view.findViewById(R.id.tvDateTime)
        imgDone = view.findViewById(R.id.imgDone)
        imgBack = view.findViewById(R.id.imgBack)

        if (noteId != -1) {
            launch {
                context?.let {
                    val notes = notesDatabase.getDatabase(it).notedao().getSpecificNote(noteId)
                    etNoteTitle.setText(notes.title)
                    etNoteSubTitle.setText(notes.subtitle)
                    etNoteDesc.setText(notes.noteText)
                }
            }
            val sdf = SimpleDateFormat("dd/M/yyy hh:mm:ss")
            tvDateTime.text = currentDate
            imgDone.setOnClickListener {
                if (noteId != -1) {
                    updateNote()
                } else
                    saveNote()
            }
        }
        imgBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun updateNote() {
        launch {
            context?.let {
                val notes = notesDatabase.getDatabase(it).notedao().getSpecificNote(noteId)
                notes.title = etNoteTitle.text.toString()
                notes.subtitle = etNoteSubTitle.text.toString()
                notes.noteText = etNoteDesc.text.toString()
                notes.dateTime = currentDate.toString()
                notesDatabase.getDatabase(it).notedao().updateNote(notes)
                etNoteTitle.setText("")
                etNoteSubTitle.setText("")
                etNoteDesc.setText("")
                requireActivity().supportFragmentManager.popBackStack()

            }
        }
    }

    private fun saveNote() {
        if (etNoteTitle.text.isNullOrEmpty()) {
            Toast.makeText(context, "Note Title is Required", Toast.LENGTH_SHORT).show()
        } else if (etNoteSubTitle.text.isNullOrEmpty()) {
            Toast.makeText(context, "Note Sub Title is Required", Toast.LENGTH_SHORT).show()
        } else if (etNoteDesc.text.isNullOrEmpty()) {
            Toast.makeText(context, "Note Description is Required", Toast.LENGTH_SHORT).show()
        } else {
            launch {
                var notes = Notes()
                notes.title = etNoteTitle.text.toString()
                notes.subtitle = etNoteSubTitle.text.toString()
                notes.noteText = etNoteDesc.text.toString()
                notes.dateTime = currentDate.toString()
                context?.let {
                    notesDatabase.getDatabase(it).notedao().insertNotes(notes)
                    etNoteTitle.setText("")
                    etNoteSubTitle.setText("")
                    etNoteDesc.setText("")
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }
    }

    private fun deleteNote() {
        launch {
            context?.let {
                notesDatabase.getDatabase(it).notedao().deleteSpecificNote(noteId)
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

}
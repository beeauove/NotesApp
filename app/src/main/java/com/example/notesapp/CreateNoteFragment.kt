package com.example.notesapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.notesapp.database.NotesDatabase
import com.example.notesapp.entities.Notes
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CreateNoteFragment : BaseFragment() {
    var selectedColor = "#171C26"
    private var currentDate: String? = null
//    private var READ_STORAGE_PERM = 123
//    private var REQUEST_CODE_IMAGE = 456
    private var noteId = -1

    private var etNoteTitle: EditText? = null
    private var etNoteSubTitle: EditText? = null
    private var etNoteDesc: EditText? = null
    private var tvDateTime: TextView? = null
    private var imgDone: ImageView? = null
    private var imgBack: ImageView? = null
    private var colorView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteId = requireArguments().getInt("noteId", -1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_note, container, false)

        etNoteTitle = view.findViewById(R.id.etNoteTitle)
        etNoteSubTitle = view.findViewById(R.id.etNoteSubTitle)
        etNoteDesc = view.findViewById(R.id.etNoteDesc)
        tvDateTime = view.findViewById(R.id.tvDateTime)
        imgDone = view.findViewById(R.id.imgDone)
        imgBack = view.findViewById(R.id.imgBack)
        colorView = view.findViewById(R.id.colorView)

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CreateNoteFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sdf = SimpleDateFormat("dd/M/yyy hh:mm:ss", Locale.getDefault())

        if (noteId != -1) {
            launch {
                context?.let {
                    val db = NotesDatabase.getDatabase(it.applicationContext)
                    val notes = db.noteDao().getSpecificNote(noteId)
                    etNoteTitle?.setText(notes.title)
                    etNoteSubTitle?.setText(notes.subtitle)
                    etNoteDesc?.setText(notes.noteText)
                }
            }
        }

        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            BroadcastReceiver, IntentFilter("bottom_sheet_action")
        )

        currentDate = sdf.format(Date())
        tvDateTime?.text = currentDate

        imgDone?.setOnClickListener {
            if (noteId != -1) updateNote()
            else saveNote()
        }

        imgBack?.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun updateNote() {
        launch {
            context?.let {
                val notes = NotesDatabase.getDatabase(it).noteDao().getSpecificNote(noteId)
                notes.title = etNoteTitle?.text.toString()
                notes.subtitle = etNoteSubTitle?.text.toString()
                notes.noteText = etNoteDesc?.text.toString()
                notes.dateTime = currentDate.toString()

                NotesDatabase.getDatabase(it).noteDao().updateNote(notes)
                etNoteTitle?.setText("")
                etNoteSubTitle?.setText("")
                etNoteDesc?.setText("")
                requireActivity().supportFragmentManager.popBackStack()

            }
        }
    }

    private fun saveNote() {
        if (etNoteTitle?.text.isNullOrEmpty()) {
            Toast.makeText(context, "Note Title is Required", Toast.LENGTH_SHORT).show()
        } else if (etNoteSubTitle?.text.isNullOrEmpty()) {
            Toast.makeText(context, "Note Sub Title is Required", Toast.LENGTH_SHORT).show()
        } else if (etNoteDesc?.text.isNullOrEmpty()) {
            Toast.makeText(context, "Note Description is Required", Toast.LENGTH_SHORT).show()
        } else {
            launch {
                val notes = Notes()
                notes.title = etNoteTitle?.text.toString()
                notes.subtitle = etNoteSubTitle?.text.toString()
                notes.noteText = etNoteDesc?.text.toString()
                notes.dateTime = currentDate.toString()
                context?.let {
                    NotesDatabase.getDatabase(it).noteDao().insertNotes(notes)
                    etNoteTitle?.setText("")
                    etNoteSubTitle?.setText("")
                    etNoteDesc?.setText("")
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }
    }

    private fun deleteNote() {
        launch {
            context?.let {
                NotesDatabase.getDatabase(it).noteDao().deleteSpecificNote(noteId)
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    private val BroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val actionColor = intent?.getStringExtra("action")

            when (actionColor!!) {
                "Blue" -> {
                    selectedColor = intent.getStringExtra("selectedColor")!!
                    colorView?.setBackgroundColor(Color.parseColor(selectedColor))
                }

                "Yellow" -> {
                    selectedColor = intent.getStringExtra("selectedColor")!!
                    colorView?.setBackgroundColor(Color.parseColor(selectedColor))
                }


                "Purple" -> {
                    selectedColor = intent.getStringExtra("selectedColor")!!
                    colorView?.setBackgroundColor(Color.parseColor(selectedColor))
                }


                "Green" -> {
                    selectedColor = intent.getStringExtra("selectedColor")!!
                    colorView?.setBackgroundColor(Color.parseColor(selectedColor))
                }


                "Orange" -> {
                    selectedColor = intent.getStringExtra("selectedColor")!!
                    colorView?.setBackgroundColor(Color.parseColor(selectedColor))
                }


                "Black" -> {
                    selectedColor = intent.getStringExtra("selectedColor")!!
                    colorView?.setBackgroundColor(Color.parseColor(selectedColor))
                }
            }
        }
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(BroadcastReceiver)
        super.onDestroy()
    }

}
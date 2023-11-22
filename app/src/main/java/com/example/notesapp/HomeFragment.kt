package com.example.notesapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapp.adapter.NotesAdapter
import com.example.notesapp.database.notesDatabase
import com.example.notesapp.entities.Notes
import kotlinx.android.synthetic.main.fragment_create_note.*
import kotlinx.android.synthetic.main.fragment_home.fabCreateNote
import kotlinx.android.synthetic.main.fragment_home.recycler_view
import kotlinx.android.synthetic.main.fragment_home.search_view
import kotlinx.coroutines.launch
import java.util.*

class HomeFragment : BaseFragment() {
    var arrNotes = ArrayList<Notes>()
    var notesAdapter = NotesAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    companion object {
       @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        launch {
            context?.let {
                val notes = notesDatabase.getDatabase(it).notedao().getAllNotes()
                notesAdapter!!.setData(notes)
                arrNotes = notes as ArrayList<Notes>
                recycler_view.adapter = notesAdapter
            }
        }
        notesAdapter!!.setOnClickListener(onClicked)
        fabCreateNote.setOnClickListener {
            replaceFragment(CreateNoteFragment.newInstance(),false)
        }
        search_view.run {
            search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                @SuppressLint("NotifyDataSetChanged")
                override fun onQueryTextChange(query: String?): Boolean {
                   val tempArr = ArrayList<Notes>()
                   for (arr in arrNotes) {
                        if (arr.title!!.lowercase(Locale.getDefault()).contains(query.toString())) {
                            tempArr.add(arr)
                        }
                    }
                    notesAdapter.setData(tempArr)
                    notesAdapter.notifyDataSetChanged()
                    return true
                }

            })
        }
    }
   private val onClicked = object :NotesAdapter.OnItemClickListener{
       override fun onClicked(noteId: Int) {
           val fragment: Fragment
           val bundle = Bundle()
           bundle.putInt("noteId", noteId)
           fragment = CreateNoteFragment.newInstance()
           bundle.also { it.also { fragment.arguments = it } }

           replaceFragment(fragment, false)

       }
   }
   fun replaceFragment(fragment: Fragment, istransition:Boolean){
        val fragmentTransition = requireActivity().supportFragmentManager.beginTransaction()
        if (istransition){
            fragmentTransition.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
        }
        fragmentTransition.replace(R.id.frame_layout,fragment).addToBackStack(fragment.javaClass.simpleName).commit()
    }
}
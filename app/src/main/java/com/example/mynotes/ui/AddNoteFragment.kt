package com.example.mynotes.ui

import android.os.AsyncTask
import android.os.AsyncTask.execute
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mynotes.R
import com.example.mynotes.databinding.FragmentAddNoteBinding
import com.example.mynotes.databinding.FragmentHomeBinding
import com.example.mynotes.db.Note
import com.example.mynotes.db.NoteDatabase

class AddNoteFragment : Fragment() {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding: FragmentAddNoteBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(binding) {
            buttonSave.setOnClickListener {
                val noteTitle = editTextTitle.text.toString().trim()
                val noteBody = editTextNote.text.toString().trim()

                if(noteTitle.isEmpty()){
                    editTextTitle.error = "title required"
                    editTextTitle.requestFocus()
                    return@setOnClickListener
                }

                if(noteBody.isEmpty()){
                    editTextNote.error = "note required"
                    editTextNote.requestFocus()
                    return@setOnClickListener
                }

                val note = Note(noteTitle, noteBody)
            }
        }
    }

    private fun saveNote(note: Note){
        class SaveNote : AsyncTask<Void, Void, Void>(){
            override fun doInBackground(vararg p0: Void?): Void? {
                NoteDatabase(requireActivity()!!).getNoteDao().addNote(note)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)

                Toast.makeText(activity, "Note Saved", Toast.LENGTH_LONG).show()
            }
        }
        SaveNote().execute()
    }
}
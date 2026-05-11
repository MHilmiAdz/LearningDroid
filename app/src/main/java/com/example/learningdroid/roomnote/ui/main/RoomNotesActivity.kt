package com.example.learningdroid.roomnote.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learningdroid.databinding.ActivityRoomNotesBinding
import com.example.learningdroid.roomnote.helper.ViewModelFactory
import com.example.learningdroid.roomnote.ui.insert.RoomNotesUpdateActivity

class RoomNotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRoomNotesBinding
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRoomNotesBinding.inflate(layoutInflater)
        adapter = NoteAdapter()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.rootLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val mainViewModel = obtainViewModel(this@RoomNotesActivity)
        mainViewModel.getAllNotes().observe(this) { noteList ->
            if (noteList != null) {
                adapter.setListNotes(noteList)
            }
        }

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this@RoomNotesActivity, RoomNotesUpdateActivity::class.java)
            startActivity(intent)
        }

        binding.rvNotes.layoutManager = LinearLayoutManager(this)
        binding.rvNotes.setHasFixedSize(true)
        binding.rvNotes.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[MainViewModel::class.java]
    }
}
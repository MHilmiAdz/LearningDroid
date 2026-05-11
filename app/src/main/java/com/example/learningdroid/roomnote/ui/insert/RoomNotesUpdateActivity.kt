package com.example.learningdroid.roomnote.ui.insert

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.learningdroid.R
import com.example.learningdroid.databinding.ActivityRoomNotesUpdateBinding
import com.example.learningdroid.roomnote.database.Note
import com.example.learningdroid.roomnote.helper.DateHelper
import com.example.learningdroid.roomnote.helper.ViewModelFactory

class RoomNotesUpdateActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NOTE = "extra_note"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }
    private var isEdit = false
    private var note: Note? = null

    private lateinit var noteAddUpdateViewModel: NoteAddUpdateViewModel
    private lateinit var binding: ActivityRoomNotesUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRoomNotesUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        noteAddUpdateViewModel = obtainViewModel(this@RoomNotesUpdateActivity)

        note = intent.getParcelableExtra(EXTRA_NOTE)
        if (note != null) {
            isEdit = true
        } else {
            note = Note()
        }
        val actionBarTitle: String
        val btnTitle: String
        if (isEdit) {
            actionBarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)
            if (note != null) {
                note?.let { note ->
                    binding.edtTitle.setText(note.title)
                    binding.edtDescription.setText(note.description)
                }
            }
        } else {
            actionBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }
        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnSubmit.text = btnTitle

        binding.btnSubmit.setOnClickListener {
            val title = binding.edtTitle.text.toString().trim()
            val description = binding.edtDescription.text.toString().trim()
            when {
                title.isEmpty() -> {
                    binding.edtTitle.error = getString(R.string.empty)
                }

                description.isEmpty() -> {
                    binding.edtDescription.error = getString(R.string.empty)
                }

                else -> {
                    note.let { note ->
                        note?.title = title
                        note?.description = description
                    }
                    if (isEdit) {
                        noteAddUpdateViewModel.update(note as Note)
                        showToast(getString(R.string.changed))
                    } else {
                        note.let { note ->
                            note?.date = DateHelper.getCurrentDate()
                        }
                        noteAddUpdateViewModel.insert(note as Note)
                        showToast(getString(R.string.added))
                    }
                    finish()
                }
            }
        }

        binding.btnDelete.setOnClickListener {
            showAlertDialog(ALERT_DIALOG_DELETE)
        }
        binding.btnCancel.setOnClickListener {
            showAlertDialog(ALERT_DIALOG_CLOSE)
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
    private fun obtainViewModel(activity: AppCompatActivity): NoteAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[NoteAddUpdateViewModel::class.java]
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogMessage = getString(R.string.message_delete)
            dialogTitle = getString(R.string.delete)
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (!isDialogClose) {
                    noteAddUpdateViewModel.delete(note as Note)
                    showToast(getString(R.string.deleted))
                }
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


}
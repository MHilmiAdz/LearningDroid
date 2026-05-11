package com.example.learningdroid.frost

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learningdroid.R
import com.example.learningdroid.databinding.ActivityChatSessionBinding
import com.example.learningdroid.frost.database.ChatSession
import com.example.learningdroid.frost.database.FrostDatabase
import com.example.learningdroid.frost.helper.DateHelper
import com.example.learningdroid.frost.ui.chatsession.ChatSessionAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

class ChatSessionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatSessionBinding
    private lateinit var adapter: ChatSessionAdapter
    private lateinit var database: FrostDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatSessionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        database = FrostDatabase.getDatabase(this)

        setupRecyclerView()
        checkAndPromptForAiLink()

        binding.fabNewChat.setOnClickListener {
            createNewSession()
        }

        database.chatSessionDao().getAllSessions().observe(this) { sessionList ->
            adapter.submitList(sessionList)
        }

        binding.btnSettings.setOnClickListener {
            showUrlInputDialog(isEditing = true)
        }
    }

    private fun setupRecyclerView() {
        adapter = ChatSessionAdapter(
            onSessionClick = { clickedSession ->
                val intent = Intent(this, ChatActivity::class.java)
                intent.putExtra("SESSION_ID", clickedSession.id)
                startActivity(intent)
            },
            onDeleteClick = { sessionToDelete ->
                showDeleteConfirmation(sessionToDelete) // NEW
            }
        )
        binding.rvSessions.layoutManager = LinearLayoutManager(this)
        binding.rvSessions.adapter = adapter
    }

    private fun createNewSession() {
        lifecycleScope.launch(Dispatchers.IO) {
            val newSession = ChatSession(
                title = "New Conversation",
                date = DateHelper.getCurrentDate()
            )
            val newId = database.chatSessionDao().insert(newSession)

            withContext(Dispatchers.Main) {
                val intent = Intent(this@ChatSessionActivity, ChatActivity::class.java)
                intent.putExtra("SESSION_ID", newId.toInt())
                startActivity(intent)
            }
        }
    }


    private fun checkAndPromptForAiLink() {
        val prefs = getSharedPreferences("FrostPrefs", MODE_PRIVATE)
        val savedUrl = prefs.getString("AI_URL", null)

        if (savedUrl == null) {
            showUrlInputDialog()
        }
    }

    private fun validateAndSaveUrl(url: String) {
        Toast.makeText(this, "Testing connection...", Toast.LENGTH_SHORT).show()

        lifecycleScope.launch {
            val isValid = testConnection(url)
            if (isValid) {
                val prefs = getSharedPreferences("FrostPrefs", MODE_PRIVATE)
                prefs.edit { putString("AI_URL", url) }
                Toast.makeText(this@ChatSessionActivity, "Connected successfully!", Toast.LENGTH_SHORT).show()
            } else {
                // --- NEW CUSTOM ERROR DIALOG ---
                val dialogView = LayoutInflater.from(this@ChatSessionActivity).inflate(R.layout.dialog_connection_failed, null)

                val dialog = MaterialAlertDialogBuilder(this@ChatSessionActivity)
                    .setView(dialogView)
                    .setCancelable(false)
                    .create()

                val btnTryAgain = dialogView.findViewById<Button>(R.id.btnDialogTryAgain)

                btnTryAgain.setOnClickListener {
                    dialog.dismiss()
                    showUrlInputDialog()
                }

                dialog.show()
            }
        }
    }

    private suspend fun testConnection(url: String): Boolean = withContext(Dispatchers.IO) {
        try {
            // Increased timeout to 10 seconds to give the emulator plenty of time
            val pingClient = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()

            val cleanUrl = if (url.endsWith("/api/chat")) url.replace("/api/chat", "") else url
            val request = Request.Builder().url(cleanUrl).build()

            val response = pingClient.newCall(request).execute()
            response.isSuccessful

        } catch (e: Exception) {
            // THIS IS THE MAGIC LINE!
            // If it fails, open your Logcat in Android Studio and search for "OllamaTest"
            Log.e("OllamaTest", "Connection failed with error: ${e.message}", e)
            false
        }
    }

    // We add a default parameter so it acts like setup mode if we don't pass true
    private fun showUrlInputDialog(isEditing: Boolean = false) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_setup_ai, null)

        val dialog = MaterialAlertDialogBuilder(this)
            .setView(dialogView)
            .setCancelable(isEditing) // Can only tap outside to cancel if editing
            .create()

        val etUrl = dialogView.findViewById<android.widget.EditText>(R.id.etDialogUrl)
        val btnConnect = dialogView.findViewById<Button>(R.id.btnDialogConnect)
        val btnCancel = dialogView.findViewById<Button>(R.id.btnDialogCancel)

        // If editing, hide the cancel button if it's the very first setup
        btnCancel.visibility = if (isEditing) View.VISIBLE else View.GONE

        // Grab the current URL from the vault so they don't have to retype it all
        val prefs = getSharedPreferences("FrostPrefs", MODE_PRIVATE)
        val currentUrl = prefs.getString("AI_URL", "")
        if (isEditing) {
            etUrl.setText(currentUrl)
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnConnect.setOnClickListener {
            val url = etUrl.text.toString().trim()
            if (url.isNotEmpty()) {
                dialog.dismiss()
                validateAndSaveUrl(url)
            } else {
                Toast.makeText(this, "URL cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun showDeleteConfirmation(session: ChatSession) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_delete_session, null)

        val dialog = MaterialAlertDialogBuilder(this)
            .setView(dialogView)
            .create()

        val btnCancel = dialogView.findViewById<Button>(R.id.btnDialogCancel)
        val btnDelete = dialogView.findViewById<Button>(R.id.btnDialogDelete)

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnDelete.setOnClickListener {
            dialog.dismiss()
            deleteSessionFromDatabase(session)
        }

        dialog.show()
    }

    private fun deleteSessionFromDatabase(session: ChatSession) {
        lifecycleScope.launch(Dispatchers.IO) {
            database.chatDao().deleteChatsBySessionId(session.id)
            database.chatSessionDao().delete(session)
        }
    }

}
package com.example.learningdroid.frost

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learningdroid.databinding.ActivityChatBinding
import com.example.learningdroid.frost.database.Chat
import com.example.learningdroid.frost.database.FrostDatabase
import com.example.learningdroid.frost.helper.ChatViewModelFactory
import com.example.learningdroid.frost.ui.chat.ChatAdapter
import com.example.learningdroid.frost.ui.chat.ChatViewModel

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var adapter: ChatAdapter
    private val chatsList = mutableListOf<Chat>()

    private val viewModel: ChatViewModel by viewModels {
        val database = FrostDatabase.getDatabase(this)
        val sessionId = intent.getIntExtra("SESSION_ID", -1)

        val prefs = getSharedPreferences("FrostPrefs", MODE_PRIVATE)
        var safeUrl = prefs.getString("AI_URL", "http://10.0.2.2:11434") ?: "http://10.0.2.2:11434"

        // 1. Remove trailing slash if the user accidentally typed one
        if (safeUrl.endsWith("/")) safeUrl = safeUrl.dropLast(1)

        // 2. Ensure it strictly targets the chat endpoint
        if (!safeUrl.endsWith("/api/chat")) safeUrl += "/api/chat"

        ChatViewModelFactory(database.chatDao(), sessionId, safeUrl)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (intent.getIntExtra("SESSION_ID", -1) == -1) {
            finish() // Safety check
            return
        }

        setupRecyclerView()
        observeViewModel()

        binding.btnSend.setOnClickListener {
            val text = binding.etMessage.text.toString().trim()
            if (text.isNotEmpty()) {
                viewModel.sendMessage(text)
                binding.etMessage.text.clear()
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = ChatAdapter(chatsList)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeViewModel() {
        // Observe the chat messages from the Room database
        viewModel.chats.observe(this) { dbChats ->
            chatsList.clear()
            chatsList.addAll(dbChats)
            adapter.notifyDataSetChanged()

            if (chatsList.isNotEmpty()) {
                binding.recyclerView.scrollToPosition(chatsList.size - 1)
            }

            // Rebuild context so the AI remembers history
            viewModel.rebuildHistory(dbChats)
        }

        // Observe the typing indicator
        viewModel.isTyping.observe(this) { isTyping ->
            if (isTyping) {
                binding.typingIndicator.visibility = View.VISIBLE
                binding.btnSend.isEnabled = false
            } else {
                binding.typingIndicator.visibility = View.GONE
                binding.btnSend.isEnabled = true
            }
        }
    }
}
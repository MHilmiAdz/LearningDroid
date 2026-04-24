package com.example.learningdroid.frost

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope // ADDED: Import lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager // ADDED: Import LayoutManager
import com.example.learningdroid.BuildConfig.OLLAMA_URL
import com.example.learningdroid.databinding.ActivityFrostBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit // ADDED: For timeouts

class FrostActivity : AppCompatActivity() {

    lateinit var binding: ActivityFrostBinding
    private val messages = mutableListOf<Message>()
    private val chatHistory = JSONArray()
    private lateinit var adapter: ChatAdapter
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(180, TimeUnit.SECONDS)
        .writeTimeout(180, TimeUnit.SECONDS)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFrostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        showWelcomePopup()

        adapter = ChatAdapter(messages)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.btnSend.setOnClickListener {
            val message = binding.etMessage.text.toString()
            sendMessage(message.trim())
        }
    }

    private fun sendMessage(text: String) {
        if (text.isEmpty()) return

        binding.etMessage.text.clear()

        adapter.addMessage(Message(text, isUser = true))
        binding.recyclerView.scrollToPosition(adapter.itemCount - 1) // CHANGED: recyclerView -> rvChat

        chatHistory.put(JSONObject().apply {
            put("role",    "user")
            put("content", text)
        })

        binding.typingIndicator.visibility = View.VISIBLE
        binding.btnSend.isEnabled = false

        lifecycleScope.launch {
            val reply = callOllama()

            binding.typingIndicator.visibility = View.GONE
            binding.btnSend.isEnabled = true

            if (!reply.isNullOrBlank()) {
                chatHistory.put(JSONObject().apply {
                    put("role",    "assistant")
                    put("content", reply)
                })
                adapter.addMessage(Message(reply, isUser = false))
            } else {
                adapter.addMessage(Message("Error: F.R.O.S.T returned an empty response.", isUser = false))
            }
            binding.recyclerView.scrollToPosition(adapter.itemCount - 1)
        }
    }

    private suspend fun callOllama(): String? = withContext(Dispatchers.IO) {
        try {
            val optionsObject = JSONObject().apply {
                put("num_predict", 100)
                put("num_ctx", 1024)
                put("temperature", 0.0)
                put("top_k", 1)
            }

            val bodyObject = JSONObject().apply {
                put("model", "olmo2:latest") // Or whichever model you are testing
                put("stream", false)
                put("keep_alive", "1h")      // Keeps it in RAM so the second message is instant
                put("options", optionsObject)
                put("think", false)
                put("messages", chatHistory)
            }

            val jsonString = bodyObject.toString()
            Log.d("OllamaRequest", "Sending: $jsonString")

            val request = Request.Builder()
                .url(OLLAMA_URL)
                .post(jsonString.toRequestBody("application/json".toMediaType()))
                .build()

            val response = client.newCall(request).execute()

            if (!response.isSuccessful) return@withContext null

            val responseBodyString = response.body.string()
            val jsonResponse = JSONObject(responseBodyString)
            jsonResponse.getJSONObject("message").getString("content")

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun showWelcomePopup() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Welcome to F.R.O.S.T \u2744\uFE0F")
            .setMessage("F.R.O.S.T is running locally on MY device. So, it might be not work for you.")
            .setPositiveButton("Got it!") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }
}
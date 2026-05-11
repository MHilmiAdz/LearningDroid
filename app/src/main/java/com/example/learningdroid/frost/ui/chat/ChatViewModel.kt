package com.example.learningdroid.frost.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningdroid.frost.database.Chat
import com.example.learningdroid.frost.database.ChatDao
import com.example.learningdroid.frost.helper.DateHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class ChatViewModel(
    private val chatDao: ChatDao,
    private val sessionId: Int,
    private val aiUrl: String
) : ViewModel() {

    // Automatically grabs the LiveData from the Room database
    val chats: LiveData<List<Chat>> = chatDao.getChatsForSession(sessionId)

    private var chatHistory = JSONArray()

    // LiveData to control the "typing..." indicator in the UI
    private val _isTyping = MutableLiveData<Boolean>(false)
    val isTyping: LiveData<Boolean> = _isTyping

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(180, TimeUnit.SECONDS)
        .writeTimeout(180, TimeUnit.SECONDS)
        .build()

    fun rebuildHistory(dbChats: List<Chat>) {
        chatHistory = JSONArray()
        for (chat in dbChats) {
            chatHistory.put(JSONObject().apply {
                put("role", if (chat.isUser) "user" else "assistant")
                put("content", chat.text)
            })
        }
    }

    fun sendMessage(text: String) {
        if (text.isBlank()) return

        _isTyping.value = true

        val userChat = Chat(
            sessionId = sessionId,
            text = text,
            isUser = true,
            date = DateHelper.getCurrentDate()
        )

        // FIX: Inject the message into the JSON memory IMMEDIATELY
        chatHistory.put(JSONObject().apply {
            put("role", "user")
            put("content", text)
        })

        viewModelScope.launch(Dispatchers.IO) {
            chatDao.insert(userChat)

            val reply = callOllama()

            val aiText = if (!reply.isNullOrBlank()) reply else "Error: F.R.O.S.T returned an empty response."
            val aiChat = Chat(
                sessionId = sessionId,
                text = aiText,
                isUser = false,
                date = DateHelper.getCurrentDate()
            )

            chatDao.insert(aiChat)
            _isTyping.postValue(false)
        }
    }

    private fun callOllama(): String? {
        try {
            val optionsObject = JSONObject().apply {
                put("num_predict", 100)
                put("num_ctx", 1024)
                put("temperature", 0.0)
                put("top_k", 1)
            }

            val bodyObject = JSONObject().apply {
                put("model", "olmo2:latest")
                put("stream", false)
                put("keep_alive", "1h")
                put("options", optionsObject)
                put("think", false)
                put("messages", chatHistory)
            }

            val jsonString = bodyObject.toString()
            val request = Request.Builder()
                .url(aiUrl)
                .post(jsonString.toRequestBody("application/json".toMediaType()))
                .build()

            val response = client.newCall(request).execute()
            val responseBodyString = response.body.string()

            if (!response.isSuccessful) {
                android.util.Log.e("OllamaError", "Server rejected request: $responseBodyString")
                return "Error: Ollama says -> $responseBodyString" // Show the error in the chat!
            }

            val jsonResponse = JSONObject(responseBodyString)
            return jsonResponse.getJSONObject("message").getString("content")

        } catch (e: Exception) {
            e.printStackTrace()
            android.util.Log.e("OllamaCrash", "JSON Parsing or Network crashed: ${e.message}", e)
            return null
        }
    }
}
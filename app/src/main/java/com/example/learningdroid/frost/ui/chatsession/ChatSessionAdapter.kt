package com.example.learningdroid.frost.ui.chatsession

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.learningdroid.R
import com.example.learningdroid.frost.database.ChatSession
import com.example.learningdroid.frost.helper.ChatSessionDiffCallback

class ChatSessionAdapter(
    private val onSessionClick: (ChatSession) -> Unit,
    private val onDeleteClick: (ChatSession) -> Unit
) : ListAdapter<ChatSession, ChatSessionAdapter.SessionViewHolder>(ChatSessionDiffCallback()) {

    class SessionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvSessionTitle)
        val tvDate: TextView = view.findViewById(R.id.tvSessionDate)
        val btnDelete: ImageButton = view.findViewById(R.id.btnDeleteSession)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_session, parent, false)
        return SessionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        val session = getItem(position)

        holder.tvTitle.text = session.title
        holder.tvDate.text = session.date

        // Click the whole item to open the chat
        holder.itemView.setOnClickListener {
            onSessionClick(session)
        }

        // Click only the trash can to delete it
        holder.btnDelete.setOnClickListener {
            onDeleteClick(session)
        }
    }
}
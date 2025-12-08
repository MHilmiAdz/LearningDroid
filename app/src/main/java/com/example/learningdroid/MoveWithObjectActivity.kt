package com.example.learningdroid

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MoveWithObjectActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PERSONA = "extra_persona"
        const val SIMPLE_PERSONA = "simple_persona"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_move_with_object)

        val tvObject:TextView = findViewById(R.id.tv_object_received)
        val tvParcelize:TextView = findViewById(R.id.tv_parcelize_received)

        // MOVE WITH OBJECT PARCELABLE

        val persona = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_PERSONA, Persona::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_PERSONA)
        }

        if (persona != null) {
            val text = "Name : ${persona.name},\nEmail : ${persona.email},\nAge : ${persona.age},\nHobby : ${persona.hobby}"
            tvObject.text = text
        }else{
            val text = "Text is in Parcelize."
            tvObject.text = text
        }

        // MOVE WITH KOTLIN PARCELIZE

        val simplePersona = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(SIMPLE_PERSONA, SimplePersona::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(SIMPLE_PERSONA)
        }

        if (simplePersona != null) {
            val text = "Name : ${simplePersona.name},\nAge : ${simplePersona.age},\nHeight : ${simplePersona.height},\nWeight : ${simplePersona.weight}"
            tvParcelize.text = text
        }else {
            val text = "Text is in Parcelable"
            tvParcelize.text = text
        }
    }
}
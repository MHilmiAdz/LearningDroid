package com.example.learningdroid.datapass

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.learningdroid.databinding.ActivityMoveWithObjectBinding


class MoveWithObjectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoveWithObjectBinding

    companion object {
        const val EXTRA_PERSONA = "extra_persona"
        const val SIMPLE_PERSONA = "simple_persona"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMoveWithObjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val persona = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_PERSONA, Persona::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_PERSONA)
        }

        if (persona != null) {
            val text = "Name : ${persona.name},\nEmail : ${persona.email},\nAge : ${persona.age},\nHobby : ${persona.hobby}"
            binding.tvObjectReceived.text = text
        }else{
            val text = "Text is in Parcelize."
            binding.tvObjectReceived.text = text
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
            binding.tvParcelizeReceived.text = text
        }else {
            val text = "Text is in Parcelable"
            binding.tvParcelizeReceived.text = text
        }
    }
}
package com.example.learningdroid

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.learningdroid.databinding.ActivityMoveBinding

class MoveActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMoveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMoveBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
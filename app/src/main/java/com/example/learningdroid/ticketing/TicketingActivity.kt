package com.example.learningdroid.ticketing

import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.learningdroid.databinding.ActivityTicketingBinding

class TicketingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTicketingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityTicketingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.rootContainer) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val seatView = binding.seatsView
        val button = binding.finishButton

        button.setOnClickListener {
            seatView.seat?.let {
                Toast.makeText(this, "Your seat is ${it.name}.", Toast.LENGTH_SHORT).show()
            } ?: run {
                Toast.makeText(this, "Please select your seat", Toast.LENGTH_SHORT).show()
            }

        }
    }
}
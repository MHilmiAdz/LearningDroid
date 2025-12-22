package com.example.learningdroid

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ScrollingActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val BUYING_KEY = "buying_key"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_scrolling)
        val mainLayout = findViewById<View>(R.id.mainLayout)

        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnBuy: Button = findViewById(R.id.btn_buy)
        btnBuy.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_buy -> {
                val resultIntent = Intent()
                resultIntent.putExtra(BUYING_KEY, "Google Pixel 10")

                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}
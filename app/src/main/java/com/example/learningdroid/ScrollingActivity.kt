package com.example.learningdroid

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class ScrollingActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val BUYING_KEY = "buying_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_scrolling)

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
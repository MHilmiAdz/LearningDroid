package com.example.learningdroid.recycle

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.learningdroid.R
import com.example.learningdroid.databinding.ActivityScrollingBinding

class ScrollingActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityScrollingBinding

    companion object {
        const val BUYING_KEY = "buying_key"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnBuy.setOnClickListener(this)
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
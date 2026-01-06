package com.example.learningdroid

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.learningdroid.databinding.ActivityMoveForResultBinding


class MoveForResultActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMoveForResultBinding

    companion object {
        const val EXTRA_SELECTED_VALUE = "extra_selected_value"
        const val RESULT_CODE = 110
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMoveForResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnChoose.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (binding.rgNumber.checkedRadioButtonId > 0) {
            var value = 0
            when (binding.rgNumber.checkedRadioButtonId) {
                R.id.rb_50 -> value = 50
                R.id.rb_100 -> value = 100
                R.id.rb_150 -> value = 150
                R.id.rb_200 -> value = 200
            }
            val resultIntent = Intent().putExtra(EXTRA_SELECTED_VALUE, value)
            setResult(RESULT_CODE, resultIntent)
            finish()
        }
    }
}
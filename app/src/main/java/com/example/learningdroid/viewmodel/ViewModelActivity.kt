package com.example.learningdroid.viewmodel

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.learningdroid.databinding.ActivityViewModelBinding


class ViewModelActivity : AppCompatActivity() {
    lateinit var binding: ActivityViewModelBinding
    private val viewModel: MainViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewModelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        displayResult()
        binding.btnCalculate.setOnClickListener {
            val width = binding.edtWidth.text.toString()
            val height = binding.edtHeight.text.toString()
            val length = binding.edtLength.text.toString()
            when {
                width.isEmpty() -> {
                    binding.edtWidth.error = "Width is Empty"
                }
                height.isEmpty() -> {
                    binding.edtHeight.error = "Height is Empty"
                }
                length.isEmpty() -> {
                    binding.edtLength.error = "Length is Empty"
                }
                else -> {
                    viewModel.calculate(width, height, length)
                    displayResult()
                }
            }
        }
    }

    private fun displayResult() {
        binding.tvResult.text = viewModel.result.toString()
    }
}
package com.example.learningdroid.viewmodel

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.learningdroid.R
import com.example.learningdroid.databinding.ActivityViewModelBinding
import cz.msebera.android.httpclient.util.TextUtils


class ViewModelActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityViewModelBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewModelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = MainViewModel(CuboidModel())

        binding.btnCalculate.setOnClickListener(this)
        binding.btnCalculateSurfaceArea.setOnClickListener(this)
        binding.btnCalculateCircumference.setOnClickListener(this)
        binding.btnCalculateVolume.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val length = binding.edtLength.text.toString().trim()
        val width = binding.edtWidth.text.toString().trim()
        val height = binding.edtHeight.text.toString().trim()

        when {
            TextUtils.isEmpty(length) -> {
                binding.edtLength.error = "Length is Empty"
            }
            TextUtils.isEmpty(width)-> {
                binding.edtWidth.error = "Width is Empty"
            }
            TextUtils.isEmpty(height) -> {
                binding.edtHeight.error = "Height is Empty"
            }
            
            else -> {
                val valueLength = length.toDoubleOrNull() ?: 0.0
                val valueWidth = width.toDoubleOrNull() ?: 0.0
                val valueHeight = height.toDoubleOrNull() ?: 0.0

                when (v?.id) {
                    R.id.btn_calculate -> {
                        viewModel.save(valueLength, valueWidth, valueHeight)
                        visible()
                    }
                    R.id.btn_calculate_circumference -> {
                        ("Circumference: " + viewModel.getCircumference().toString()).also { binding.tvResult.text = it }
                        gone()
                    }
                    R.id.btn_calculate_surface_area -> {
                        ("Surface Area: " + viewModel.getSurfaceArea().toString()).also { binding.tvResult.text = it }
                        gone()
                    }
                    R.id.btn_calculate_volume -> {
                        ("Volume: " + viewModel.getVolume().toString()).also { binding.tvResult.text = it }
                        gone()
                    }
                }
            }
        }
    }

    private fun visible() {
        binding.btnCalculateVolume.visibility = View.VISIBLE
        binding.btnCalculateCircumference.visibility = View.VISIBLE
        binding.btnCalculateSurfaceArea.visibility = View.VISIBLE
        binding.btnCalculate.visibility = View.GONE
    }

    private fun gone() {
        binding.btnCalculateVolume.visibility = View.GONE
        binding.btnCalculateCircumference.visibility = View.GONE
        binding.btnCalculateSurfaceArea.visibility = View.GONE
        binding.btnCalculate.visibility = View.VISIBLE
    }
}

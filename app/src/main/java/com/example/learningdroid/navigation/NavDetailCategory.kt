package com.example.learningdroid.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.learningdroid.R
import com.example.learningdroid.databinding.FragmentNavDetailCategoryBinding

class NavDetailCategory : Fragment() {
    private var _binding: FragmentNavDetailCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNavDetailCategoryBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataName = NavDetailCategoryArgs.fromBundle(arguments as Bundle).name
        val dataDescription = NavDetailCategoryArgs.fromBundle(arguments as Bundle).stock

        binding.tvCategoryName.text = dataName
        "Stock : $dataDescription".also { binding.tvCategoryDescription.text = it }

        binding.btnHome.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_navDetailCategory_to_navigationFragment)
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
package com.example.learningdroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.learningdroid.databinding.FragmentNavigationBinding
class NavigationFragment : Fragment() {
    private var _binding: FragmentNavigationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNavigationBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCategory.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_navigationFragment_to_navCategoryFragment)
        )
        binding.btnProfile.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_navigationFragment_to_navProfileActivity)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
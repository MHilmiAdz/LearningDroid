package com.example.learningdroid.fragment

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.example.learningdroid.R
import com.example.learningdroid.databinding.ActivityFlexFragmentsBinding

class FlexFragments : AppCompatActivity() {
    private lateinit var binding: ActivityFlexFragmentsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFlexFragmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val fragmentManager = supportFragmentManager
        val homeFragment = HomeFragments()
        val fragment = fragmentManager.findFragmentByTag(HomeFragments::class.java.simpleName)

        if (fragment !is HomeFragments) {
            Log.d("MyFlexibleFragment", "Fragment Name :" + HomeFragments::class.java.simpleName)
            fragmentManager.commit {
                add(R.id.frame_container, homeFragment, HomeFragments::class.java.simpleName)
            }
        }
    }
}
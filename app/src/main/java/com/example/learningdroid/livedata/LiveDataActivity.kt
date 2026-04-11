package com.example.learningdroid.livedata

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.learningdroid.R
import com.example.learningdroid.databinding.ActivityLiveDataBinding

class LiveDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLiveDataBinding
    private lateinit var liveDataTimerViewModel: MainViewData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLiveDataBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        liveDataTimerViewModel = ViewModelProvider(this)[MainViewData::class.java]
        subscribe()
    }

    private fun subscribe() {
        val elapsedTimeObserver = Observer<Long?> { aLong ->
            val newText = this@LiveDataActivity.resources.getString(R.string.seconds, aLong)
            binding.timerTextview.text = newText
        }
        liveDataTimerViewModel.getElapsedTime().observe(this, elapsedTimeObserver)
    }
}
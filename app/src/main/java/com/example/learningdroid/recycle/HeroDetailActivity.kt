package com.example.learningdroid.recycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.learningdroid.databinding.ActivityHeroDetailBinding

class HeroDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHeroDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeroDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }

        // Get strings sent from RecycleActivity
        val name = intent.getStringExtra("EXTRA_NAME")
        val description = intent.getStringExtra("EXTRA_DESCRIPTION")
        val photoUrl = intent.getStringExtra("EXTRA_PHOTO")

        // Bind data to layout views
        binding.tvDetailName.text = name
        binding.tvDetailDescription.text = description

        Glide.with(this)
            .load(photoUrl)
            .into(binding.imgDetailPhoto)
    }
}
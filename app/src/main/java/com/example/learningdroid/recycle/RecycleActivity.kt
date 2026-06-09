package com.example.learningdroid.recycle

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learningdroid.R
import com.example.learningdroid.databinding.ActivityRecycleBinding
import com.example.learningdroid.datapass.Hero

class RecycleActivity: AppCompatActivity() {
    private val list = ArrayList<Hero>()
    private lateinit var binding: ActivityRecycleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecycleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        list.addAll(getListHeroes())
        showRecyclerList()
    }

    private fun getListHeroes(): ArrayList<Hero> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.getStringArray(R.array.data_photo)
        val listHero = ArrayList<Hero>()
        for (i in dataName.indices) {
            val hero = Hero(dataName[i], dataDescription[i], dataPhoto[i])
            listHero.add(hero)
        }
        return listHero
    }

    private fun showRecyclerList() {
        binding.rvHeroes.layoutManager = LinearLayoutManager(this)
        val listHeroAdapter = ListHeroAdapter(list)
        binding.rvHeroes.adapter = listHeroAdapter

        listHeroAdapter.setOnItemClickCallback(object : ListHeroAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Hero, sharedView: android.view.View) {
                showSelectedHero(data, sharedView)
            }
        })
    }

    private fun showSelectedHero(hero: Hero, sharedView: android.view.View) {
        val intent = Intent(this, HeroDetailActivity::class.java).apply {
            putExtra("EXTRA_NAME", hero.name)
            putExtra("EXTRA_DESCRIPTION", hero.description)
            putExtra("EXTRA_PHOTO", hero.photo)
        }
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this@RecycleActivity,
            sharedView,
            "shared_hero_image"
        )
        startActivity(intent, options.toBundle())
    }
}
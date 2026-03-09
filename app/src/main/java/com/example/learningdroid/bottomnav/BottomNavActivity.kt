package com.example.learningdroid.bottomnav

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.learningdroid.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bottom_nav)
        
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        // Fix: Use supportFragmentManager to find NavHostFragment when using FragmentContainerView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        
        // Note: setupActionBarWithNavController is omitted because the theme is NoActionBar.
        // To use it, add a Toolbar to the layout and call setSupportActionBar(toolbar).

        navView.setupWithNavController(navController)
    }
}
package com.app.codingtest.movieapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.app.codingtest.movieapp.R
import com.app.codingtest.movieapp.databinding.MainActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNavigationGraph()
    }

    private fun setNavigationGraph() {
        val navController =
            (supportFragmentManager.findFragmentById(R.id.nav_container) as NavHostFragment)
                .navController
        val graph = navController
            .navInflater.inflate(R.navigation.mobile_navigation)
        val appBarConfiguration = AppBarConfiguration(graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }
}
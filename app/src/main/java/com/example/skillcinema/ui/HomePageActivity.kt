package com.example.skillcinema.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.skillcinema.R
import com.example.skillcinema.databinding.ActivityHomePageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomePageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navView: BottomNavigationView = binding.navView




        val navController = findNavController(R.id.nav_host_fragment_activity_home_page)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home_btn, R.id.navigation_dashboard_btn, R.id.navigation_notifications_btn
            )
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}
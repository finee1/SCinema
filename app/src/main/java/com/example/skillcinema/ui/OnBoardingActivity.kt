package com.example.skillcinema.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.skillcinema.R
import dagger.hilt.android.AndroidEntryPoint

private const val ONBOARDING_PREFS_NAME = "onboarding prefs"
private const val KEY_OBOARDING = "onboarding key"
@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences(ONBOARDING_PREFS_NAME, MODE_PRIVATE)

        val selector = prefs.getInt(KEY_OBOARDING, 0)

        if (selector == 1) {
            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
            finish()
        }
        prefs.edit().putInt(KEY_OBOARDING, 1).apply()

        setContentView(R.layout.activity_main)

    }
}
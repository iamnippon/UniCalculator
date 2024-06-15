package com.zxdeveloper.myapplication

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.zxdeveloper.myapplication.databinding.ActivityAboutBinding

class AboutActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // back button
        binding.aboutBackButton.setOnClickListener {
            finish()
        }
        binding.aboutBackButtonHitbox.setOnClickListener {
            finish()
        }


        }
    }


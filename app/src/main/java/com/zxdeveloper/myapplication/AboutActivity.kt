package com.zxdeveloper.myapplication

import android.content.Intent
import android.net.Uri
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

        binding.aboutGithub.setOnClickListener {
            // open github
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://github.com/iamnippon")
            )
            startActivity(browserIntent)
        }

        binding.aboutDiscord.setOnClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://x.com/Iamnippon1")
            )
            startActivity(browserIntent)
        }

        binding.aboutLicense.setOnClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://github.com/iamnippon/UniCalculator/blob/main/LICENSE")
            )
        }


    }

}



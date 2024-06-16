package com.zxdeveloper.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sothree.slidinguppanel.library.BuildConfig
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
            startActivity(browserIntent)
        }

        binding.aboutPrivacyPolicy.setOnClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://iamnippon.github.io/android/")
            )
            startActivity(browserIntent)
        }

        var clickAppVersionCount = 0
        val appVersion = this.getString(R.string.app_version)
        binding.aboutAppVersion.text = appVersion
        binding.aboutAppVersion.setOnClickListener {
            clickAppVersionCount++
            if (clickAppVersionCount > 3) {
                Toast.makeText(this, "Thank you for using this app!", Toast.LENGTH_SHORT).show()
                clickAppVersionCount = 0
            }

        }

    }}



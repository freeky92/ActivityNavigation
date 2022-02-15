package com.asurspace.activitynavigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asurspace.activitynavigation.databinding.ActivityAboutBinding

class AboutActivity : BaseActivityCompat() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            versionNameField.text = BuildConfig.VERSION_NAME
            versionCodeField.text = BuildConfig.VERSION_CODE.toString()
            okTb.setOnClickListener { finish() }
        }
    }

}
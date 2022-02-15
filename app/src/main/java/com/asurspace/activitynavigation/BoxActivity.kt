package com.asurspace.activitynavigation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asurspace.activitynavigation.databinding.ActivityBoxBinding

class BoxActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBoxBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoxBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toMainTb.setOnClickListener {
            onToMainMenuPressed()
        }

    }

    private fun onToMainMenuPressed() {
        val intent = Intent(this, MainActivity::class.java)
        // FLAG_ACTIVITY_CLEAR_TOP - means pop backStack & create MainActivity
        // FLAG_ACTIVITY_SINGLE_TOP - means pop backStack & if(MainActivity already created) -> go there
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
    }

}
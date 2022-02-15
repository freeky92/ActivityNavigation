package com.asurspace.activitynavigation

import androidx.appcompat.app.AppCompatActivity

open class BaseActivityCompat: AppCompatActivity() {

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}
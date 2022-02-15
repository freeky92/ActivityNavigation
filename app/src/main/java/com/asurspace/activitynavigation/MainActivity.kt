package com.asurspace.activitynavigation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.asurspace.activitynavigation.databinding.ActivityMainBinding
import com.asurspace.activitynavigation.model.Options

class MainActivity : BaseActivityCompat() {

    private lateinit var options: Options

    private val requestStartLauncher = registerForActivityResult(
        StartActivityForResult(),
        ::onResultArrived
    )

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            openBoxTb.setOnClickListener {
                val i = Intent(this@MainActivity, OpenBoxActivity::class.java)
                i.putExtra(OpenBoxActivity.EXTRA_OPTIONS, options)
                requestStartLauncher.launch(i)
            }
            optionsTb.setOnClickListener {
                val i = Intent(this@MainActivity, OptionsActivity::class.java)
                i.putExtra(OptionsActivity.EXTRA_OPTIONS, options)
                requestStartLauncher.launch(i)
            }
            aboutTb.setOnClickListener {
                val i = Intent(this@MainActivity, AboutActivity::class.java)
                requestStartLauncher.launch(i)
            }
            exitTb.setOnClickListener { onSupportNavigateUp() }
        }

        options = savedInstanceState?.getParcelable(KEY_OPTIONS) ?: Options.DEFAULT
    }

    private fun onResultArrived(result: ActivityResult?) {
        if (result?.resultCode == OPTIONS_REQUEST_CODE || result?.resultCode == RESULT_OK) {
            options = result.data?.getParcelableExtra(OptionsActivity.EXTRA_OPTIONS)
                ?: throw IllegalArgumentException("Can't get the update data from options activity")
            Log.i("Success", "$options")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_OPTIONS, options)
    }

    companion object {
        @JvmStatic
        private val KEY_OPTIONS = "OPTIONS"

        @JvmStatic
        private val OPTIONS_REQUEST_CODE = 1
    }

}
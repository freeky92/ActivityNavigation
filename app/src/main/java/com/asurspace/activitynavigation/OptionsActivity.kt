package com.asurspace.activitynavigation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.asurspace.activitynavigation.databinding.ActivityOptionsBinding
import com.asurspace.activitynavigation.model.Options

class OptionsActivity : BaseActivityCompat() {

    private lateinit var binding: ActivityOptionsBinding

    private lateinit var options: Options
    private lateinit var newOptions: Options

    private lateinit var spinnerAdapter: ArrayAdapter<BoxCountItem>
    private lateinit var boxCountItems: List<BoxCountItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        options = savedInstanceState?.getParcelable(KEY_OPTIONS)
            ?: intent.getParcelableExtra(EXTRA_OPTIONS)
                    ?: throw IllegalArgumentException("You need to specify EXTRA_OPTIONS argument to launch this activity")

        newOptions = options

        setUpSpinner()
        setUpCheckBox()
        updateUi()

        with(binding) {
            cancelTb.setOnClickListener { finish() }
            confermTb.setOnClickListener { onConfirmPressed() }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(EXTRA_OPTIONS, options)
    }

    private fun setUpSpinner() {
        boxCountItems = (2..6).map { BoxCountItem(it, "$it boxes") }

        spinnerAdapter = ArrayAdapter(
            this,
            R.layout.spinner_adapter_item,
            boxCountItems
        )
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_adapter_item)

        binding.spinner.adapter = spinnerAdapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                p0: AdapterView<*>?,
                p1: View?,
                position: Int,
                id: Long
            ) {
                val count = boxCountItems[position].count
                newOptions = options.copy(boxCount = count)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    private fun setUpCheckBox() {
        binding.checkB.setOnCheckedChangeListener { _, b ->
            newOptions = newOptions.copy(isTimerEnable = b)
        }
    }

    private fun updateUi() {
        binding.checkB.isChecked = options.isTimerEnable
        val currentIndex = boxCountItems.indexOfFirst { it.count == options.boxCount }

        binding.spinner.setSelection(currentIndex)
    }

    private fun onConfirmPressed() {
        val i = Intent()
        i.putExtra(EXTRA_OPTIONS, newOptions)
        setResult(Activity.RESULT_OK, i)
        finish()
    }

    override fun onBackPressed() {
        val i = Intent()
        i.putExtra(EXTRA_OPTIONS, options)
        setResult(Activity.RESULT_OK, i)
        finish()
    }

    companion object {

        @JvmStatic
        val EXTRA_OPTIONS = "EXTRA_OPTIONS"

        @JvmStatic
        private val KEY_OPTIONS = "KEY_OPTIONS"

    }

    class BoxCountItem(
        val count: Int,
        private val optionTitle: String
    ) {
        override fun toString(): String {
            return optionTitle
        }
    }
}
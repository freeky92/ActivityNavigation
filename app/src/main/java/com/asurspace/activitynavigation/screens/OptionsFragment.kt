package com.asurspace.activitynavigation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.asurspace.activitynavigation.R
import com.asurspace.activitynavigation.contract.CustomAction
import com.asurspace.activitynavigation.contract.HasCustomAction
import com.asurspace.activitynavigation.contract.HasCustomTitle
import com.asurspace.activitynavigation.contract.navigator
import com.asurspace.activitynavigation.databinding.FragmentOptionsBinding
import com.asurspace.activitynavigation.model.Options

class OptionsFragment : Fragment(), HasCustomTitle, HasCustomAction {

    private var _binding: FragmentOptionsBinding? = null
    private val binding get() = _binding!!

    private lateinit var options: Options
    private lateinit var spinnerAdapter: ArrayAdapter<BoxCountItem>
    private lateinit var boxCountItems: List<BoxCountItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        options = savedInstanceState?.getParcelable(KEY_OPTIONS)
            ?: arguments?.getParcelable(ARG_OPTIONS)
                    ?: throw IllegalArgumentException("You need to specify ARG_OPTIONS argument to launch this fragment")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOptionsBinding.inflate(inflater, container, false)

        setUpSpinner()
        setUpCheckBox()
        updateUi()

        with(binding) {
            cancelTb.setOnClickListener { onCancelPressed() }
            confermTb.setOnClickListener { onConfirmPressed() }
        }

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(ARG_OPTIONS, options)
    }

    private fun setUpSpinner() {
        boxCountItems = (2..6).map { BoxCountItem(it, "$it boxes") }

        spinnerAdapter = ArrayAdapter(
            requireContext(),
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
                options = options.copy(boxCount = count)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    private fun setUpCheckBox() {
        binding.checkB.setOnCheckedChangeListener { _, b ->
            options = options.copy(isTimerEnable = b)
        }
    }

    private fun updateUi() {
        binding.checkB.isChecked = options.isTimerEnable
        val currentIndex = boxCountItems.indexOfFirst { it.count == options.boxCount }

        binding.spinner.setSelection(currentIndex)
    }

    private fun onConfirmPressed() {
        navigator().provideResult(options)
        navigator().goBack()
    }

    private fun onCancelPressed() {
        navigator().goBack()
    }

    override fun getCustomAction() = CustomAction(
        iconRes = R.drawable.ic_check_circle_24,
        textRes = R.string.confirm,
        onCustomAction = {
            navigator().run {
                provideResult(options)
                goBack()
            }
        }
    )

    override fun getTitleRes(): Int = R.string.options

    companion object {

        @JvmStatic
        private val ARG_OPTIONS = "ARG_OPTIONS"

        @JvmStatic
        private val KEY_OPTIONS = "KEY_OPTIONS"

        @JvmStatic
        fun newInstance(options: Options) = OptionsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_OPTIONS, options)
            }
        }

    }

    class BoxCountItem(
        val count: Int,
        private val optionTitle: String
    ) {
        override fun toString(): String {
            return optionTitle
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
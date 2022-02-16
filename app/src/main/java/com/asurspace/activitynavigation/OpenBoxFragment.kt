package com.asurspace.activitynavigation

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asurspace.activitynavigation.contract.HasCustomTitle
import com.asurspace.activitynavigation.contract.navigator
import com.asurspace.activitynavigation.databinding.FragmentOpenBoxBinding
import com.asurspace.activitynavigation.databinding.ItemBoxBinding
import com.asurspace.activitynavigation.model.Options
import com.google.android.material.snackbar.Snackbar
import kotlin.math.max
import kotlin.properties.Delegates
import kotlin.random.Random

class OpenBoxFragment : Fragment(), HasCustomTitle {

    private var _binding: FragmentOpenBoxBinding? = null
    private val binding get() = _binding!!

    private lateinit var options: Options

    private lateinit var timer: CountDownTimer

    private var timerStartTimestamp by Delegates.notNull<Long>()
    private var boxIndex by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        options = arguments?.getParcelable(ARG_OPTIONS)
            ?: throw  IllegalArgumentException("Can't launch OpenBoxFragment without option.")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOpenBoxBinding.inflate(inflater, container, false)

        boxIndex = savedInstanceState?.getInt(KEY_INDEX) ?: Random.nextInt(options.boxCount)

        if (options.isTimerEnable) {
            timerStartTimestamp =
                savedInstanceState?.getLong(KEY_START_TIMESTAMP) ?: System.currentTimeMillis()
            setupTimer()
            updateTimerUi()
        }

        createBoxes()

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_INDEX, boxIndex)
        if (options.isTimerEnable) {
            outState.putLong(KEY_START_TIMESTAMP, timerStartTimestamp)
        }
    }

    override fun onStart() {
        super.onStart()
        if (options.isTimerEnable) {
            timer.start()
        }
    }

    override fun onStop() {
        super.onStop()
        if (options.isTimerEnable) {
            timer.cancel()
        }
    }

    private fun createBoxes() {
        val boxBindings = (0 until options.boxCount).map { index ->
            val boxBinding = ItemBoxBinding.inflate(layoutInflater)
            boxBinding.root.id = View.generateViewId()
            boxBinding.boxTitleTextView.text =
                getString(R.string.box_title, index + 1)
            boxBinding.root.setOnClickListener { view ->
                onBoxSelected(view)
            }
            boxBinding.root.tag = index
            binding.root.addView(boxBinding.root)
            boxBinding
        }

        binding.flow.referencedIds = boxBindings.map { it.root.id }.toIntArray()
    }

    private fun onBoxSelected(view: View) {
        if (view.tag as Int == boxIndex) {
           navigator().showCongratulationScreen()
        } else {
            Snackbar.make(
                binding.root,
                "This box is empty. Try another one.",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun setupTimer() {
        timer = object : CountDownTimer(getRemainingSeconds() * 1000, 1000) {
            override fun onFinish() {
                updateTimerUi()
                showTimerEndDialog()
            }

            override fun onTick(p0: Long) {
                updateTimerUi()
            }
        }
    }

    private fun updateTimerUi() {
        if (getRemainingSeconds() >= 0) {
            binding.timeTextView.visibility = View.VISIBLE
            binding.timeTextView.text = getString(R.string.timer_text, getRemainingSeconds())
        } else {
            binding.timeTextView.visibility = View.GONE
        }
    }

    private fun showTimerEndDialog() {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.the_end))
            .setMessage(getString(R.string.oops))
            .setCancelable(false)
            .setPositiveButton("Ok") { _, _ ->
                requireActivity().onBackPressed()
                //finish()
            }
            .create()
        dialog.show()
    }

    private fun getRemainingSeconds(): Long {
        val finishedAt = timerStartTimestamp + TIMER_DURATION
        return max(0L, (finishedAt - System.currentTimeMillis()) / 1000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        private val ARG_OPTIONS = "ARG_OPTIONS"

        @JvmStatic
        private val KEY_RESULT = "KEY_RESULT"

        @JvmStatic
        private val KEY_INDEX = "KEY_INDEX"

        @JvmStatic
        private val KEY_START_TIMESTAMP = "KEY_START_TIMESTAMP"

        @JvmStatic
        private val TIMER_DURATION = 10_000L

        @JvmStatic
        fun newInstance(options: Options) = OpenBoxFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_OPTIONS, options)
            }
        }

    }

    override fun getTitleRes() = R.string.open_box
}
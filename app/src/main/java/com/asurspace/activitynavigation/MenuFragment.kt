package com.asurspace.activitynavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asurspace.activitynavigation.contract.navigator
import com.asurspace.activitynavigation.databinding.FragmentMenuBinding
import com.asurspace.activitynavigation.model.Options

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private lateinit var options: Options

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        options = savedInstanceState?.getParcelable(KEY_OPTIONS) ?: Options.DEFAULT
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)

        navigator().listenResults(Options::class.java, viewLifecycleOwner, ::onResultArrived)

        with(binding) {
            openBoxTb.setOnClickListener {
                navigator().showOpenBoxScreen(options)
            }
            optionsTb.setOnClickListener {
                navigator().showOptionsScreen(options)
            }
            aboutTb.setOnClickListener {
                navigator().showAboutScreen()
            }
            exitTb.setOnClickListener { requireActivity().finish() }
        }

        return binding.root
    }

    private fun onResultArrived(options: Options) {
        this.options = options
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
        private val KEY_OPTIONS = "OPTIONS"

        @JvmStatic
        private val OPTIONS_REQUEST_CODE = 1

    }

}
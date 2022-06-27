package com.example.bpz_lab1

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

abstract class TrialFragment : Fragment() {
    private val trialDialog by lazy { Dialog(requireContext()).apply { setContentView(R.layout.trial_dialog) } }
    private val vm: MainViewModel by viewModels()

    fun showTrialDialog() {
        if (trialDialog.isShowing) return
        trialDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        trialDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        trialDialog.findViewById<ImageView>(R.id.ivClose).setOnClickListener {
            trialDialog.dismiss()
        }

        trialDialog.findViewById<Button>(R.id.btnActivate).setOnClickListener {
            val userKey = trialDialog.findViewById<EditText>(R.id.etKey).text.toString()

            activate(userKey)
        }

        trialDialog.show()
    }

    private fun activate(userKey: String) {
        val isActivated = vm.activate(userKey)
        if (isActivated) {
            (requireActivity() as MainActivity).timer.cancel()
            ((requireActivity() as MainActivity).application as App).sharedPrefs.setIsActivated(true)
            showToast(requireContext(), "Congratulations! Program is now activated.")
            trialDialog.dismiss()
        } else {
            showToast(requireContext(), "Wrong key")
        }
    }
}
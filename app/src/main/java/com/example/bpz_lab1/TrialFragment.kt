package com.example.bpz_lab1

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment

abstract class TrialFragment : Fragment() {
    private val trialDialog by lazy { Dialog(requireContext()).apply { setContentView(R.layout.trial_dialog) } }

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
            (requireActivity() as MainActivity).timer.cancel()
            (requireActivity() as MainActivity).sharedPrefs.setIsActivated(true)
        }

        trialDialog.show()
    }
}
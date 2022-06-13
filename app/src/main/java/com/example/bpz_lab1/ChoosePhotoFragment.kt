package com.example.bpz_lab1

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.bpz_lab1.FileManager.getTmpFileUri

class ChoosePhotoFragment : TrialFragment() {
    private var latestTmpUri: Uri? = null

    private val trialPeriodObserver = object : Observer {
        override fun update() {
            showTrialDialog()
        }
    }

    private val registerTakePicture = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            latestTmpUri?.let { uri ->
                navigateToEditFragment(uri)
            }
        }
    }

    private val getImagesFromGallery: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                navigateToEditFragment(it)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_choosephoto, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.takeFromGallery).setOnClickListener {
            getImagesFromGallery.launch("image/*")
        }

        view.findViewById<Button>(R.id.takePhoto).setOnClickListener {
            takePhoto()
        }
    }

    private fun navigateToEditFragment(uri: Uri) {
        val editFragment = EditFragment.newInstance(uri = uri.toString())

        parentFragmentManager
            .beginTransaction()
            .replace(R.id.container, editFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun takePhoto() {
        getTmpFileUri(requireContext()).let { uri ->
            latestTmpUri = uri
            registerTakePicture.launch(uri)
        }
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).observable.addObserver(trialPeriodObserver)
    }

    override fun onPause() {
        super.onPause()
        (requireActivity() as MainActivity).observable.removeObserver(trialPeriodObserver)
    }
}
package com.example.bpz_lab1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.core.view.drawToBitmap
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.options

class EditFragment : TrialFragment(), HasCustomAction {
    private lateinit var imageView: ImageView

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            val uriContent = result.uriContent
            imageView.setImageURI(uriContent)
        }
    }


    private val trialPeriodObserver = object : Observer {
        override fun update() {
            showTrialDialog()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_edit, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageView = view.findViewById(R.id.ivImage)

        imageView.setImageURI(getUriString().toUri())


        view.findViewById<Button>(R.id.btnFilter).setOnClickListener {
            imageView.post {
                imageView.setImageBitmap(imageView.drawToBitmap().toBlackAndWhite())
            }
        }

        view.findViewById<Button>(R.id.btnCrop).setOnClickListener {
            cropImage.launch(
                options(uri = getCurrentImageUri(image = imageView))
            )
        }
    }

    private fun getCurrentImageUri(image: ImageView): Uri =
        image.drawToBitmap().toUrl(requireContext())

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).observable.addObserver(trialPeriodObserver)
    }

    override fun onPause() {
        super.onPause()
        (requireActivity() as MainActivity).observable.removeObserver(trialPeriodObserver)
    }

    private fun getUriString() = requireArguments().getString(URI, "")

    companion object {
        private const val URI = "URI"

        fun newInstance(uri: String): EditFragment {
            val args = Bundle().apply {
                putString(URI, uri)
            }

            val fragment = EditFragment().apply {
                arguments = args
            }
            return fragment
        }
    }

    override fun getCustomAction(): CustomAction =
        CustomAction(
            R.drawable.ic_share,
            ::shareImage
        )

    private fun shareImage() {
        val uri = imageView.drawToBitmap().toUrl(requireContext())

        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uri)
            type = "image/jpg"
        }

        requireContext().startActivity(Intent.createChooser(shareIntent, null))
    }
}
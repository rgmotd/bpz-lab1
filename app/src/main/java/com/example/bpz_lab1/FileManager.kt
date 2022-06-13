package com.example.bpz_lab1

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.util.*

object FileManager {

    fun getTmpFileUri(context: Context): Uri {
        val tmpFile =
            File.createTempFile("tmp_image_file", ".png", context.cacheDir).apply {
                createNewFile()
                deleteOnExit()
            }

        return FileProvider.getUriForFile(
            Objects.requireNonNull(context),
            BuildConfig.APPLICATION_ID + ".provider", tmpFile
        )
    }
}
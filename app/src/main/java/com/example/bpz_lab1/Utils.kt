package com.example.bpz_lab1

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*

fun Bitmap.toBlackAndWhite(): Bitmap {
    val width: Int = this.width
    val height: Int = this.height
    val pixels = IntArray(width * height)
    this.getPixels(pixels, 0, width, 0, 0, width, height)

    val alpha = 0xFF shl 24

    for (i in 0 until height) {
        for (j in 0 until width) {
            var grey = pixels[width * i + j]
            val red = grey and 0x00FF0000 shr 16
            val green = grey and 0x0000FF00 shr 8
            val blue = grey and 0x000000FF
            grey = (red * 0.3 + green * 0.59 + blue * 0.11).toInt()
            grey = alpha or (grey shl 16) or (grey shl 8) or grey
            pixels[width * i + j] = grey
        }
    }
    val newBmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
    newBmp.setPixels(pixels, 0, width, 0, 0, width, height)
    return newBmp
}

fun Bitmap.toUrl(context: Context): Uri {
    val f = File(context.cacheDir, "file_${Random().nextLong()}")
    f.createNewFile()

    val byteArrayOutputStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    val bmp = byteArrayOutputStream.toByteArray()

    val fileOutputStream = FileOutputStream(f)
    fileOutputStream.write(bmp)
    fileOutputStream.flush()
    fileOutputStream.close()
    return FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", f)
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
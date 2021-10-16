package com.thomasjbarrerasconsulting.faces.kotlin.facedetector

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import kotlin.math.max
import kotlin.math.min

class BitmapScaler {
    companion object {
        fun scaleBitmap(bitmap: Bitmap, scale: Float, width: Int, height: Int): Bitmap {
            // Determine how much to scale the image.
            val fullScaleFactor = min(
                scale * width.toFloat() / bitmap.width.toFloat(),
                scale * height.toFloat() / bitmap.height.toFloat()
            )

            val scaledBitmap = Bitmap.createScaledBitmap(
                bitmap,
                (fullScaleFactor * bitmap.width).toInt(),
                (fullScaleFactor * bitmap.height).toInt(),
                true
            )
            val x = max(0.0f,  ((scaledBitmap.width - width) / 2.0f) ).toInt()
            val y = max(0.0f,  ((scaledBitmap.height - height) / 2.0f)).toInt()
            val w = min(width.toFloat(), scaledBitmap.width.toFloat() - x).toInt()
            val h = min(height.toFloat(), scaledBitmap.height.toFloat() - y).toInt()

            return Bitmap.createBitmap(scaledBitmap, x, y, w, h)
        }
    }

}
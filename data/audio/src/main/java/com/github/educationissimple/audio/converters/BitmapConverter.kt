package com.github.educationissimple.audio.converters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Base64.DEFAULT
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class BitmapConverter {

    @TypeConverter
    fun bitmapToBase64(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val byteArray = baos.toByteArray()
        val result = Base64.encodeToString(byteArray, DEFAULT)
        return result
    }

    @TypeConverter
    fun base64ToBitmap(base64String: String): Bitmap {
        val byteArray = Base64.decode(base64String, DEFAULT)
        val result = BitmapFactory.decodeByteArray(
            byteArray,
            0,
            byteArray.size
        )
        return result
    }

}
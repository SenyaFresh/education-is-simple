package com.github.educationissimple.audio.converters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Base64.DEFAULT
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

/**
 * A utility class to convert [Bitmap] objects to and from Base64 strings. Used for saving
 * bitmap images in Room Database.
 */
class BitmapConverter {

    /**
     * Converts a [Bitmap] into a Base64-encoded string.
     *
     * @param bitmap The [Bitmap] object to be converted to Base64.
     * @return A Base64-encoded [String] representing the [Bitmap].
     */
    @TypeConverter
    fun bitmapToBase64(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val byteArray = baos.toByteArray()
        val result = Base64.encodeToString(byteArray, DEFAULT)
        return result
    }

    /**
     * Converts a Base64-encoded string back into a [Bitmap].
     *
     * @param base64String The Base64-encoded string representing the image.
     * @return The decoded [Bitmap] object.
     */
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
package com.example.namequizapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Parcelable
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.Serializable

class Entry(var entryName: String, var bitmapByteArray: ByteArray) : Serializable {

    //static methods goes inside companion object
    companion object {
        fun byteArrayToBitmap(ba: ByteArray) : Bitmap
        {
            val inputStream = ByteArrayInputStream(ba)
            val o = BitmapFactory.Options()
            val bm = BitmapFactory.decodeStream(inputStream, null, o)
            return bm
        }

        fun bitmapToByteArray(bm: Bitmap) : ByteArray
        {
            val stream = ByteArrayOutputStream()
            bm.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val imageBytes = stream.toByteArray()
            return imageBytes
        }

    }

}
package com.example.namequizapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*


class QuizActivity : AppCompatActivity() {

    private var entryList = arrayListOf<Entry>()
    private var usedIndexes = arrayListOf<Int>()
    private lateinit var activeEntry: Entry
    private var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        entryList = intent.getSerializableExtra(EXTRA_MESSAGE) as ArrayList<Entry>


        //set random start picture
        var imageView = findViewById<ImageView>(R.id.imageView)
        var number = Random().nextInt(entryList.size)
        imageView.setImageBitmap(byteArrayToBitmap(entryList[number].bitmapByteArray))
        usedIndexes.add(number)
        activeEntry = entryList[number]

    }


    fun submitClick(view: View)
    {
        var imageView = findViewById<ImageView>(R.id.imageView)
        var nameInput = findViewById<EditText>(R.id.editText3).text
        var scoreText = findViewById<TextView>(R.id.scoreView)

        //Find random entry not yet used. Spaghetti code
        var number = Random().nextInt(entryList.size)
        while(true)
        {
            if(!usedIndexes.contains(number))
                break

            number = Random().nextInt(entryList.size)
        }


        if(nameInput.toString().equals(activeEntry.entryName, true))
        {
            score++
            scoreText.text = score.toString()
        }
        else
        {
            val text = "Correct name was ${activeEntry.entryName}"
            val duration = Toast.LENGTH_LONG
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        }

        //Next quiz question
        imageView.setImageBitmap(byteArrayToBitmap(entryList[number].bitmapByteArray))
        usedIndexes.add(number)
        activeEntry = entryList[number]

    }

    private fun bitmapToByteArray(bm: Bitmap) : ByteArray
    {
        val stream = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val imageBytes = stream.toByteArray()
        return imageBytes
    }

    private fun byteArrayToBitmap(ba: ByteArray) : Bitmap
    {
        val inputStream = ByteArrayInputStream(ba)
        val o = BitmapFactory.Options()
        val bm = BitmapFactory.decodeStream(inputStream, null, o)
        return bm
    }

}

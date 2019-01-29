package com.example.namequizapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class AddActivity : AppCompatActivity() {

    private val IMAGE_REQUEST = 111
    private var entryList = arrayListOf<Entry>()
    private lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        entryList = intent.getSerializableExtra(AlarmClock.EXTRA_MESSAGE) as ArrayList<Entry>
    }

    fun imageClick(view: View)
    {
        var imageChooserIntent = Intent(Intent.ACTION_GET_CONTENT)
        imageChooserIntent.setType("image/*")
        startActivityForResult(imageChooserIntent, IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data!!
            findViewById<TextView>(R.id.textView2).text = imageUri.lastPathSegment
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
        }
    }

    fun addClick(view: View)
    {
        if(bitmap != null)
        {
            val name = findViewById<EditText>(R.id.editText).text.toString()
            var newEntry = Entry(name, Entry.bitmapToByteArray(bitmap))
            entryList.add(newEntry)
        }
        else
        {
            //velg bilde feilmelding
        }

    }

    override fun onBackPressed() {
        //super.onBackPressed()
        val returnIntent = Intent()
        returnIntent.putExtra(AlarmClock.EXTRA_MESSAGE, entryList)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

}

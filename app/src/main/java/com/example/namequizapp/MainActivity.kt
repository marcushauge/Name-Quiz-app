package com.example.namequizapp

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.View
import java.io.ByteArrayOutputStream
import java.util.jar.Manifest
import java.io.ByteArrayInputStream


class MainActivity : AppCompatActivity() {

    private val CHANGE_FROM_DATABASE_REQUEST = 123
    private val PERMISSION_REQUEST = 999
    private val ADD_REQUEST = 321
    private var entryList = arrayListOf<Entry>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var e1 = Entry(resources.getString(R.string.circle_entry), bitmapToByteArray(BitmapFactory.decodeResource(resources, R.drawable.circle)))
        var e2 = Entry(resources.getString(R.string.square_entry), bitmapToByteArray(BitmapFactory.decodeResource(resources, R.drawable.square)))
        var e3 = Entry(resources.getString(R.string.triangle_entry), bitmapToByteArray(BitmapFactory.decodeResource(resources, R.drawable.triangle)))

        entryList.add(e1)
        entryList.add(e2)
        entryList.add(e3)

        requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.MEDIA_CONTENT_CONTROL, android.Manifest.permission.MANAGE_DOCUMENTS), PERMISSION_REQUEST)


    }

    fun databaseClick(view: View)
    {
        val intent = Intent(this, DatabaseActivity::class.java)
        intent.putExtra(EXTRA_MESSAGE, entryList)
        startActivityForResult(intent, CHANGE_FROM_DATABASE_REQUEST)
    }

    fun quizClick(view: View)
    {
        val intent = Intent(this, QuizActivity::class.java)
        intent.putExtra(EXTRA_MESSAGE, entryList)
        startActivity(intent)
    }

    fun addClick(view: View)
    {
        val intent = Intent(this, AddActivity::class.java)
        intent.putExtra(EXTRA_MESSAGE, entryList)
        startActivityForResult(intent, ADD_REQUEST)
    }


    //Sets the "database"-variable to whatever the Database activity returns
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CHANGE_FROM_DATABASE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                entryList = data?.getSerializableExtra(EXTRA_MESSAGE) as ArrayList<Entry>
            }
        }

        if (requestCode == ADD_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                entryList = data?.getSerializableExtra(EXTRA_MESSAGE) as ArrayList<Entry>
            }
        }
    }

    private fun resToUri(resid: Int): Uri{
        val pack = applicationContext.resources.getResourcePackageName(resid)
        val type = applicationContext.resources.getResourceTypeName(resid)
        val ename = applicationContext.resources.getResourceEntryName(resid)

        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + pack + '/' + type + '/' + ename)
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

package com.example.namequizapp

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.provider.ContactsContract
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class DatabaseActivity : AppCompatActivity() {

    private var entryList = arrayListOf<Entry>()
    private lateinit var dialog: AlertDialog

    //List of each horizontal "boxes" for easy deletion.
    private var horizontalList = ArrayList<LinearLayout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)

        requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.MEDIA_CONTENT_CONTROL, android.Manifest.permission.MANAGE_DOCUMENTS), 999)

        //The "database"
        entryList = intent.getSerializableExtra(EXTRA_MESSAGE) as ArrayList<Entry>

        //The scrollable linear layout
        var linear = findViewById<LinearLayout>(R.id.linearLayout)

        for(entry in entryList)
        {
            //Horizontal box inside the scrollable vertical list
            var linearHorizontal: LinearLayout = LinearLayout(this)
            linearHorizontal.orientation = LinearLayout.HORIZONTAL

            //Image
            var iv: ImageView = ImageView(this)
            iv.setImageBitmap(Entry.byteArrayToBitmap(entry.bitmapByteArray))
            linearHorizontal.addView(iv)

            //Name next to image
            var nameText = TextView(this)
            nameText.text = entry.entryName
            linearHorizontal.addView(nameText)

            linear.addView(linearHorizontal)
            horizontalList.add(linearHorizontal)
        }

        dialog = generateDialog()

    }


    fun removeEntry(view: View)
    {
        dialog.show()
    }



    private fun generateDialog() : AlertDialog
    {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this) //kan være feil
        builder.setMessage("test test test")
        builder.setTitle("Title test")

        var txtField = EditText(this)
        builder.setView(txtField)

        builder.apply {
            //Button which when pressed removes entry with equal entryName as string in the textfield
            setPositiveButton("Remove", DialogInterface.OnClickListener { dialog, which ->
                for(entry in entryList)
                {
                    if(entry.entryName.equals(txtField.text.toString(), true))
                    {
                        entryList.remove(entry)

                        //removing the view
                        for(ll in horizontalList)
                        {
                            var txtV: TextView = ll.getChildAt(1) as TextView
                            if(txtV.text.toString().equals(txtField.text.toString(), true))
                            {
                                var parent = ll.parent as ViewGroup
                                parent.removeView(ll)
                                break
                            }
                        }
                        break
                    }

                }

            })

            setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })

        }

        return builder.create()

    }

    //Sends back the "database" list, changed or not, to the main activity.
    override fun onBackPressed() {
        //super.onBackPressed()
        val returnIntent = Intent()
        returnIntent.putExtra(EXTRA_MESSAGE, entryList)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }


}

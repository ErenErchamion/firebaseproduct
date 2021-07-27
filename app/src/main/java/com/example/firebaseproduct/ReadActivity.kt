package com.example.firebaseproduct

import android.content.ContentValues.TAG
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.File


class ReadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read2)
    }

   /* fun read(view: View){
        val db = FirebaseFirestore.getInstance()
        db.collection("Products")
            .get()
            .addOnCompleteListener {

                val result: StringBuffer = StringBuffer()

                if(it.isSuccessful) {
                    for(document in it.result!!) {
                        result.append(document.data.getValue("Product Name")).append(" ")
                            .append(document.data.getValue("Product Price")).append("\n\n")
                    }
                    val textViewResult : TextView = findViewById(R.id.datatextView) as TextView
                    textViewResult.setText(result)
                }
            }
    }
*/
    fun readalternative(view: View){
        val db = FirebaseFirestore.getInstance()
        val searchEditText = findViewById(R.id.searchEditText) as EditText
        var searchId=searchEditText.text.toString()





        val docRef = db.collection("Products").document(""+searchId)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    var pName = document.getString("Product Name")
                    var pPrice = document.getString("Product Price")
                    var pStatus = document.getString("Product Status")
                    var pImagePath=document.getString("Product Image Name")
                    val PNDisplayEditText : EditText = findViewById(R.id.PNDisplayEditText) as EditText
                    val PPDisplayEditText : EditText = findViewById(R.id.PPDisplayEditText) as EditText
                    val PSDisplayEditText : EditText = findViewById(R.id.PSDisplayEditText) as EditText
                    PNDisplayEditText.setText(pName)
                    PPDisplayEditText.setText(pPrice)
                    PSDisplayEditText.setText(pStatus)



                    val storage = FirebaseStorage.getInstance()
                    val pathReference = storage.getReferenceFromUrl("gs://firabase-dummy.appspot.com/images/"+pImagePath+".jpg")

                    val localFile = File.createTempFile("images", "jpg")

                    pathReference.getFile(localFile).addOnSuccessListener {
                        val ProductImageView : ImageView = findViewById(R.id.ProductImageView) as ImageView
                        val myBitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath())
                        ProductImageView.setImageBitmap(myBitmap)

                    }.addOnFailureListener {
                        // Handle any errors
                    }

                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

    }






    }

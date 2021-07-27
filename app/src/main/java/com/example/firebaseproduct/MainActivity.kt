package com.example.firebaseproduct

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }




    fun finish(view: View){
        val db = FirebaseFirestore.getInstance()

        val ProductNameText = findViewById(R.id.ProductNameText) as EditText
        val ProductPriceText = findViewById(R.id.ProductPriceText) as EditText
        val ProductStatusText = findViewById(R.id.ProductStatusText) as EditText
        val FileNameText = findViewById(R.id.FileNameText) as EditText
        var filename=FileNameText.text.toString()
        var name=ProductNameText.text.toString()
        var price=ProductPriceText.text.toString()
        var status=ProductStatusText.text.toString()



        val product= hashMapOf(
            "Product Name" to name,
            "Product Price" to price,
            "Product Status" to status,
            "Product Image Name" to filename

        )


        db.collection("Products").document(""+filename)
            .set(product)
    }



    fun Upload(view: View){
        var intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(
                intent,
                "Please select..."
            ),
            100
        )
    }


    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {

        super.onActivityResult(
            requestCode,
            resultCode,
            data
        )

        if (requestCode == 100
            && resultCode == Activity.RESULT_OK
            && data != null
            && data.data != null
        ) {

            // Get the Uri of data
            val file_uri = data.data
            uploadImageToFirebase(file_uri)
        }

    }

    private fun uploadImageToFirebase(fileUri: Uri?) {
        if (fileUri != null) {

            val FileNameText = findViewById(R.id.FileNameText) as EditText
            var filename=FileNameText.text.toString()
            var fileName = filename +".jpg"


            val refStorage = FirebaseStorage.getInstance().reference.child("images/$fileName")

            refStorage.putFile(fileUri)
                .addOnSuccessListener(
                    OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                        taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                            val imageUrl = it.toString()
                        }
                    })

                ?.addOnFailureListener(OnFailureListener { e ->
                    print(e.message)
                })
        }

    }
    fun readButtonNext(view: View){
        val intent = Intent(this, ReadActivity::class.java)
        startActivity(intent)
            }
    }


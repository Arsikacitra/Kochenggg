package com.example.tubes

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.add_product.*

class AddProduct : AppCompatActivity() {

    private val CAMERA_REQUEST_CODE = 100;
    private val STORAGE_REQUEST_CODE = 101;
    //image click constants
    private val IMAGE_PICK_CAMERA_CODE = 102;
    private val IMAGE_PICK_GALLERY_CODE = 103;
    //arrays of permissions
    private lateinit var cameraPermissions:Array<String> //camera and storage
    private lateinit var storagePermissions:Array<String> //camera and storage
    //variables that will contain data to save in database
    private var imageUri: Uri? = null

    private lateinit var databaseProduct: DatabaseProduct
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_product)

        databaseProduct = DatabaseProduct(this)

        add_button2.setOnClickListener{
            if(name_input.text.toString() == "" || price_input.text.toString() == "" || imageUri == null){
                Toast.makeText(this, "Tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
            else {
                databaseProduct.addProduct(
                    name_input.text.toString().trim { it <= ' ' },
                    price_input.text.toString().toLong(),""+imageUri
                )
                Toast.makeText(this,"Produk berhasil ditambahkan", Toast.LENGTH_SHORT).show()

                handler = Handler()
                handler.postDelayed({
                    startActivity(Intent(this, ViewProduct::class.java))
                    finish()
                }, 1000)
            }
        }

        cameraPermissions = arrayOf(android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        storagePermissions = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        //click imageView to pick image
        image_input.setOnClickListener {
            //show image pick dialog
            imagePickDialog()
        }
        ic_camera.setOnClickListener {
            //show image pick dialog
            imagePickDialog()
        }
    }

    private fun imagePickDialog() {
        //option to display in dialog
        val options = arrayOf("Camera", "Gallery")
        //dialog
        val builder = AlertDialog.Builder(this)
        //title
        builder.setTitle("Pick Image From")
        //set items/options

        builder.setItems(options){ dialog, which ->
            //handle item clicks
            if(which == 0){
                //camera clicked
                if(!checkCameraPermissions()){
                    //permission not granted
                    requestCameraPermissions()
                }
                else{
                    //permissions already granted
                    pickFromCamera()
                }
            }
            else if(which == 1){
                //gallery clicked
                if(!checkStoragePermission()){
                    //permission not granted
                    requestStoragePermissions()
                }
                else{
                    //permission already granted
                    pickFromGallery()
                }
            }

        }
        //show dialog
        builder.show()
    }

    private fun pickFromGallery() {
        //pick image from gallery using Intent
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*" //only image to be picked
        startActivityForResult(
            galleryIntent,
            IMAGE_PICK_CAMERA_CODE
        )
    }

    private fun requestStoragePermissions() {
        //request the storage permission
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE)
    }

    private fun checkStoragePermission(): Boolean {
        //check if storage permission is enable or not
        return  ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) ==  PackageManager.PERMISSION_GRANTED
    }

    private fun pickFromCamera() {
        //pick image from camera using Intent
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "Image Title")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image Description")
        //put image url
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        //Intent to open camera
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUrl)
        startActivityForResult(
            cameraIntent,
            IMAGE_PICK_CAMERA_CODE
        )
    }

    private fun requestCameraPermissions() {
        //request the camera permissions
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE)
    }

    private fun checkCameraPermissions(): Boolean {
        //check if camera permissions (camera and storage) are enabled or not
        val results = ContextCompat.checkSelfPermission(this,
            android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        val results1 = ContextCompat.checkSelfPermission(this,
            android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

        return  results && results1
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() //go back to previous activity
        return super.onSupportNavigateUp()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()) {
                    //if allowed returns true otherwise false
                    val cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    val storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED
                    if (cameraAccepted && storageAccepted) {
                        pickFromCamera()
                    } else {
                        Toast.makeText(this, "Camera and Storage permissions are required", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            STORAGE_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()) {
                    //if allowed returns true otherwise false
                    val storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    if (storageAccepted) {
                        pickFromGallery()
                    } else {
                        Toast.makeText(this, "Storage permissions is required", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //image picked from camera or gallery wil be received here
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == IMAGE_PICK_GALLERY_CODE){
                //pick from gallery
                //crop image
                CropImage.activity(data!!.data)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this)
            }
            else if(requestCode == IMAGE_PICK_CAMERA_CODE){
                //pick from camera
                //crop image
                CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this)
            }
            else if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                //cropped image received
                val result = CropImage.getActivityResult(data)
                if(resultCode == Activity.RESULT_OK){
                    val resultUri = result.uri
                    imageUri = resultUri
                    //set image
                    image_input.setImageURI(resultUri)


                }
                else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    //error
                    val error = result.error
                    Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
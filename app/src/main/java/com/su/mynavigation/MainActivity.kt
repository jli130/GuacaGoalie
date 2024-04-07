package com.su.mynavigation

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.Manifest
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var db: DatabaseHelper
    private lateinit var userList: List<UserListModel>
    private lateinit var dashboard: Dashboard
    private lateinit var dashboardView: View
    private lateinit var mainActivityInstance: MainActivity
    private lateinit var takePictureLauncher: ActivityResultLauncher<Intent>
    private lateinit var pickFromGalleryLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_dashboard)
        dashboardView = window.decorView.rootView
        dashboard = Dashboard()
        mainActivityInstance = this
        db = DatabaseHelper(this)
        //db.deleteAll()
        //val user1 = UserListModel( "Nathan", "OMG HELLO! Howdy There! My name is John Doe! I am a thrilled user of Guacagoalie who loves travelling, having fun, and working out! I see myself using this app to help make my future more active and pleasant.\"\n", 0, 0, 0, 0, 0)
        //db.insert(user1)
        userList = db.getAllInfo()
        if (userList.isEmpty()) {
            val nextPage = Intent(this, Intro::class.java)
            startActivity(nextPage)
            finish()
        } else {


            val name = userList[0].name
            val about = userList[0].about
            val goal = userList[0].goal
            val milestone = userList[0].milestone
            val dName = findViewById<TextView>(R.id.usernameText)
            val dAbout = findViewById<TextView>(R.id.aboutMeMessageText)
            val dGoal = findViewById<TextView>(R.id.weeklyGoalNumberText)
            val dMile = findViewById<TextView>(R.id.milestoneNumberText)
            dName.text = name
            dAbout.text = about
            dGoal.text = goal.toString()
            dMile.text = milestone.toString()

        }

        val milestoneButton = findViewById<ImageButton>(R.id.milestoneGoalButton)
        val stepsTodayButton3 = findViewById<ImageButton>(R.id.stepsTodayButton3)
        val profilePhotoButton = findViewById<ImageButton>(R.id.profilePhotoButton)
        val editText = findViewById<EditText>(R.id.aboutMeMessageText)


        bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.selectedItemId = R.id.dashboard

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.dashboard -> {

                }

                R.id.rewards -> {
                    val nextPage = Intent(this, Rewards::class.java)
                    startActivity(nextPage)
                    finish()
                }

                R.id.map -> {
                    val nextPage = Intent(this, Map::class.java)
                    startActivity(nextPage)
                    finish()
                }
            }
            true
        }


        milestoneButton.setOnClickListener {
            dashboard.accessMilestone(mainActivityInstance, db)
        }

        stepsTodayButton3.setOnClickListener {
            dashboard.acessDaily(dashboardView, mainActivityInstance, db)
        }

        // Initialize the ActivityResultLauncher for taking a picture
        takePictureLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    profilePhotoButton.setImageBitmap(imageBitmap)
                }
            }

        // Initialize the ActivityResultLauncher for picking from gallery
        pickFromGalleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    val imageUri = data?.data
                    profilePhotoButton.setImageURI(imageUri)
                }
            }

        profilePhotoButton.setOnClickListener {
            showChangeProfilePhotoDialog()
        }

        // These two listeners are for changing the about me text.
        // Hide keyboard when user clicks outside EditText
        editText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(editText.windowToken, 0)
            }
        }

        // Show keyboard and enable editing when EditText is clicked
        editText.setOnClickListener {
            editText.isFocusableInTouchMode = true
            editText.requestFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        }



    }

    private fun showChangeProfilePhotoDialog() {
        val options = arrayOf("Take Photo", "Choose from Gallery", "Cancel")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Change Profile Photo")
        builder.setItems(options) { dialog, which ->
            when (which) {
                0 -> {
                    // This is for taking pictures.

                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED
                    ) {
                        // If it's the first time launching GuacaGoalie or we don't have permission
                        // to use the camera, ask for permission here.
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.CAMERA),
                            100
                        )
                    } else {
                        // Permission has already been granted, proceed with launching camera
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        takePictureLauncher.launch(intent)
                    }
                }

                1 -> {
                    // This is for picking images from the gallery for the profile photo.
                    val intent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    pickFromGalleryLauncher.launch(intent)
                }

                2 -> dialog.dismiss()
            }
        }
        builder.show()
    }

    /**
     * This is for the user profile photo button, if they want to change their profile photo.
     * This function handles the user's input for camera permission request.
     * Does the user grant us camera permission or deny? Handles logic here.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission granted, proceed with launching camera
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                takePictureLauncher.launch(intent)
            } else {
                // Camera permission denied, handle accordingly
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }


}
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
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.util.Log
import android.widget.Button
import android.widget.Toast


class MainActivity : AppCompatActivity(), SensorEventListener{

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var db: DatabaseHelper
    private lateinit var userList: List<UserListModel>
    private lateinit var dashboard: Dashboard
    private lateinit var dashboardView: View
    private lateinit var mainActivityInstance: MainActivity
    private lateinit var takePictureLauncher: ActivityResultLauncher<Intent>
    private lateinit var pickFromGalleryLauncher: ActivityResultLauncher<Intent>
    private var sensorManager: SensorManager? = null
    private var totalSteps = 0f
    companion object{
        private const val ACTIVITY_RECOGNITION_REQUEST_CODE = 1
    }
    // Creating a variable which will give the running status
    // and initially given the boolean value as false
    private var running = false

    // Creating a variable which will counts total steps
    // and it has been given the value of 0 float


    // Creating a variable  which counts previous total
    // steps and it has also been given the value of 0 float
    private var previousTotalSteps = 0f


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_dashboard)

        dashboardView = window.decorView.rootView
        dashboard = Dashboard()
        mainActivityInstance = this
        db = DatabaseHelper(this)
        val editButton = findViewById<Button>(R.id.button2)
        loadData()
        resetSteps()
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
            val dsteps = findViewById<TextView>(R.id.stepsNumberText)
            dName.text = name
            dAbout.text = about
            dGoal.text = goal.toString()
            dMile.text = milestone.toString()
            dsteps.text = userList[0].steps.toString()
        }


        editButton.setOnClickListener(){
            val nextPage = Intent(this, Edit::class.java)
            startActivity(nextPage)
            finish()
        }

        // Adding a context of SENSOR_SERVICE as Sensor Manager
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val milestoneButton = findViewById<ImageButton>(R.id.milestoneGoalButton)
        val stepsTodayButton3 = findViewById<ImageButton>(R.id.stepsTodayButton3)
        val profilePhotoButton = findViewById<ImageButton>(R.id.profilePhotoButton)

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


    }




    private fun showChangeProfilePhotoDialog() {
        val options = arrayOf("Take Photo", "Choose from Gallery", "Cancel")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Change Profile Photo")
        builder.setItems(options) { dialog, which ->
            when (which) {
                0 -> {
                    // This is for talking pictures.

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



    override fun onResume() {
        super.onResume()
        running = true

        // Returns the number of steps taken by the user since the last reboot while activated
        // This sensor requires permission android.permission.ACTIVITY_RECOGNITION.
        // So don't forget to add the following permission in AndroidManifest.xml present in manifest folder of the app.
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)


        if (stepSensor == null) {
            // This will give a toast message to the user if there is no sensor in the device
            Toast.makeText(this, "No sensor detected on this device", Toast.LENGTH_SHORT).show()
        } else {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                    ACTIVITY_RECOGNITION_REQUEST_CODE)

            }
            // Rate suitable for the user interface
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {

        // Calling the TextView that we made in activity_main.xml
        // by the id given to that TextView
        var tv_stepsTaken = findViewById<TextView>(R.id.stepsNumberText)

        if (running) {
            totalSteps = event!!.values[0]

            // Current steps are calculated by taking the difference of total steps
            // and previous steps
            val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()

            // It will show the current steps to the user
            tv_stepsTaken.text = ("$currentSteps")
            if(currentSteps % userList[0].milestone == 0 && currentSteps != 0){
                Toast.makeText(this, "You have reached a mileStone", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun resetSteps() {
        var tv_stepsTaken = findViewById<TextView>(R.id.stepsNumberText)
        tv_stepsTaken.setOnClickListener {
            // This will give a toast message if the user want to reset the steps
            Toast.makeText(this, "Long tap to reset steps", Toast.LENGTH_SHORT).show()
        }

        tv_stepsTaken.setOnLongClickListener {

            previousTotalSteps = totalSteps
            // When the user will click long tap on the screen,
            // the steps will be reset to 0
            tv_stepsTaken.text = 0.toString()

            // This will save the data
            saveData()

            true
        }
    }

    private fun saveData() {

        // Shared Preferences will allow us to save
        // and retrieve data in the form of key,value pair.
        // In this function we will save data
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putFloat("key1", previousTotalSteps)
        editor.putFloat("key2", totalSteps)
        editor.apply()
    }

    private fun loadData() {

        // In this function we will retrieve data
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val savedNumber = sharedPreferences.getFloat("key1", 0f)
        val savedNumbner2 =  sharedPreferences.getFloat("key2", 0f)

        // Log.d is used for debugging purposes
        Log.d("MainActivity", "$savedNumber")

        previousTotalSteps = savedNumber
        totalSteps = savedNumbner2
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // We do not have to write anything in this function for this app
    }


}
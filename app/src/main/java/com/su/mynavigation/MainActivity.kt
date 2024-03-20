package com.su.mynavigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav : BottomNavigationView
    private lateinit var db : DatabaseHelper
    private lateinit var userList : List<UserListModel>
    private lateinit var dashboard: Dashboard
    private lateinit var dashboardView: View
    private lateinit var mainActivityInstance: MainActivity
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
        if(userList.isEmpty()){
            val nextPage = Intent(this, Intro::class.java)
            startActivity(nextPage)
            finish()
        }
        else{


            val name = userList[0].name
            val about = userList[0].about
            val goal = userList[0].goal
            val dName = findViewById<TextView>(R.id.usernameText)
            val dAbout = findViewById<TextView>(R.id.aboutMeMessageText)
            val dGoal = findViewById<TextView>(R.id.weeklyGoalNumberText)
            dName.text = name
            dAbout.text = about
            dGoal.text = goal.toString()


        }

        val milestoneButton = findViewById<ImageButton>(R.id.miltstoneGoalButton)
        val imageButton = findViewById<ImageButton>(R.id.stepsTodayButton3)

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


        milestoneButton.setOnClickListener{
            dashboard.accessMilestone(mainActivityInstance)
        }

        imageButton.setOnClickListener {
            dashboard.acessDaily(dashboardView, mainActivityInstance)
        }


    }


}
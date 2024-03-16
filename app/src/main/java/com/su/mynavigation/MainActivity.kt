package com.su.mynavigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav : BottomNavigationView
    private lateinit var db : DatabaseHelper
    private lateinit var userList : List<UserListModel>
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_dashboard)

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




        bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.selectedItemId = R.id.dashboard

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.dashboard -> {
                }
                R.id.rewards -> {
                }
                R.id.map -> {
                    val nextPage = Intent(this, Map::class.java)
                    startActivity(nextPage)
                    finish()
                }
            }
            true
        }
    }




}

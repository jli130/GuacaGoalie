package com.su.mynavigation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import kotlin.collections.Map

class Intro: AppCompatActivity() {

    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.intro)
        val Bt = findViewById<Button>(R.id.button)
        db = DatabaseHelper(this)
        Bt.setOnClickListener{
            val name = findViewById<EditText>(R.id.nameEnter).text.toString()
            val about = findViewById<EditText>(R.id.AboutEnter).text.toString()
            val goal = Integer.parseInt(findViewById<TextInputLayout>(R.id.GoalEnter).editText?.text.toString())
            val milestone = Integer.parseInt(findViewById<TextInputLayout>(R.id.mileGoalEnter).editText?.text.toString())
            val user = UserListModel(name, about, 0, goal, 0, 0, milestone)
            db.insert(user)
            val nextPage = Intent(this, MainActivity::class.java)
            startActivity(nextPage)
            finish()
        }


    }

}
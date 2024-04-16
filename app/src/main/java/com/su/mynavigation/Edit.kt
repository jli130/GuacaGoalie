package com.su.mynavigation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class Edit: AppCompatActivity() {
    private lateinit var db: DatabaseHelper
    private lateinit var userList: List<UserListModel>
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.intro)
        db = DatabaseHelper(this)
        userList = db.getAllInfo()
        val name = userList[0].name
        val about = userList[0].about
        val goal = userList[0].goal
        val milestone = userList[0].milestone
        val Bt = findViewById<Button>(R.id.button)
        val oname = findViewById<EditText>(R.id.nameEnter)
        val oabout = findViewById<EditText>(R.id.AboutEnter)
        val ogoal = findViewById<TextInputLayout>(R.id.GoalEnter)
        val omilestone = findViewById<TextInputLayout>(R.id.mileGoalEnter)

        oname.setText(name)
        oabout.setText(about)
        ogoal.editText?.setText(goal.toString())
        omilestone.editText?.setText(milestone.toString())

        Bt.setOnClickListener{
            val nname = oname.text.toString()
            val nabout = oabout.text.toString()
            val ngoal = Integer.parseInt(ogoal.editText?.text.toString())
            val nmilestone = Integer.parseInt(omilestone.editText?.text.toString())
            db.updateUserName(nname, userList[0])
            db.updateAbout(nabout, userList[0])
            db.updateGoal(ngoal, userList[0])
            db.updateMileStone(nmilestone, userList[0])
            val nextPage = Intent(this, MainActivity::class.java)
            startActivity(nextPage)
            finish()
        }


    }

}
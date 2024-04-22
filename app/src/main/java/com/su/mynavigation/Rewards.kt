package com.su.mynavigation

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class Rewards : AppCompatActivity(){

    private lateinit var bottomNav3 : BottomNavigationView
    private lateinit var db : DatabaseHelper
    private lateinit var userList : List<UserListModel>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_rewards)
        db = DatabaseHelper(this)
        val username = findViewById<TextView>(R.id.usernameTextView)
        val points = findViewById<TextView>(R.id.textView3)
        userList = db.getAllInfo()
        username.text = userList[0].name
        points.text = userList[0].rewards.toString()


        val r1 = findViewById<ImageButton>(R.id.reward1)
        val r2 = findViewById<ImageButton>(R.id.reward2)
        val r3 = findViewById<ImageButton>(R.id.reward3)
        val r4 = findViewById<ImageButton>(R.id.reward4)
        val r5 = findViewById<ImageButton>(R.id.reward5)
        val r6 = findViewById<ImageButton>(R.id.reward6)
        val r7 = findViewById<ImageButton>(R.id.reward7)
        val r8 = findViewById<ImageButton>(R.id.reward8)
        val r9 = findViewById<ImageButton>(R.id.reward9)
        val r10 = findViewById<ImageButton>(R.id.reward10)
        val r11 = findViewById<ImageButton>(R.id.reward11)
        val r12 = findViewById<ImageButton>(R.id.reward12)
        val r13 = findViewById<ImageButton>(R.id.reward13)
        val r14 = findViewById<ImageButton>(R.id.reward14)
        val r15 = findViewById<ImageButton>(R.id.reward15)
        val r16 = findViewById<ImageButton>(R.id.reward16)
        val r17 = findViewById<ImageButton>(R.id.reward17)
        val r18 = findViewById<ImageButton>(R.id.reward18)
        val r19 = findViewById<ImageButton>(R.id.reward19)
        val r20 = findViewById<ImageButton>(R.id.reward20)
        val r21 = findViewById<ImageButton>(R.id.reward21)
        val r22 = findViewById<ImageButton>(R.id.reward22)
        val r23 = findViewById<ImageButton>(R.id.reward23)
        val r24 = findViewById<ImageButton>(R.id.reward24)
        val r25 = findViewById<ImageButton>(R.id.reward25)



        val t1 = findViewById<TextView>(R.id.reward1texttop)
        val t2 = findViewById<TextView>(R.id.reward2texttop)
        val t3 = findViewById<TextView>(R.id.reward3texttop)
        val t4 = findViewById<TextView>(R.id.reward4texttop)
        val t5 = findViewById<TextView>(R.id.reward5texttop)
        val t6 = findViewById<TextView>(R.id.reward6texttop)
        val t7 = findViewById<TextView>(R.id.reward7texttop)
        val t8 = findViewById<TextView>(R.id.reward8texttop)
        val t9 = findViewById<TextView>(R.id.reward9texttop)
        val t10 = findViewById<TextView>(R.id.reward10texttop)
        val t11 = findViewById<TextView>(R.id.reward11texttop)
        val t12 = findViewById<TextView>(R.id.reward12texttop)
        val t13 = findViewById<TextView>(R.id.reward13texttop)
        val t14 = findViewById<TextView>(R.id.reward14texttop)
        val t15 = findViewById<TextView>(R.id.reward15texttop)
        val t16 = findViewById<TextView>(R.id.reward16texttop)
        val t17 = findViewById<TextView>(R.id.reward17texttop)
        val t18 = findViewById<TextView>(R.id.reward18texttop)
        val t19 = findViewById<TextView>(R.id.reward19texttop)
        val t20 = findViewById<TextView>(R.id.reward20texttop)
        val t21 = findViewById<TextView>(R.id.reward21texttop)
        val t22 = findViewById<TextView>(R.id.reward22texttop)
        val t23 = findViewById<TextView>(R.id.reward23texttop)
        val t24 = findViewById<TextView>(R.id.reward24texttop)
        val t25 = findViewById<TextView>(R.id.reward25texttop)



        r1.setOnClickListener(){
            if(userList[0].rewards >= 10){
                val temp = userList[0].rewards-10
                db.updateRewards(temp, userList[0])
                points.text = temp.toString()
                t1.text = "Bought"
                userList = db.getAllInfo()
            }
            else{
                Toast.makeText(this, "You do not have enough points", Toast.LENGTH_SHORT).show()
            }
        }

        r2.setOnClickListener(){
            if(userList[0].rewards >= 20){
                val temp = userList[0].rewards-20
                db.updateRewards(temp, userList[0])
                points.text = temp.toString()
                t2.text = "Bought"
                userList = db.getAllInfo()
            }
            else{
                Toast.makeText(this, "You do not have enough points", Toast.LENGTH_SHORT).show()
            }
        }
        r3.setOnClickListener(){
            if(userList[0].rewards >= 50){
                val temp = userList[0].rewards-50
                db.updateRewards(temp, userList[0])
                points.text = temp.toString()
                t3.text = "Bought"
                userList = db.getAllInfo()
            }
            else{
                Toast.makeText(this, "You do not have enough points", Toast.LENGTH_SHORT).show()
            }
        }
        r4.setOnClickListener(){
            if(userList[0].rewards >= 100){
                val temp = userList[0].rewards-100
                db.updateRewards(temp, userList[0])
                points.text = temp.toString()
                t4.text = "Bought"
                userList = db.getAllInfo()
            }
            else{
                Toast.makeText(this, "You do not have enough points", Toast.LENGTH_SHORT).show()
            }
        }
        r5.setOnClickListener(){
            if(userList[0].rewards >= 10){
                val temp = userList[0].rewards-10
                db.updateRewards(temp, userList[0])
                points.text = temp.toString()
                t5.text = "Bought"
                userList = db.getAllInfo()
            }
            else{
                Toast.makeText(this, "You do not have enough points", Toast.LENGTH_SHORT).show()
            }
        }
        r6.setOnClickListener(){
            if(userList[0].rewards >= 110){
                val temp = userList[0].rewards-110
                db.updateRewards(temp, userList[0])
                points.text = temp.toString()
                t6.text = "Bought"
                userList = db.getAllInfo()
            }
            else{
                Toast.makeText(this, "You do not have enough points", Toast.LENGTH_SHORT).show()
            }
        }
        r7.setOnClickListener(){
            if(userList[0].rewards >= 120){
                val temp = userList[0].rewards-120
                db.updateRewards(temp, userList[0])
                points.text = temp.toString()
                t7.text = "Bought"
                userList = db.getAllInfo()
            }
            else{
                Toast.makeText(this, "You do not have enough points", Toast.LENGTH_SHORT).show()
            }
        }

        r8.setOnClickListener(){
            if(userList[0].rewards >= 130){
                val temp = userList[0].rewards-130
                db.updateRewards(temp, userList[0])
                points.text = temp.toString()
                t8.text = "Bought"
                userList = db.getAllInfo()
            }
            else{
                Toast.makeText(this, "You do not have enough points", Toast.LENGTH_SHORT).show()
            }
        }
        r9.setOnClickListener(){
            if(userList[0].rewards >= 150){
                val temp = userList[0].rewards-150
                db.updateRewards(temp, userList[0])
                points.text = temp.toString()
                t9.text = "Bought"
                userList = db.getAllInfo()
            }
            else{
                Toast.makeText(this, "You do not have enough points", Toast.LENGTH_SHORT).show()
            }
        }
        r10.setOnClickListener(){
            if(userList[0].rewards >= 100){
                val temp = userList[0].rewards-100
                db.updateRewards(temp, userList[0])
                points.text = temp.toString()
                t10.text = "Bought"
                userList = db.getAllInfo()
            }
            else{
                Toast.makeText(this, "You do not have enough points", Toast.LENGTH_SHORT).show()
            }
        }
        r11.setOnClickListener(){
            if(userList[0].rewards >= 200){
                val temp = userList[0].rewards-200
                db.updateRewards(temp, userList[0])
                points.text = temp.toString()
                t11.text = "Bought"
                userList = db.getAllInfo()
            }
            else{
                Toast.makeText(this, "You do not have enough points", Toast.LENGTH_SHORT).show()
            }
        }
        r12.setOnClickListener(){
            if(userList[0].rewards >= 220){
                val temp = userList[0].rewards-220
                db.updateRewards(temp, userList[0])
                points.text = temp.toString()
                t12.text = "Bought"
                userList = db.getAllInfo()
            }
            else{
                Toast.makeText(this, "You do not have enough points", Toast.LENGTH_SHORT).show()
            }
        }
        r13.setOnClickListener(){
            if(userList[0].rewards >= 240){
                val temp = userList[0].rewards-240
                db.updateRewards(temp, userList[0])
                points.text = temp.toString()
                t13.text = "Bought"
                userList = db.getAllInfo()
            }
            else{
                Toast.makeText(this, "You do not have enough points", Toast.LENGTH_SHORT).show()
            }
        }

        r14.setOnClickListener(){
            if(userList[0].rewards >= 260){
                val temp = userList[0].rewards-260
                db.updateRewards(temp, userList[0])
                points.text = temp.toString()
                t14.text = "Bought"
                userList = db.getAllInfo()
            }
            else{
                Toast.makeText(this, "You do not have enough points", Toast.LENGTH_SHORT).show()
            }
        }
        r15.setOnClickListener(){
            if(userList[0].rewards >= 300){
                val temp = userList[0].rewards-300
                db.updateRewards(temp, userList[0])
                points.text = temp.toString()
                t15.text = "Bought"
                userList = db.getAllInfo()
            }
            else{
                Toast.makeText(this, "You do not have enough points", Toast.LENGTH_SHORT).show()
            }
        }
        r16.setOnClickListener(){
            if(userList[0].rewards >= 350){
                val temp = userList[0].rewards-350
                db.updateRewards(temp, userList[0])
                points.text = temp.toString()
                t16.text = "Bought"
                userList = db.getAllInfo()
            }
            else{
                Toast.makeText(this, "You do not have enough points", Toast.LENGTH_SHORT).show()
            }
        }

        r17.setOnClickListener(){
            if(userList[0].rewards >= 400){
                val temp = userList[0].rewards-400
                db.updateRewards(temp, userList[0])
                points.text = temp.toString()
                t17.text = "Bought"
                userList = db.getAllInfo()
            }
            else{
                Toast.makeText(this, "You do not have enough points", Toast.LENGTH_SHORT).show()
            }
        }
        r18.setOnClickListener(){
            if(userList[0].rewards >= 500){
                val temp = userList[0].rewards-500
                db.updateRewards(temp, userList[0])
                points.text = temp.toString()
                t18.text = "Bought"
                userList = db.getAllInfo()
            }
            else{
                Toast.makeText(this, "You do not have enough points", Toast.LENGTH_SHORT).show()
            }
        }
        r19.setOnClickListener(){
            if(userList[0].rewards >= 500){
                val temp = userList[0].rewards-500
                db.updateRewards(temp, userList[0])
                points.text = temp.toString()
                t19.text = "Bought"
                userList = db.getAllInfo()
            }
            else{
                Toast.makeText(this, "You do not have enough points", Toast.LENGTH_SHORT).show()
            }
        }

        r20.setOnClickListener(){
            if(userList[0].rewards >= 500){
                val temp = userList[0].rewards-500
                db.updateRewards(temp, userList[0])
                points.text = temp.toString()
                t20.text = "Bought"
                userList = db.getAllInfo()
            }
            else{
                Toast.makeText(this, "You do not have enough points", Toast.LENGTH_SHORT).show()
            }
        }
        r21.setOnClickListener(){
            if(userList[0].rewards >= 800){
                val temp = userList[0].rewards-800
                db.updateRewards(temp, userList[0])
                points.text = temp.toString()
                t21.text = "Bought"
                userList = db.getAllInfo()
            }
            else{
                Toast.makeText(this, "You do not have enough points", Toast.LENGTH_SHORT).show()
            }
        }
        r22.setOnClickListener(){
            if(userList[0].rewards >= 1200){
                val temp = userList[0].rewards-1200
                db.updateRewards(temp, userList[0])
                points.text = temp.toString()
                t22.text = "Bought"
                userList = db.getAllInfo()
            }
            else{
                Toast.makeText(this, "You do not have enough points", Toast.LENGTH_SHORT).show()
            }
        }
        r23.setOnClickListener(){
            if(userList[0].rewards >= 1800){
                val temp = userList[0].rewards-1800
                db.updateRewards(temp, userList[0])
                points.text = temp.toString()
                t23.text = "Bought"
                userList = db.getAllInfo()
            }
            else{
                Toast.makeText(this, "You do not have enough points", Toast.LENGTH_SHORT).show()
            }
        }
        r24.setOnClickListener(){
            if(userList[0].rewards >= 2000){
                val temp = userList[0].rewards-2000
                db.updateRewards(temp, userList[0])
                points.text = temp.toString()
                t24.text = "Bought"
                userList = db.getAllInfo()
            }
            else{
                Toast.makeText(this, "You do not have enough points", Toast.LENGTH_SHORT).show()
            }
        }
        r25.setOnClickListener(){
            if(userList[0].rewards >= 3000){
                val temp = userList[0].rewards-3000
                db.updateRewards(temp, userList[0])
                points.text = temp.toString()
                t25.text = "Bought"
                userList = db.getAllInfo()
            }
            else{
                Toast.makeText(this, "You do not have enough points", Toast.LENGTH_SHORT).show()
            }
        }

        bottomNav3 = findViewById<BottomNavigationView>(R.id.bottomNav3)
        bottomNav3.selectedItemId = R.id.rewards

        bottomNav3.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.dashboard -> {
                    val nextPage = Intent(this, MainActivity::class.java)
                    startActivity(nextPage)
                    finish()
                }
                R.id.rewards -> {

                }
                R.id.map -> {
                    val nextPage = Intent(this, com.su.mynavigation.Map::class.java)
                    startActivity(nextPage)
                    finish()
                }
            }
            true
        }



    }
}

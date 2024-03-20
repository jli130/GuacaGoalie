package com.su.mynavigation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.collections.Map

class Rewards : AppCompatActivity(){

    private lateinit var bottomNav3 : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_rewards)


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

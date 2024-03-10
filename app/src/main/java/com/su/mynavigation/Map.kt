package com.su.mynavigation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.collections.Map


class Map : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var bottomNav2 : BottomNavigationView
    private var map : GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_map)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)


        bottomNav2 = findViewById<BottomNavigationView>(R.id.bottomNav2)
        bottomNav2.selectedItemId = R.id.map
        bottomNav2.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.dashboard -> {
                    val nextPage = Intent(this, MainActivity::class.java)
                    startActivity(nextPage)
                    finish()
                }
                R.id.rewards -> {

                }
                R.id.map -> {

                }
            }
            true
        }


    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
    }

}
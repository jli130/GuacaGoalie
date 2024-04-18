package com.su.mynavigation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView


class Map : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private lateinit var lastLocation : Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var bottomNav2 : BottomNavigationView
    private var map : GoogleMap? = null
    private lateinit var latLangDB : LatlngDB
    private lateinit var llList: List<latlng>

    companion object{
        private val LOCATION_REQUEST_CODE = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_map)
        latLangDB = LatlngDB(this)
        llList = latLangDB.getAllInfo()

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)



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
                    val nextPage = Intent(this, Rewards::class.java)
                    startActivity(nextPage)
                    finish()
                }
                R.id.map -> {

                }
            }
            true
        }


    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
        map!!.uiSettings.isZoomControlsEnabled = true
        map!!.setOnMarkerClickListener(this)
        SetUpMap()

    }

    private fun SetUpMap(){
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)

            return
        }
        map?.isMyLocationEnabled = true

        fusedLocationProviderClient.lastLocation.addOnSuccessListener(this) {location->
            if(location != null){
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                map?.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }
        if(llList.isNotEmpty()){
            for(i in llList){
                PlaceMarkeronMap(i)
            }

        }
    }

    private fun PlaceMarkeronMap(currentLatLng: latlng){
        val temp = LatLng(currentLatLng.Lat, currentLatLng.Lng)
        val markerOption = MarkerOptions().position(temp)
        markerOption.title("$currentLatLng")
        map?.addMarker(markerOption)

    }
    override fun onMarkerClick(p0: Marker) = false



}
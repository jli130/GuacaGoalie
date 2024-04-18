package com.su.mynavigation

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class LatlngDB (context : Context) : SQLiteOpenHelper(context,
    DB_Name, null,
    DB_Version
){
    companion object{
        val DB_Name = "LLDB"
        val DB_Version = 1
        val TableName = "LatLng"
        val Col_ID = "ID"
        val Col_Lat = "Lat"
        val Col_Lng = "Lng"

    }
    override fun onCreate(p0: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TableName ($Col_ID INTEGER, $Col_Lat DOUBLE, $Col_Lng DOUBLE)"

        p0?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val dropTable = "DROP TABLE IF EXISTS $TableName"
        p0?.execSQL(dropTable)
        onCreate(p0)
    }

    fun insert(ll:latlng){
        val db = writableDatabase
        val values = ContentValues().apply{

            put(Col_ID, ll.ID)
            put(Col_Lat, ll.Lat)
            put(Col_Lng, ll.Lng)
        }
        db.insert(TableName, null, values)
        db.close()

    }

    fun getAllInfo():List<latlng>{
        val infoList = mutableListOf<latlng>()
        val db = readableDatabase
        val query = "SELECT * FROM $TableName"

        val cursor = db.rawQuery(query, null)
        while(cursor.moveToNext()){
            val ID = cursor.getInt(cursor.getColumnIndexOrThrow(Col_ID))
            val Lat = cursor.getDouble(cursor.getColumnIndexOrThrow(Col_Lat))
            val Lng = cursor.getDouble(cursor.getColumnIndexOrThrow(Col_Lng))


            val LatLng = latlng(Lat, Lng, ID)
            infoList.add(LatLng)
        }
        cursor.close()
        db.close()
        return infoList
    }
    fun deleteAll() {
        val db = writableDatabase
        // db.delete(TABLE_NAME,null,null);
        //db.execSQL("delete * from"+ TABLE_NAME);
        db.execSQL("delete from $TableName");
        db.close()
    }

}
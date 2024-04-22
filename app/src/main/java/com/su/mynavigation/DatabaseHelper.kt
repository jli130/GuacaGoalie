package com.su.mynavigation

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper (context : Context) : SQLiteOpenHelper(context, DB_Name, null, DB_Version){
    companion object{
        val DB_Name = "DB"
        val DB_Version = 1
        val TableName = "DashBoard"
        val Col_Name = "name"
        val Col_Steps = "steps"
        val Col_Goal = "goal"
        val Col_About = "about"
        val Col_Progress = "progress"
        val Col_Rewards = "rewards"
        val Col_MileStone = "mileStone"
    }


    override fun onCreate(p0: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TableName ($Col_Name TEXT, $Col_About TEXT, $Col_Steps INTEGER, $Col_Goal INTEGER, $Col_Progress INTEGER, $Col_Rewards INTEGER, $Col_MileStone INTEGER)"

        p0?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, old: Int, new: Int) {
        val dropTable = "DROP TABLE IF EXISTS $TableName"
        p0?.execSQL(dropTable)
        onCreate(p0)
    }

    fun insert(user:UserListModel){
        val db = writableDatabase
        val values = ContentValues().apply{

            put(Col_Name, user.name)
            put(Col_About, user.about)
            put(Col_Steps, user.steps)
            put(Col_Goal, user.goal)
            put(Col_Progress, user.progress)
            put(Col_Rewards, user.rewards)
            put(Col_MileStone, user.milestone)
        }
        db.insert(TableName, null, values)
        db.close()

    }

    fun getAllInfo():List<UserListModel>{
        val infoList = mutableListOf<UserListModel>()
        val db = readableDatabase
        val query = "SELECT * FROM $TableName"

        val cursor = db.rawQuery(query, null)
        while(cursor.moveToNext()){
            val name = cursor.getString(cursor.getColumnIndexOrThrow(Col_Name))
            val about = cursor.getString(cursor.getColumnIndexOrThrow(Col_About))
            val steps = cursor.getInt(cursor.getColumnIndexOrThrow(Col_Steps))
            val goal = cursor.getInt(cursor.getColumnIndexOrThrow(Col_Goal))
            val progress = cursor.getInt(cursor.getColumnIndexOrThrow(Col_Progress))
            val rewards = cursor.getInt(cursor.getColumnIndexOrThrow(Col_Rewards))
            val milestone = cursor.getInt(cursor.getColumnIndexOrThrow(Col_MileStone))

            val userListModel = UserListModel(name, about, steps, goal, progress, rewards, milestone)
            infoList.add(userListModel)
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


    fun updateUserName(name:String, user:UserListModel){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(Col_Name, name)
        }
        val whereClause = "$Col_Name = ?"
        val whereArgs = arrayOf(user.name)
        db.update(TableName, values, whereClause, whereArgs)
        db.close()

    }

    fun updateAbout(about:String, user:UserListModel){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(Col_About, about)
        }
        val whereClause = "$Col_About = ?"
        val whereArgs = arrayOf(user.about)
        db.update(TableName, values, whereClause, whereArgs)
        db.close()

    }
    fun updateSteps(steps:Int, user:UserListModel){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(Col_Steps, steps)
        }
        val whereClause = "$Col_Name = ?"
        val whereArgs = arrayOf(user.name)
        db.update(TableName, values, whereClause, whereArgs)
        db.close()

    }
    fun updateMileStone(ms:Int, user:UserListModel){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(Col_MileStone, ms)
        }
        val whereClause = "$Col_MileStone = ?"
        val whereArgs = arrayOf(user.milestone.toString())
        db.update(TableName, values, whereClause, whereArgs)
        db.close()

    }
    fun updateRewards(rewards:Int, user:UserListModel){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(Col_Rewards, rewards)
        }
        val whereClause = "$Col_Rewards = ?"
        val whereArgs = arrayOf(user.rewards.toString())
        db.update(TableName, values, whereClause, whereArgs)
        db.close()

    }

    fun updateProgress(progress:Int, user:UserListModel){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(Col_Progress, progress)
        }
        val whereClause = "$Col_Name = ?"
        val whereArgs = arrayOf(user.name)
        db.update(TableName, values, whereClause, whereArgs)
        db.close()

    }

    fun updateGoal(goal:Int, user:UserListModel){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(Col_Goal, goal)
        }
        val whereClause = "$Col_Goal = ?"
        val whereArgs = arrayOf(user.goal.toString())
        db.update(TableName, values, whereClause, whereArgs)
        db.close()

    }


}
package com.example.tubes

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseUser(var context: Context) : SQLiteOpenHelper(context,
        DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableUser = ("CREATE TABLE " + TABLE_USER_NAME + "("
                + COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_USER_NAME + " TEXT,"
                + COL_USER_EMAIL + " TEXT,"
                + COL_USER_PASSWORD + " TEXT,"
                + COL_USER_IMAGE + " TEXT"
                + ")")
        db?.execSQL(createTableUser)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableUser = "DROP TABLE IF EXISTS $TABLE_USER_NAME"
        //Drop User Table if exist
        db?.execSQL(dropTableUser)
        // Create tables again
        onCreate(db)
    }

    fun addUser(user: User){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_USER_NAME, user.username)
        values.put(COL_USER_EMAIL, user.email)
        values.put(COL_USER_PASSWORD, user.password)
        values.put(COL_USER_IMAGE, user.image)

        db.insert(TABLE_USER_NAME, null, values)
        db.close()
    }

    fun checkUser(email: String): Boolean {
        // array of columns to fetch
        val columns = arrayOf(COL_USER_ID)
        val db = this.readableDatabase
        // selection criteria
        val selection = "$COL_USER_EMAIL = ?"
        // selection argument
        val selectionArgs = arrayOf(email)
        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        val cursor = db.query(
                TABLE_USER_NAME, //Table to query
                columns,        //columns to return
                selection,      //columns for the WHERE clause
                selectionArgs,  //The values for the WHERE clause
                null,  //group the rows
                null,   //filter by row groups
                null)  //The sort order

        val cursorCount = cursor.count
        cursor.close()
        db.close()
        if (cursorCount > 0) {
            return true
        }
        return false
    }

    fun checkUser(email: String, password: String): Boolean {
        // array of columns to fetch
        val columns = arrayOf(COL_USER_ID)
        val db = this.readableDatabase
        // selection criteria
        val selection = "$COL_USER_EMAIL = ? AND $COL_USER_PASSWORD = ?"
        // selection arguments
        val selectionArgs = arrayOf(email, password)
        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        val cursor = db.query(
                TABLE_USER_NAME, //Table to query
                columns, //columns to return
                selection, //columns for the WHERE clause
                selectionArgs, //The values for the WHERE clause
                null,  //group the rows
                null, //filter by row groups
                null) //The sort order

        val cursorCount = cursor.count
        cursor.close()
        db.close()
        if (cursorCount > 0)
            return true
        return false
    }

    fun readUser(): MutableList<User>{
        val list: MutableList<User> = ArrayList()

        val db = this.readableDatabase
        val query = "Select * from $TABLE_USER_NAME"
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()){
            do{
                val user = User()
                user.id = result.getString(result.getColumnIndex(COL_USER_ID)).toInt()
                user.username = result.getString(result.getColumnIndex(COL_USER_NAME))
                user.email = result.getString(result.getColumnIndex(COL_USER_EMAIL))
                user.password = result.getString(result.getColumnIndex(COL_USER_PASSWORD))
                user.image = result.getString(result.getColumnIndex(COL_USER_IMAGE))
                list.add(user)
            } while (result.moveToNext())
        }

        result.close()
        db.close()
        return  list
    }

    fun updateUser(username: String, email: String, password: String, image: String) : Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_USER_NAME, username)
        contentValues.put(COL_USER_PASSWORD, password)
        contentValues.put(COL_USER_IMAGE, image)
        db.update(TABLE_USER_NAME, contentValues, "user_email = ?", arrayOf(email))
        return true
    }

    //    fun deleteUser(user: User) {
//        val db = this.writableDatabase
//        // delete user record by id
//        db.delete(TABLE_NAME, "$COL_ID = ?",
//            arrayOf(user.id.toString()))
//        db.close()
//    }

    companion object {
        // Database Version
        private const val DATABASE_VERSION = 1
        // Database Name
        private const val DATABASE_NAME = "UserDB.db"
        // User table name
        private const val TABLE_USER_NAME = "User"
        // User Table Columns names
        private const val COL_USER_ID = "user_id"
        private const val COL_USER_NAME = "user_name"
        private const val COL_USER_EMAIL = "user_email"
        private const val COL_USER_PASSWORD = "user_password"
        private const val COL_USER_IMAGE = "user_image"
    }
}
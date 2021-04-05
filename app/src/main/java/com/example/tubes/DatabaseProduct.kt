package com.example.tubes

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DatabaseProduct(var context: Context) : SQLiteOpenHelper( context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableProduct = ("CREATE TABLE " +TABLE_PRODUCT_NAME + "("
                + COL_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_PRODUCT_NAME + " TEXT,"
                + COL_PRODUCT_PRICE + " DOUBLE DEFAULT 0,"
                + COL_PRODUCT_IMAGE + " TEXT"
                + ")")
        db?.execSQL(createTableProduct)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableProduct = "DROP TABLE IF EXISTS $TABLE_PRODUCT_NAME"
        db?.execSQL(dropTableProduct)
        onCreate(db)
    }

    fun addProduct(name: String, price: Long, image: String) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_PRODUCT_NAME, name)
        cv.put(COL_PRODUCT_PRICE, price)
        cv.put(COL_PRODUCT_IMAGE, image)
        val result = db.insert(TABLE_PRODUCT_NAME, null, cv)
        if (result == -1L) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show()
        }
    }

    fun readProduct(): Cursor? {
        val query = "SELECT * FROM $TABLE_PRODUCT_NAME"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        if (db != null) {
            cursor = db.rawQuery(query, null)
        }
        return cursor
    }

    fun updateProduct(id: String, name: String?, price: String, image: String?) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_PRODUCT_NAME, name)
        cv.put(COL_PRODUCT_PRICE, price)
        cv.put(COL_PRODUCT_IMAGE, image)
        val result = db.update(TABLE_PRODUCT_NAME, cv, "product_id=?", arrayOf(id)).toLong()
        if (result == -1L) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteProduct(id: String) {
        val db = this.writableDatabase
        val result = db.delete(TABLE_PRODUCT_NAME, "product_id=?", arrayOf(id)).toLong()
        if (result == -1L) {
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show()
        }
    }


    companion object {
        private const val DATABASE_NAME = "ProductDB.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_PRODUCT_NAME = "Product"
        private const val COL_PRODUCT_ID = "product_id"
        private const val COL_PRODUCT_NAME = "product_name"
        private const val COL_PRODUCT_PRICE = "product_price"
        private const val COL_PRODUCT_IMAGE = "product_image"
    }
}
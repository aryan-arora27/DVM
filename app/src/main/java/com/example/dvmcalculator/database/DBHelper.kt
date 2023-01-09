package com.example.dvmcalculator.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {

        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                INPUT_COl + " TEXT," +
                RESULT_COL + " TEXT" + ")")

        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addInput(input : String, result : String ){

        val values = ContentValues()

        values.put(INPUT_COl, input)
        values.put(RESULT_COL, result)

        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)

        db.close()
    }

    fun getInput(): Cursor? {

        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
    }

    companion object{
        private val DATABASE_NAME = "MyDB"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "ResTable"

        val ID_COL = "id"
        val INPUT_COl = "input"
        val RESULT_COL = "result"
    }
}

package com.example.dvmcalculator.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import  android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABSE_NAME, null, DATABSE_VERSION) {

    companion object{
        private const val DATABSE_VERSION=1
        private const val DATABSE_NAME="CalcResults"
        private const val TABLE_RESULTS="ResultsTable"

        private const val KEY_ID="_id"
        private const val KEY_INPUT="input"
        private const val KEY_RESULT="result"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE_RESULTS_TABLE=("CREATE TABLE " + TABLE_RESULTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_INPUT + " TEXT," + KEY_RESULT + " TEXT" + ")")
        db?.execSQL(CREATE_RESULTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db!!.execSQL("DROP TABLE IF EXISTS "+ TABLE_RESULTS)
        onCreate(db)
    }

    fun addResult(res: ResModelClass): Long{
        val db=this.writableDatabase

        val contentValues=ContentValues()
        contentValues.put(KEY_INPUT, res.input)
        contentValues.put(KEY_RESULT, res.result)

        val success=db.insert(TABLE_RESULTS, null, contentValues)

        db.close()
        return success
    }

    @SuppressLint("Range")
    fun viewResult(): List<ResModelClass>{
        val reslist=ArrayList<ResModelClass>()
        val db=writableDatabase
        val selectQuery="SELECT * FROM $TABLE_RESULTS"
        val cursor=db.rawQuery(selectQuery, null)
        if(cursor!=null){
            if(cursor.moveToFirst()){
                do{
                    val res=ResModelClass()
                    res.id=Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID)))
                    res.input=cursor.getString(cursor.getColumnIndex(TABLE_RESULTS))
                    res.result=cursor.getString(cursor.getColumnIndex(KEY_RESULT))
                    reslist.add(res)
                }while (cursor.moveToNext())
            }
        }
        cursor.close()
        return reslist
    }
}
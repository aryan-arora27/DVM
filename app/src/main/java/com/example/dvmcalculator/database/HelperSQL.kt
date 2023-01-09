package com.example.dvmcalculator.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HelperSQL(c:Context) :SQLiteOpenHelper(
    c,CalcHistData.DATABASE_NAME,null,CalcHistData.DATABASE_VERSION
),CalcHistData
{
    lateinit var values:ContentValues
    lateinit var db:SQLiteDatabase
    var curs :Cursor?=null

    override fun onCreate(p0: SQLiteDatabase?) {
        db?.execSQL(CalcHistData.TABLE_SET)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(CalcHistData.TABLE_GET)
        onCreate(db)
    }

    override fun listCalc(): ArrayList<ResData> {
        db=this.readableDatabase
        val storeCalcInfo= ArrayList<ResData>()
        curs=db.rawQuery(CalcHistData.USER_SELECT,null)
        if(curs!!.moveToFirst()){
            do{
                val id=curs!!.getString(0)
                val calc=curs!!.getString(1)
                val res=curs!!.getString(2)
                storeCalcInfo.add(ResData(id,calc,res))
            }while (curs!!.moveToNext())
        }
        curs!!.requery()
        curs!!.close()
        return storeCalcInfo
    }

    override fun addUserInfo(us: ResData) {
        values= ContentValues()
        curs=db.rawQuery(CalcHistData.USER_SELECT,null)
        values.put(




            CalcHistData.COLUMN_CALC,us.calc)
        values.put(CalcHistData.COLUMN_RESULT,us.result)
        db.insert(CalcHistData.TABLE_CALC,null,values)
        curs!!.requery()

    }
}
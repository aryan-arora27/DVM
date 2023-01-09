package com.example.dvmcalculator.database

interface CalcHistData {
    fun listCalc() :ArrayList<ResData>
    fun addUserInfo(us: ResData)
    companion object{
        const val DATABASE_NAME = "MyDB"
        const val DATABASE_VERSION = 1
        const val TABLE_CALC = "ResTable"

        const val COLUMN_ID = "_id"
        const val COLUMN_CALC = "input"
        const val COLUMN_RESULT = "result"

        const val USER_SELECT="SELECT * FROM $TABLE_CALC"

        const val TABLE_GET= "DROP TABLE EXISTS $TABLE_CALC"

        const val TABLE_SET="CREATE TABLE $TABLE_CALC" +
                "($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_CALC TEXT, $COLUMN_RESULT TEXT)"

    }
}
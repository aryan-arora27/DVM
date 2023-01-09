package com.example.dvmcalculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calculatordvm.R
import com.example.dvmcalculator.database.DBHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.pow
import androidx.recyclerview.widget.RecyclerView
import com.example.dvmcalculator.database.HelperSQL
import com.example.dvmcalculator.database.ResData

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    private lateinit var datahelper:HelperSQL

    fun numberAction(view: View) {
        if(view is Button){
            if(view.text=="."){
                    workingsTV.append(view.text)
            }
            else{
                workingsTV.append(view.text)
            }
        }
    }

    fun operationAction(view: View) {
        if(view is Button ){
            workingsTV.append(view.text)
        }
    }

    fun allClearAction(view: View) {
        workingsTV.text=""
        resultsTV.text=""
    }

    fun backspaceAction(view: View) {
        val length=workingsTV.length()
        if(length>0){
            workingsTV.text=workingsTV.text.subSequence(0,length-1)
        }
    }

    fun equalsAction(view: View) {
//        resultsTV.text=calculateResults()
//        val newCalc=ResData(workingsTV.text.toString(),resultsTV.text.toString())
          workingsTV.text=calculateResults()
        if(workingsTV.text.endsWith(".0")){
            val length=workingsTV.length()
            workingsTV.text=workingsTV.text.subSequence(0,length-2)
            resultsTV.text=workingsTV.text

        }
        else{
            resultsTV.text=calculateResults()
            workingsTV.text=calculateResults()
        }

    }

    @SuppressLint("Range")
    fun histAction(view:View){
        histBtn.setOnClickListener{
            addCalc(view)
        }
    }
    fun addCalc(v:View){
        datahelper= HelperSQL(v.context)
        recyclerView.layoutManager=LinearLayoutManager(v.context)
        recyclerView.setHasFixedSize(true)
        val allData=datahelper.listCalc()
        if(allData.size>0){
            recyclerView.visibility=View.VISIBLE
            resultsTV.visibility=INVISIBLE
            workingsTV.visibility=INVISIBLE
            val mAdapter=MyAdapter(v.context,allData)
            recyclerView.adapter=mAdapter
        }
        else{
            recyclerView.visibility=View.GONE
            resultsTV.visibility=VISIBLE
            workingsTV.visibility=VISIBLE
        }
    }

//    fun backAction(view: View) {
//        workingsTV.visibility= VISIBLE
//        resultsTV.visibility=VISIBLE
//        recyclerView.visibility= INVISIBLE
//    }

    private fun calculateResults(): String
    {
        val digitOperators=digitsOperators()
        if(digitOperators.isEmpty()){
            return ""
        }

        if(digitOperators.contains('[') || digitOperators.contains(']')){
            val ans=arrayMulCalc(digitOperators)
            return ans
        }
        else{
            val bracketCal= bracketCalculate(digitOperators)
            val timesDivision = timesDivisionCalculate(bracketCal)
            if(timesDivision.isEmpty()){
                return ""
            }
            val result = addSubtractCal(timesDivision)

            return result.toString()
        }
    }

    private fun arrayMulCalc(passedList: MutableList<Any>): String {
        var start1=passedList.indexOf('[')+1
        var stop1=passedList.indexOf(']')-1
        val noOfEle=(stop1-start1)/2 +1
        var newList=mutableListOf<Any>()
        newList=passedList
        val tempList1=mutableListOf<Any>()
        val tempList2=mutableListOf<Any>()
        for(i in start1..stop1){
            tempList1.add(passedList[i])
        }
        for(i in start1-1..stop1+1){
            newList.removeAt(i)
        }
        println(newList)
        start1=noOfEle+1
        stop1=newList.size-2
        val size=tempList1.size-1
        for(i in start1..stop1){
            tempList2.add(newList[i])
            if(i!=(2*noOfEle)){
                tempList2.add(',')
            }
        }
        val result=mutableListOf<Any>()
        var t1=0.0f
        var t2=0.0f
        println(tempList1)
        println(tempList2)
        println(size)
        if(tempList1.size==tempList2.size){
            for(i in 0..size){
                if(i%2==0){
                    t1=tempList2[i] as Float
                    t2=tempList1[i] as Float
                    result.add(t1*t2)
                }

            }
            return result.toString()
        }
        else{
            return "Error"
        }
    }

    private fun addSubtractCal(passedList: MutableList<Any>): Float {
        var result:Float
        println(passedList)

        result= passedList[0] as Float

        for(i in passedList.indices){
            if(passedList[i] is Char && i!=passedList.lastIndex){
                val operator = passedList[i]
                val nextDigit =passedList[i+1] as Float
                if (operator=='+'){
                    result+= nextDigit
                }
                if (operator=='-'){
                    result-= nextDigit
                }
            }
        }
        return result
    }

    private fun bracketCalculate(passedList: MutableList<Any>): MutableList<Any> {
        var list=passedList
        while (list.contains('(') && list.contains(')')){
            list=calBracket(list)
        }
        return list
    }

    private fun timesDivisionCalculate(passedList: MutableList<Any>): MutableList<Any> {
        var list=passedList
        while (list.contains('x') || list.contains('/') || list.contains('^')){
            list=calTimesDiv(list)
        }
        return list
    }

    private fun calBracket(passedList: MutableList<Any>): MutableList<Any> {
        var list=passedList
        var newList= mutableListOf<Any>()
        val start=list.indexOf('(')+1
        val stop=list.indexOf(')')-1
        val tempList1= mutableListOf<Any>()
        for (i in start..stop){
            val digit=list[i]
            tempList1.add(digit)
        }
        println(tempList1)

        val timesDivision = timesDivisionCalculate(tempList1)
        var result=0.0f
        if(timesDivision.isEmpty()){
            result=0.0f
        }
        result = addSubtractCal(timesDivision)
        for(i in 0..start-2){
            newList.add(passedList[i])
        }
        newList.add(result)
        for(i in stop+2..passedList.size-1){
            newList.add(passedList[i])
        }

        return newList
    }


    private fun calTimesDiv(passedList: MutableList<Any>): MutableList<Any> {
        val newList= mutableListOf<Any>()
        var restartIndex=passedList.size
        for (i in passedList.indices){
            if (passedList[i] is Char && i!=passedList.lastIndex && i<restartIndex){
                val operator =passedList[i]
                val prevDigit =passedList[i-1] as Float
                val nextDigit =passedList[i+1] as Float
                when(operator){
                    'x'->
                    {
                        newList.add(prevDigit * nextDigit)
                        restartIndex=i+1
                    }
                    '/'->
                    {
                        newList.add(prevDigit / nextDigit)
                        restartIndex=i+1
                    }
                    '^'->
                    {
                        newList.add(prevDigit.pow(nextDigit))
                        restartIndex=i+1
                    }

                    else->
                    {
                        newList.add(prevDigit)
                        newList.add(operator)


                    }
                }
            }
            if(i>restartIndex){
                newList.add(passedList[i])
            }
        }
        println(newList)

        return newList
    }

    private fun digitsOperators(): MutableList<Any>
    {
        val list = mutableListOf<Any>()
        var currentDigit=""
        var i=0
        if((workingsTV.text).startsWith('-')){
            var a:Float
            a= 0.0F
            list.add(a)
        }
        for(character in workingsTV.text){
            if(character.isDigit() || character=='.'){
                currentDigit+=character
            }
            else{
                if(currentDigit!="") {
                    list.add(currentDigit.toFloat())
                    currentDigit = ""
                    list.add(character)
                    i++
                }
                else{
                    list.add(character)
                    i++
                }
            }
        }
        if(currentDigit!=""){
            list.add(currentDigit.toFloat())
        }
        println(list)

        return list
    }

}
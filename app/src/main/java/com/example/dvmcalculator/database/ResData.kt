package com.example.dvmcalculator.database

data class ResData(
    val id:String,
    val calc:String,
    val result:String
){
    internal constructor(
        calc: String,
        result: String
    ) :this (
        id="",
        calc=calc,
        result= result
            ){}

    internal constructor(
        calc: String
    ) :this (
        calc=calc,
        result=""
    ){}
}

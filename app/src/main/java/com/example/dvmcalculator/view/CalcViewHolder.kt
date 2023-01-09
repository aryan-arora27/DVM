package com.example.dvmcalculator.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calculatordvm.R

class CalcViewHolder(v: View):RecyclerView.ViewHolder(v) {
    val usercalc=v.findViewById<TextView>(R.id.textcalc)
    val userres=v.findViewById<TextView>(R.id.textresult)

}
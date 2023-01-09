package com.example.dvmcalculator

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.calculatordvm.R
import kotlinx.android.synthetic.main.activity_main.*
import com.example.dvmcalculator.database.HelperSQL
import com.example.dvmcalculator.database.ResData
import com.example.dvmcalculator.view.CalcViewHolder

class MyAdapter(
    private val c:Context,listUser:ArrayList<ResData>
):RecyclerView.Adapter<CalcViewHolder>()
{

    private val listUs:ArrayList<ResData>
    private val mDataBase:HelperSQL
    init {
        this.listUs=listUser
        mDataBase= HelperSQL(c)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalcViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.userinput,parent,false)
        return CalcViewHolder(v)
    }

    override fun onBindViewHolder(holder: CalcViewHolder, position: Int) {
        val newList= listUs[position]
        holder.usercalc.text=newList.calc
        holder.userres.text=newList.result
    }

    override fun getItemCount(): Int {

        return listUs.size
    }

}
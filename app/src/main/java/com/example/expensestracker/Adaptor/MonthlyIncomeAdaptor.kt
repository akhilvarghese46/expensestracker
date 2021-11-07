package com.example.expensestracker.Adaptor

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.expensestracker.Data.IncomeMonthData
import com.example.expensestracker.Data.MonthData
import com.example.expensestracker.R

class MonthlyIncomeAdaptor(private val context: Activity, private val arrayList: ArrayList<IncomeMonthData>):
        ArrayAdapter<IncomeMonthData>(context, R.layout.activity_monthlyincome,arrayList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.list_view,null)

        val itemid : TextView = view.findViewById(R.id.item_id)
        val itemname : TextView = view.findViewById(R.id.item_name)
        val itemamount : TextView = view.findViewById(R.id.item_amount)
        val addeddate : TextView = view.findViewById(R.id.added_date)

        itemid.text = arrayList[position].itemId.toString()
        itemname.text = arrayList[position].month
        itemamount.text = arrayList[position].amount.toString()
        addeddate.text = arrayList[position].itemAddedDate.toString()

        return view
    }
}
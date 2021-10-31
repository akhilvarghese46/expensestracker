package com.example.expensestracker.Adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.expensestracker.Data.MonthData
import com.example.expensestracker.R
import com.example.expensestracker.monthlyexpensesActivity

class MonthlyAdaptor(private val context: monthlyexpensesActivity, private val arrayList: ArrayList<MonthData>):
        ArrayAdapter<MonthData>(context, R.layout.activity_monthlyexpenses,arrayList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.list_view,null)

        val itemname : TextView = view.findViewById(R.id.item_name)
        val itemamount : TextView = view.findViewById(R.id.item_amount)
        val addeddate : TextView = view.findViewById(R.id.added_date)

        itemname.text = arrayList[position].month
        itemamount.text = arrayList[position].amount.toString()


        return view
    }
}
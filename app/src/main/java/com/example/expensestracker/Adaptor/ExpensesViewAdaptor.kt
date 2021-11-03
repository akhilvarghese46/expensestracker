package com.example.expensestracker.Adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.expensestracker.Data.ExpensesData
import com.example.expensestracker.ExpensesviewActivity
import com.example.expensestracker.R

class ExpensesViewAdaptor(private val context: ExpensesviewActivity, private val arrayList : ArrayList<ExpensesData>):
    ArrayAdapter<ExpensesData>(context, R.layout.activity_listview,arrayList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.activity_listview,null)

        val itemname : TextView = view.findViewById(R.id.item_name)
        val itemamount : TextView = view.findViewById(R.id.item_amount)
        val addeddate : TextView = view.findViewById(R.id.added_date)

        itemname.text = arrayList[position].itemName
        itemamount.text = arrayList[position].itemAmount.toString()
        addeddate.text = arrayList[position].itemAddedDate

        return view
    }

}
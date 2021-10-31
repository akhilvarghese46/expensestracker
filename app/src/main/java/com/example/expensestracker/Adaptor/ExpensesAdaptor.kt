package com.example.expensestracker.Adaptor

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.expensestracker.Data.ExpensesData
import com.example.expensestracker.R

/*class ExpensesAdaptor(private val context: Activity, private val arrayList: ArrayList<ExpensesData>):
    ArrayAdapter<ExpensesData>(context, R.layout.list_view,arrayList)*/

class ExpensesAdaptor(private val context: Activity, private val arrayList : ArrayList<ExpensesData>):
    ArrayAdapter<ExpensesData>(context, R.layout.list_view,arrayList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.list_view,null)

        val itemname : TextView = view.findViewById(R.id.item_name)
        val itemamount : TextView = view.findViewById(R.id.item_amount)
        val addeddate : TextView = view.findViewById(R.id.added_date)

        itemname.text = arrayList[position].itemName
        itemamount.text = arrayList[position].itemAmount.toString()
        addeddate.text = arrayList[position].itemAddedDate

        return view
    }
    /*override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.list_view,null)

        val itemID : TextView = view.findViewById(R.id.item_id)
        val itemname : TextView = view.findViewById(R.id.item_name)
        val itemamount : TextView = view.findViewById(R.id.item_amount)
        //val itemdiscription : TextView = view.findViewById(R.id.item_discription)
        val itemaddeddate : TextView = view.findViewById(R.id.added_date)

        itemID.text = arrayList[position].itemId.toString()
        itemname.text = arrayList[position].itemName.toString()
        itemamount.text = arrayList[position].itemAmount.toString()
       // itemdiscription.text = arrayList[p0].itemAmount.toString()
        itemaddeddate.text = arrayList[position].itemAmount.toString()


        return view
    }*/
}
package com.example.expensestracker

import android.os.Bundle
import android.widget.ListView
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity

class monthlyexpensesActivity: AppCompatActivity() {

    var _numberpicker: NumberPicker? = null
    var monthArrList = arrayListOf<MonthData>()
    lateinit var myAdapter: MonthlyAdaptor
    lateinit var myListview: ListView
    var monthList: List<String> = arrayListOf(
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monthlyexpenses)
        myListview = findViewById(R.id.expense_monthview)


        _numberpicker = findViewById<NumberPicker>(R.id.expense_yarpicker)
        _numberpicker?.setMinValue(1975)
        _numberpicker?.setMaxValue(1980)
        var obj1: MonthData =
            MonthData()
        obj1.month ="January"
        obj1.amount= 15

        var obj2: MonthData =
            MonthData()
        obj2.month ="feb"
        obj2.amount= 22
        /*monthArrList[0].month = "January"
        monthArrList[0].amount = 15

        monthArrList[1].month = "Feb"
        monthArrList[1].amount = 22*/
        monthArrList.add(obj1)
        monthArrList.add(obj2)

        myAdapter = MonthlyAdaptor(this, monthArrList)
        myListview.adapter = myAdapter
        _numberpicker?.setOnValueChangedListener(object : NumberPicker.OnValueChangeListener {
            override fun onValueChange(p0: NumberPicker?, p1: Int, p2: Int) {
                //_textview?.setText("user has selected: " + p2)


            }
        })
    }

}
package com.example.expensestracker

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.NumberPicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.expensestracker.Adaptor.MonthlyAdaptor
import com.example.expensestracker.Data.MonthData

class monthlyexpensesActivity: AppCompatActivity(), AdapterView.OnItemClickListener {

    var _numberpicker: NumberPicker? = null
    var monthArryList = arrayListOf<MonthData>()
    lateinit var mothlyAdapter: MonthlyAdaptor
    lateinit var monthListview: ListView
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
        monthListview = findViewById(R.id.expense_monthview)


        _numberpicker = findViewById<NumberPicker>(R.id.expense_yarpicker)
        _numberpicker?.setMinValue(1975)
        _numberpicker?.setMaxValue(1980)
        var obj1: MonthData =
            MonthData()
        obj1.month =monthList[0]
        obj1.amount= 15

        var obj2: MonthData =
            MonthData()
        obj2.month =monthList[1]
        obj2.amount= 22
        /*monthArrList[0].month = "January"
        monthArrList[0].amount = 15

        monthArrList[1].month = "Feb"
        monthArrList[1].amount = 22*/
        monthArryList.add(obj1)
        monthArryList.add(obj2)

        mothlyAdapter = MonthlyAdaptor(this, monthArryList)
        monthListview.adapter = mothlyAdapter
        monthListview.onItemClickListener = this

        _numberpicker?.setOnValueChangedListener(object : NumberPicker.OnValueChangeListener {
            override fun onValueChange(p0: NumberPicker?, p1: Int, p2: Int) {
                //_textview?.setText("user has selected: " + p2)


            }
        })
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val monthName = p1?.findViewById(R.id.item_name) as TextView
        var monthname = monthName.text.toString().substring(0,3)

       // val intent = Intent(this, expensesviewActivity::class.java)
        //intent.putExtra("monthName", monthname);
        //startActivity(intent)
    }

}
package com.example.expensestracker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.NumberPicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.expensestracker.Adaptor.MonthlyAdaptor
import com.example.expensestracker.Data.ExpensesData
import com.example.expensestracker.Data.IncomeData
import com.example.expensestracker.Data.MonthData

class MonthlyIncomeActivity: AppCompatActivity(), AdapterView.OnItemClickListener {

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
        "august",
        "September",
        "October",
        "november",
        "December"
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monthlyexpenses)
        monthListview = findViewById(R.id.expense_monthview)


        _numberpicker = findViewById<NumberPicker>(R.id.expense_yarpicker)
        _numberpicker?.setMinValue(1975)
        _numberpicker?.setMaxValue(1980)


        var dbData = DataBaseHelper(applicationContext)
        var db = dbData.readableDatabase
       // var incomeDatalist = arrayListOf<IncomeData>()
        var incomeData = db.rawQuery("select * from monthlyIncome" , null)

        for (i in monthList.indices) {
            incomeData.moveToFirst()
            var obj: MonthData = MonthData()
            obj.month = monthList[i]
            var totalamount:Int=0
            val mon = monthList[i].substring(0,3).toString()
            val mm = incomeData.getString(4)
            var bolval = incomeData.getString(4).contains(monthList[i].substring(0,3))
                for(i in 0 until incomeData.count) {
                    if (bolval == true) {

                        totalamount = totalamount + incomeData.getInt(2)

                    }
                    incomeData.moveToNext()
                }


            obj.amount = totalamount
            monthArryList.add(obj)
        }




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
        val selectedMonth = p1?.findViewById(R.id.item_name) as TextView
        var monthname = selectedMonth.text.toString().substring(0,3)

        val intent = Intent(this, IncomeviewActivity::class.java)
        intent.putExtra("selectedMonth", monthname);
        startActivity(intent)
    }

}
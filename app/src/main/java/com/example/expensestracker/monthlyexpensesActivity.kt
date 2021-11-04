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

        var currentYear = 2021
        get_MonthList(currentYear)


        _numberpicker?.setOnValueChangedListener(object : NumberPicker.OnValueChangeListener {
            override fun onValueChange(p0: NumberPicker?, p1: Int, p2: Int) {
                monthArryList.clear()
                get_MonthList(p2)

            }
        })
    }

    private fun get_MonthList(currentYear: Int) {
        monthListview = findViewById(R.id.expense_monthview)


        _numberpicker = findViewById<NumberPicker>(R.id.expense_yarpicker)
        _numberpicker?.setMinValue(2015)
        _numberpicker?.setMaxValue(2030)
        _numberpicker?.setValue(currentYear)

        var str = "select * from monthlyExpenses where date_added like '%$currentYear%'"
        var dbData = DataBaseHelper(applicationContext)
        var db = dbData.readableDatabase
        var expensesDatalist = arrayListOf<ExpensesData>()
        var expensesData = db.rawQuery(str, null)

        for (i in monthList.indices) {
            expensesData.moveToFirst()
            var obj: MonthData = MonthData()
            obj.month = monthList[i]
            var totalamount:Int=0
            val mon = monthList[i].substring(0,3).toString()


            for(i in 0 until expensesData.count) {
                val mm = expensesData.getString(4)
                if (expensesData.getString(4).contains(mon)) {

                    totalamount = totalamount + expensesData.getInt(2)

                }
                expensesData.moveToNext()
            }


            obj.amount = totalamount
            monthArryList.add(obj)
        }



        mothlyAdapter = MonthlyAdaptor(this, monthArryList)
        monthListview.adapter = mothlyAdapter
        monthListview.onItemClickListener = this
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val selectedMonth = p1?.findViewById(R.id.item_name) as TextView
        var monthname = selectedMonth.text.toString().substring(0,3)
        var selYear = findViewById<NumberPicker>(R.id.expense_yarpicker).value.toString()
        val intent = Intent(this, ExpensesviewActivity::class.java)
        intent.putExtra("selectedMonth", monthname)
        intent.putExtra("selecedYear", selYear);
        startActivity(intent)
    }

}
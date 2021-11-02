package com.example.expensestracker

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.expensestracker.Adaptor.ExpensesAdaptor
import com.example.expensestracker.Adaptor.ExpensesViewAdaptor
import com.example.expensestracker.Data.ExpensesData

class ExpensesviewActivity:AppCompatActivity() {
    lateinit var expensesListview: ListView
    lateinit var expensesviewAdaptor: ExpensesViewAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expensesview)
        expensesListview = findViewById(R.id.monthlyexpensesList)
        getMonthExpensesList()

    }

    private fun getMonthExpensesList() {
        var selectedMonth = intent.getStringExtra("selectedMonth")
        var dbData = DataBaseHelper(applicationContext)
        var db = dbData.readableDatabase
        var expensesDatalist = arrayListOf<ExpensesData>()
        var expensesData = db.rawQuery("select * from monthlyExpenses" , null)
        expensesData.moveToFirst()
        for(i in 0 until expensesData.count) {
            if (expensesData.getString(4).contains(selectedMonth.toString())) {
                var obj: ExpensesData = ExpensesData(
                    expensesData.getInt(0),
                    expensesData.getString(1),
                    expensesData.getInt(2),
                    expensesData.getString(3),
                    expensesData.getString(4)
                )
                expensesDatalist.add(obj)
            }
            expensesData.moveToNext()

        }


        //expensesListView = findViewById(R.id.monthlyexpensesList)
        // expensesListView.isClickable = true
        expensesviewAdaptor = ExpensesViewAdaptor(this, expensesDatalist)
        expensesListview.adapter = expensesviewAdaptor
    }
}
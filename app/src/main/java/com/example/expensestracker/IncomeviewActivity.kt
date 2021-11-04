package com.example.expensestracker

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.expensestracker.Adaptor.ExpensesAdaptor
import com.example.expensestracker.Adaptor.ExpensesViewAdaptor
import com.example.expensestracker.Adaptor.IncomeviewAdaptor
import com.example.expensestracker.Adaptor.MonthlyAdaptor
import com.example.expensestracker.Data.ExpensesData
import com.example.expensestracker.Data.IncomeData

class IncomeviewActivity:AppCompatActivity(), AdapterView.OnItemClickListener {
    lateinit var IncomeListview: ListView
    lateinit var incomeviewAdaptor: IncomeviewAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incomeview)
        IncomeListview = findViewById(R.id.monthlyincomeList)
        getMonthIncomeList()





    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val iteamId = p1?.findViewById(R.id.item_id) as TextView
        val itemname = p1?.findViewById(R.id.item_name) as TextView
        val addeddate = p1?.findViewById(R.id.added_date) as TextView
        val itemamount = p1?.findViewById(R.id.item_amount) as TextView

        // Custom dialog layout
        val income_DialogView =
            LayoutInflater.from(this).inflate(R.layout.activity_viewincome, null)
        val expense_Builder = AlertDialog.Builder(this)
            .setView(income_DialogView)
        val expitemid = income_DialogView.findViewById(R.id.item_id) as TextView
        val expSelectDate = income_DialogView.findViewById(R.id.txt_selected_Date) as TextView
        val expenseitem = income_DialogView.findViewById(R.id.expense_item) as TextView
        val expensevalue = income_DialogView.findViewById(R.id.expense_value) as TextView


        expitemid.text = iteamId.text.toString()
        expSelectDate.text = addeddate.text.toString()
        expenseitem.text = itemname.text.toString()
        expensevalue.text = itemamount.text.toString()
        val mAlertDialog = expense_Builder.show()

        val edit_button: View = income_DialogView.findViewById(R.id.floatingActionButton3)
        edit_button.setOnClickListener {
            mAlertDialog.dismiss()
            val EditDialogView = LayoutInflater.from(this).inflate(R.layout.activity_editincome, null)
            val expense_editBuilder = AlertDialog.Builder(this)
                .setView(EditDialogView)
            val expitemid = EditDialogView.findViewById(R.id.item_id) as TextView
            val expSelectDate =
                EditDialogView.findViewById(R.id.txt_selected_Date) as TextView
            val expenseitem = EditDialogView.findViewById(R.id.expense_item) as TextView
            val expensevalue = EditDialogView.findViewById(R.id.expense_value) as TextView


            expitemid.text = iteamId.text.toString()
            expSelectDate.text = addeddate.text.toString()
            expenseitem.text = itemname.text.toString()
            expensevalue.text = itemamount.text.toString()

            val editAlertDialog = expense_editBuilder.show()
            val edit_submit_button: View = EditDialogView.findViewById(R.id.btn_add_Income)
            edit_submit_button.setOnClickListener {
                val expitemid = EditDialogView.findViewById(R.id.item_id) as TextView
                val expSelectDate =
                    EditDialogView.findViewById(R.id.txt_selected_Date) as TextView
                val expenseitem = EditDialogView.findViewById(R.id.expense_item) as TextView
                val expensevalue =
                    EditDialogView.findViewById(R.id.expense_value) as TextView


             update_Income_Data(
                    expitemid.text.toString().toInt(),
                    expenseitem.text.toString(),
                    expensevalue.text.toString().toInt(),
                    expSelectDate.text.toString()
                )
                editAlertDialog.dismiss()
            }


        }
    }

    private fun update_Income_Data(incomitemid: Int, incomitem: String, incomvalue: Int, incomSelectDate: String) {
        var dbData = DataBaseHelper(applicationContext)
        var dataBase = dbData.readableDatabase
        var cv = ContentValues()
        cv.put("income_source" , incomitem)
        cv.put("date_added" , incomSelectDate)
        cv.put("income_amount" , incomvalue)
        val whereclause = "_idIncome?"
        val whereargs = arrayOf(incomitemid.toString())
        dataBase.update("monthlyIncome",cv,whereclause,whereargs )
    }


    private fun getMonthIncomeList() {
        var selectedMonth = intent.getStringExtra("selectedMonth")
        var dbData = DataBaseHelper(applicationContext)
        var db = dbData.readableDatabase
        var incomeDatalist = arrayListOf<IncomeData>()
        var incomeData = db.rawQuery("select * from monthlyIncome" , null)
        incomeData.moveToFirst()
        for(i in 0 until incomeData.count) {
            if (incomeData.getString(4).contains(selectedMonth.toString())) {
                var obj: IncomeData = IncomeData(
                    incomeData.getInt(0),
                    incomeData.getString(1),
                    incomeData.getInt(2),
                    incomeData.getString(3)
                )
                incomeDatalist.add(obj)
            }
            incomeData.moveToNext()
        }


        IncomeListview = findViewById(R.id.monthlyexpensesList)
       // expensesListview.isClickable = true
        incomeviewAdaptor = IncomeviewAdaptor(this, incomeDatalist)
        IncomeListview.adapter = incomeviewAdaptor
        IncomeListview.setOnItemClickListener(this)



    }
}
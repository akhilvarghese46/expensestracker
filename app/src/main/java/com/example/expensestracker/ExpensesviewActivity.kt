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
import com.example.expensestracker.Adaptor.MonthlyAdaptor
import com.example.expensestracker.Data.ExpensesData

class ExpensesviewActivity:AppCompatActivity(), AdapterView.OnItemClickListener {
    lateinit var expensesListview: ListView
    lateinit var expensesviewAdaptor: ExpensesViewAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expensesview)
        expensesListview = findViewById(R.id.monthlyexpensesList)
        getMonthExpensesList()





    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
      val iteamId = p1?.findViewById(R.id.item_id) as TextView
          val itemname = p1?.findViewById(R.id.item_name) as TextView
         val itemdiscription = p1?.findViewById(R.id.item_discription) as TextView
         val addeddate = p1?.findViewById(R.id.added_date) as TextView
         val itemamount = p1?.findViewById(R.id.item_amount) as TextView

         // Custom dialog layout
         val expense_DialogView = LayoutInflater.from(this).inflate(R.layout.activity_viewexpenses, null )
         val expense_Builder = AlertDialog.Builder(this)
             .setView(expense_DialogView)
         val expitemid = expense_DialogView.findViewById(R.id.item_id) as TextView
         val expSelectDate = expense_DialogView.findViewById(R.id.txt_selected_Date) as TextView
         val expenseitem = expense_DialogView.findViewById(R.id.expense_item) as TextView
         val expensevalue = expense_DialogView.findViewById(R.id.expense_value) as TextView
         val expense_discription = expense_DialogView.findViewById(R.id.expense_discription) as TextView

         expitemid.text=iteamId.text.toString()
         expSelectDate.text=addeddate.text.toString()
         expenseitem.text=itemname.text.toString()
         expensevalue.text=itemamount.text.toString()
         expense_discription.text=itemdiscription.text.toString()
         val mAlertDialog = expense_Builder.show()

         val edit_button: View = expense_DialogView.findViewById(R.id.floatingActionButton3)
         edit_button.setOnClickListener{
             mAlertDialog.dismiss()
             val expense_EditDialogView = LayoutInflater.from(this).inflate(R.layout.activity_editexpenses, null )
             val expense_editBuilder = AlertDialog.Builder(this)
                 .setView(expense_EditDialogView)
             val expitemid = expense_EditDialogView.findViewById(R.id.item_id) as TextView
             val expSelectDate = expense_EditDialogView.findViewById(R.id.txt_selected_Date) as TextView
             val expenseitem = expense_EditDialogView.findViewById(R.id.expense_item) as TextView
             val expensevalue = expense_EditDialogView.findViewById(R.id.expense_value) as TextView
             val expense_discription = expense_EditDialogView.findViewById(R.id.expense_discription) as TextView

             expitemid.text=iteamId.text.toString()
             expSelectDate.text=addeddate.text.toString()
             expenseitem.text=itemname.text.toString()
             expensevalue.text=itemamount.text.toString()
             expense_discription.text=itemdiscription.text.toString()

             val editAlertDialog = expense_editBuilder.show()
             val edit_submit_button: View = expense_EditDialogView.findViewById(R.id.btn_add_expense)
             edit_submit_button.setOnClickListener{
                 val expitemid = expense_EditDialogView.findViewById(R.id.item_id) as TextView
                 val expSelectDate = expense_EditDialogView.findViewById(R.id.txt_selected_Date) as TextView
                 val expenseitem = expense_EditDialogView.findViewById(R.id.expense_item) as TextView
                 val expensevalue = expense_EditDialogView.findViewById(R.id.expense_value) as TextView
                 val expense_discription = expense_EditDialogView.findViewById(R.id.expense_discription) as TextView

                 update_Expense_Data(expitemid.text.toString().toInt(),expenseitem.text.toString() ,expensevalue.text.toString().toInt(),expense_discription.text.toString(),expSelectDate.text.toString())
                 editAlertDialog.dismiss()
             }
         }





}

    private fun update_Expense_Data(expitemid: Int, expenseitem: String, expensevalue: Int, expense_discription: String, expSelectDate: String) {
        var dbData = DataBaseHelper(applicationContext)
        var dataBase = dbData.readableDatabase
        var cv = ContentValues()
        cv.put("item_name" , expenseitem)
        cv.put("amount" , expensevalue)
        cv.put("description" , expense_discription)
        cv.put("date_added" , expSelectDate)
        val whereclause = "_id?"
        val whereargs = arrayOf(expitemid.toString())
        dataBase.update("monthlyExpenses",cv,whereclause,whereargs )
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


        expensesListview = findViewById(R.id.monthlyexpensesList)
       // expensesListview.isClickable = true
        expensesviewAdaptor = ExpensesViewAdaptor(this, expensesDatalist)
        expensesListview.adapter = expensesviewAdaptor
        expensesListview.setOnItemClickListener(this)



    }
}
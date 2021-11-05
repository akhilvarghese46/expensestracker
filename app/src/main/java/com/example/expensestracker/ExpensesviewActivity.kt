package com.example.expensestracker

import android.app.DatePickerDialog
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
import java.text.SimpleDateFormat
import java.util.*

class ExpensesviewActivity:AppCompatActivity(), AdapterView.OnItemClickListener {
    lateinit var expensesListview: ListView
    lateinit var expensesviewAdaptor: ExpensesViewAdaptor
    lateinit var txt_selected_Date: TextView
    lateinit var cal: Calendar
    lateinit var passedMonth: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expensesview)
        expensesListview = findViewById(R.id.monthlyexpensesList)
        getMonthExpensesList()
        getRregorIrregTotal()





    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
      val iteamId = p1?.findViewById(R.id.item_id) as TextView
          val itemname = p1?.findViewById(R.id.item_name) as TextView
         val itemdiscription = p1?.findViewById(R.id.item_discription) as TextView
         val addeddate = p1?.findViewById(R.id.added_date) as TextView
         val itemamount = p1?.findViewById(R.id.item_amount) as TextView

         // Custom dialog layout
         val expense_DialogView = LayoutInflater.from(this).inflate(R.layout.activity_viewexpenses, null )
         val expense_Builder = AlertDialog.Builder(this,R.style.CustomAlertDialog)
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
             val expense_editBuilder = AlertDialog.Builder(this,R.style.CustomAlertDialog)
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
                 getMonthExpensesList()
                /* val monthActyObj =  monthlyexpensesActivity()
                 var year = expSelectDate.text.takeLast(4)
                 with(monthActyObj) {
                     get_MonthList(year.toString().toInt())
                 }*/
                 editAlertDialog.dismiss()
             }



             val btn_cancel: View = expense_EditDialogView.findViewById(R.id.btn_cancel_expense)
             btn_cancel.setOnClickListener {
                 editAlertDialog.dismiss()
             }


             txt_selected_Date = expense_EditDialogView.findViewById(R.id.txt_selected_Date)
             fn_today_date()
             val btn_choose_Date: View = expense_EditDialogView.findViewById(R.id.btn_choose_Date)
             btn_choose_Date.setOnClickListener {
                 val date_Dialog = DatePickerDialog(
                     this,
                     fn_date_listener,
                     cal.get(Calendar.YEAR),
                     cal.get(Calendar.MONTH),
                     cal.get(Calendar.DAY_OF_MONTH)
                 )
                 date_Dialog.show()
             }
         }

        val fabdelete: View = expense_DialogView.findViewById(R.id.fab_delete)
        fabdelete.setOnClickListener {
            val delexpitemid = expense_DialogView.findViewById(R.id.item_id) as TextView
            delete_ExpensesData(delexpitemid.text.toString().toInt())
            getMonthExpensesList()
            mAlertDialog.dismiss()
        }

        val btn_cancel: View = expense_DialogView.findViewById(R.id.btn_cancel_expense)
        btn_cancel.setOnClickListener {
            mAlertDialog.dismiss()
        }

}

    private fun delete_ExpensesData(Id: Int) {
        var dbData = DataBaseHelper(applicationContext)
        var dataBase = dbData.writableDatabase
        val whereclause = "_id = ?"
        val whereargs = arrayOf(Id.toString())
        dataBase.delete("monthlyExpenses",whereclause,whereargs )
    }

    private fun fn_today_date() {
        cal = Calendar.getInstance()
        val mYear = cal.get(Calendar.YEAR)
        val mMonth = cal.getDisplayName(Calendar.MONTH,Calendar.SHORT,Locale.US)
        val mDay = cal.get(Calendar.DAY_OF_MONTH)

        var strDate: String = mDay.toString() + "-" + mMonth.toString() + "-" + mYear.toString()
       // txt_selected_Date.setText(strDate)
        passedMonth = mMonth

    }
    private fun currentMonth(): String {
        cal = Calendar.getInstance()
        val mMonth = cal.getDisplayName(Calendar.MONTH,Calendar.SHORT,Locale.US)
        val curntMon = mMonth.toString()
        return curntMon
    }

    //Listener
    val fn_date_listener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, monthOfYear)
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        val myFormat = "d-MMM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        txt_selected_Date.text = sdf.format(cal.time)
        passedMonth = cal.getDisplayName(Calendar.MONTH,Calendar.SHORT,Locale.US)

    }

    private fun update_Expense_Data(expitemid: Int, expenseitem: String, expensevalue: Int, expense_discription: String, expSelectDate: String) {
        var dbData = DataBaseHelper(applicationContext)
        var dataBase = dbData.writableDatabase
        var cv = ContentValues()
        cv.put("item_name" , expenseitem)
        cv.put("amount" , expensevalue)
        cv.put("description" , expense_discription)
        cv.put("date_added" , expSelectDate)
        val whereclause = "_id = ?"
        val whereargs = arrayOf(expitemid.toString())
        dataBase.update("monthlyExpenses",cv,whereclause,whereargs )
    }
    private fun getRregorIrregTotal() {
        var selectedMonth = intent.getStringExtra("selectedMonth")
        var selecedYear = intent.getStringExtra("selecedYear")
        val totalRegular = findViewById(R.id.totalregularexp) as TextView
        val totalIrregular = findViewById(R.id.totalIrregularexp) as TextView
        var dbData = DataBaseHelper(applicationContext)
        var db = dbData.readableDatabase
        var str = "select sum(amount) from monthlyExpenses where date_added LIKE '%$selectedMonth%' and date_added like '%$selecedYear%' and  isRegular=1"
        var str2  = "select sum(amount) from monthlyExpenses where date_added LIKE '%$selectedMonth%' and date_added like '%$selecedYear%' and  isRegular=0"
        var regExpensesData = db.rawQuery(str, null)

        regExpensesData.moveToFirst()
        var irregExpensesData = db.rawQuery(str2,null)
        irregExpensesData.moveToFirst()
        totalIrregular.text = "€"+ irregExpensesData.getInt(0).toString()
        totalRegular.text = "€"+ regExpensesData.getInt(0).toString()
    }
    private fun getMonthExpensesList() {
        var selectedMonth = intent.getStringExtra("selectedMonth")
        var selecedYear = intent.getStringExtra("selecedYear")

        var dbData = DataBaseHelper(applicationContext)
        var db = dbData.readableDatabase
        var expensesDatalist = arrayListOf<ExpensesData>()
        var str = "select * from monthlyExpenses where date_added like '%$selecedYear%' and date_added like '%$selectedMonth%'"

        var expensesData = db.rawQuery(str, null)
        expensesData.moveToFirst()
        if(expensesData.count == 0)
        {
            var obj: ExpensesData = ExpensesData(
              0,
                "No Data Found",
                0,
                "",
                "",
                0
            )
            expensesDatalist.add(obj)
        }
        for(i in 0 until expensesData.count) {
           // if (expensesData.getString(4).contains(selectedMonth.toString())) {
                var obj: ExpensesData = ExpensesData(
                    expensesData.getInt(0),
                    expensesData.getString(1),
                    expensesData.getInt(2),
                    expensesData.getString(3),
                    expensesData.getString(4),
                    expensesData.getInt(5)
                )
                expensesDatalist.add(obj)
            //}
            expensesData.moveToNext()
        }


        expensesListview = findViewById(R.id.monthlyexpensesList)
       // expensesListview.isClickable = true
        expensesviewAdaptor = ExpensesViewAdaptor(this, expensesDatalist)
        expensesListview.adapter = expensesviewAdaptor
        expensesListview.setOnItemClickListener(this)



    }
}
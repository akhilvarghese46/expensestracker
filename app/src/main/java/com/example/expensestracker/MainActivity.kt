package com.example.expensestracker

import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat
import java.util.*
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.expensestracker.Adaptor.ExpensesAdaptor
import com.example.expensestracker.Data.ExpensesData
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private var clicked = false
    lateinit var cal: Calendar
    lateinit var txt_selected_Date: TextView
    lateinit var passedMonth: String
    private lateinit var monthlyexpensesList : ListView
    lateinit var db :String
    var expensesDatalist = arrayListOf<ExpensesData>()
    lateinit var expensesAdaptor: ExpensesAdaptor
    lateinit var expensesListView: ListView




    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getExpensesValues()

        val currentMonth = currentMonth()
        getExpenseAndincomeTotal(currentMonth)





///======================

        /*     var dbData = DataBaseHelper(applicationContext)
             var db = dbData.readableDatabase
             var result = db.rawQuery("select * from monthlyExpenses" , null)


             //monthlyexpensesList =


             var cv = ContentValues()
             cv.put("item_name" , "akkk")
             cv.put("amount" , 550)
             cv.put("description" , "test chumma")
             cv.put("date_added", "25-Aug-10")

             db.insert("monthlyExpenses",null,cv)
             result.requery()

             var ad = AlertDialog.Builder(this)
             ad.setTitle("Add record")
             ad.setMessage("Record inserted successfully.....!")
             ad.setPositiveButton("ok",null)
             ad.show()

             monthlyexpensesList = findViewById<ListView>(R.id.monthlyexpensesList)
             var adapter = SimpleCursorAdapter(applicationContext,android.R.layout.simple_expandable_list_item_2,result,
                 arrayOf("item_name" ,"amount"),
                 intArrayOf(android.R.id.text1 , android.R.id.text2))
             monthlyexpensesList.adapter = adapter

             /////==========================


     */

        //initialize floating action buttons
        val add_btn: View = findViewById(R.id.add_btn)
        val add_income: View = findViewById(R.id.add_income)
        val add_expenses: View = findViewById(R.id.add_expenses)
        val viewAllExpense :View = findViewById(R.id.btn_view_all_expense)
        val viewAllIncome :View = findViewById(R.id.btn_view_all_income)

        //view all expenses shows monthly list
        viewAllExpense.setOnClickListener {
            val intent = Intent(this, monthlyexpensesActivity::class.java)
            startActivity(intent)
        }

        //view all Income shows monthly list
        viewAllIncome.setOnClickListener {
            val intent = Intent(this, MonthlyIncomeActivity::class.java)
            startActivity(intent)
        }


        // add button click event - fab
        add_btn.setOnClickListener{
            onAddButtonClick()
        }

        // add income - fab
        add_income.setOnClickListener{
            //Toast.makeText(this,"add_income buttom Clicked", Toast.LENGTH_SHORT).show()

            // Custom dialog layout
            val income_DialogView = LayoutInflater.from(this).inflate(R.layout.add_income, null )
            val income_Builder = AlertDialog.Builder(this)
                .setView(income_DialogView)

            val mAlertDialog = income_Builder.show()

            // add income value
            val submit_button: View = income_DialogView.findViewById(R.id.btn_add_income)
            submit_button.setOnClickListener{
                val income_source = income_DialogView.findViewById(R.id.item_source) as EditText
                val income_value = income_DialogView.findViewById(R.id.income_value) as EditText
                val income_Date = income_DialogView.findViewById(R.id.txt_selected_Date) as TextView

                val incomeAddedDate: LocalDate = LocalDate.parse(income_Date.toString(),  DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                insert_Income_Data(income_source.text.toString(),income_value.text.toString().toInt(),income_Date.text.toString())
                mAlertDialog.dismiss()

            }

            // cancel button for income value adding dialog box
            val btn_cancel: View = income_DialogView.findViewById(R.id.btn_cancel_income)
            btn_cancel.setOnClickListener {
                mAlertDialog.dismiss()
            }

            // income date select
            txt_selected_Date = income_DialogView.findViewById(R.id.txt_selected_Date)
            fn_today_date()
            val btn_choose_Date: View = income_DialogView.findViewById(R.id.btn_choose_Date)
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

        // add expensive - fab
        add_expenses.setOnClickListener{
            //Toast.makeText(this,"add_expenses edit buttom Clicked", Toast.LENGTH_SHORT).show()

            // Custom dialog layout
            val expense_DialogView = LayoutInflater.from(this).inflate(R.layout.add_expenses, null )
            val expense_Builder = AlertDialog.Builder(this,R.style.CustomAlertDialog)
                .setView(expense_DialogView)

            val mAlertDialog = expense_Builder.show()

            // add income value
            val submit_button: View = expense_DialogView.findViewById(R.id.btn_add_expense)
            submit_button.setOnClickListener{
                val itemName = expense_DialogView.findViewById(R.id.expense_item) as EditText
                val itemAmount = expense_DialogView.findViewById(R.id.expense_value) as EditText
                val itemDate = expense_DialogView.findViewById(R.id.txt_selected_Date) as TextView
                val itemDiscription = expense_DialogView.findViewById(R.id.expense_discription) as EditText
                //val itemNewDate: LocalDate = LocalDate.parse(itemDate.toString(),  DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                insert_Expense_Data(itemName.text.toString() ,itemAmount.text.toString().toInt(),itemDiscription.text.toString(),itemDate.text.toString())
                mAlertDialog.dismiss()
            }

            // cancel button for income value adding dialog box
            val btn_cancel: View = expense_DialogView.findViewById(R.id.btn_cancel_expense)
            btn_cancel.setOnClickListener {
                mAlertDialog.dismiss()
            }

            // income date select
            txt_selected_Date = expense_DialogView.findViewById(R.id.txt_selected_Date)
            fn_today_date()
            val btn_choose_Date: View = expense_DialogView.findViewById(R.id.btn_choose_Date)
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

    }

    private fun getExpenseAndincomeTotal(currentMonth: String) {
        val totalExpenses = findViewById(R.id.totalexpenses) as TextView
        val totalIncome = findViewById(R.id.totalIncome) as TextView
        var dbData = DataBaseHelper(applicationContext)
        var db = dbData.readableDatabase

        var expensesData = db.rawQuery("select sum(amount) from monthlyExpenses where date_added LIKE '%$currentMonth%'", null)

        expensesData.moveToFirst()
        var incomeData = db.rawQuery("select sum(income_amount) from monthlyIncome where date_added LIKE '%$currentMonth%'" , null)

        incomeData.moveToFirst()
        totalIncome.text = incomeData.getInt(0).toString()
        totalExpenses.text = (incomeData.getInt(0) - expensesData.getInt(0)).toString()
    }

    public fun getExpensesValues() {
        var dbData = DataBaseHelper(applicationContext)
        var db = dbData.readableDatabase
        var expensesData = db.rawQuery("select * from monthlyExpenses" , null)

        expensesData.moveToFirst()
        for(i in 0 until expensesData.count) {
            var obj: ExpensesData = ExpensesData(expensesData.getInt(0), expensesData.getString(1),expensesData.getInt(2), expensesData.getString(3),expensesData.getString(4))
            /*obj.itemId = expensesData.getInt(0)
            obj.itemName = expensesData.getString(1)
            obj.itemAmount = expensesData.getInt(2)
            obj.itemDiscription = expensesData.getString(3)
            obj.itemAddedDate = expensesData.getString(4)*/
            expensesData.moveToNext()
            expensesDatalist.add(obj)
        }

        expensesListView = findViewById(R.id.monthlyexpensesList)
       // expensesListView.isClickable = true
        expensesAdaptor = ExpensesAdaptor(this, expensesDatalist)
        expensesListView.adapter = expensesAdaptor
       // expensesListView.onItemClickListener = this

    }

    private fun insert_Income_Data(incomeSource:String,incomeAmount: Int, incomeDate: String) {
        var dbData = DataBaseHelper(applicationContext)
        var dataBase = dbData.readableDatabase
        var cv = ContentValues()
        cv.put("income_amount" , incomeAmount)
        cv.put("income_source" , incomeSource)
        cv.put("date_added" , incomeDate.toString())
        dataBase.insert("monthlyIncome",null,cv)
    }

    private fun insert_Expense_Data(itemName:String, amount:Int, discription:String, addedDate: String) {
        var dbData = DataBaseHelper(applicationContext)
        var dataBase = dbData.readableDatabase
        var cv = ContentValues()
        cv.put("item_name" , itemName)
        cv.put("amount" , amount)
        cv.put("description" , discription)
        cv.put("date_added" , addedDate.toString())
        dataBase.insert("monthlyExpenses",null,cv)
    }



    private fun fn_today_date() {
        cal = Calendar.getInstance()
        val mYear = cal.get(Calendar.YEAR)
        val mMonth = cal.getDisplayName(Calendar.MONTH,Calendar.SHORT,Locale.US)
        val mDay = cal.get(Calendar.DAY_OF_MONTH)

        var strDate: String = mDay.toString() + "-" + mMonth.toString() + "-" + mYear.toString()
        txt_selected_Date.setText(strDate)
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

    private fun onAddButtonClick() {
        setVisibility(clicked)
        clicked = !clicked
    }

    private fun setVisibility(clicked:Boolean) {
        val add_income: View = findViewById(R.id.add_income)
        val add_expenses: View = findViewById(R.id.add_expenses)
        if(!clicked){
            add_expenses.visibility=View.VISIBLE
            add_income.visibility=View.VISIBLE
        }else{
            add_expenses.visibility=View.INVISIBLE
            add_income.visibility=View.INVISIBLE
        }

    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        TODO("Not yet implemented")
    }
}
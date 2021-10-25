package com.example.expensestracker

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var clicked = false
    lateinit var cal: Calendar
    lateinit var txt_selected_Date: TextView
    lateinit var passedMonth: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialize floating action buttons
        val add_btn: View = findViewById(R.id.add_btn)
        val add_income: View = findViewById(R.id.add_income)
        val add_expenses: View = findViewById(R.id.add_expenses)

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
            val expense_Builder = AlertDialog.Builder(this)
                .setView(expense_DialogView)

            val mAlertDialog = expense_Builder.show()

            // add income value
            val submit_button: View = expense_DialogView.findViewById(R.id.btn_add_expense)
            submit_button.setOnClickListener{
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


    private fun fn_today_date() {
        cal = Calendar.getInstance()
        val mYear = cal.get(Calendar.YEAR)
        val mMonth = cal.getDisplayName(Calendar.MONTH,Calendar.SHORT,Locale.US)
        val mDay = cal.get(Calendar.DAY_OF_MONTH)

        var strDate: String = mDay.toString() + "-" + mMonth.toString() + "-" + mYear.toString()
        txt_selected_Date.setText(strDate)
        passedMonth = mMonth

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
}
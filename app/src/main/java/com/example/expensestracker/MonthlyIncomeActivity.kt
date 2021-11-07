package com.example.expensestracker

import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.NumberPicker
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.expensestracker.Adaptor.MonthlyIncomeAdaptor
import com.example.expensestracker.Data.IncomeMonthData
import com.example.expensestracker.Data.MonthData
import java.text.SimpleDateFormat
import java.util.*

class MonthlyIncomeActivity: AppCompatActivity(), AdapterView.OnItemClickListener {

    var _numberpicker: NumberPicker? = null
    var monthArryList = arrayListOf<IncomeMonthData>()
    lateinit var mothlyincomeAdapter: MonthlyIncomeAdaptor
    lateinit var monthListview: ListView
    lateinit var txt_selected_Date: TextView
    lateinit var cal: Calendar
    lateinit var passedMonth: String
    var defaultYear :Int = 0

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
        setContentView(R.layout.activity_monthlyincome)
        val actionBar: ActionBar?
        actionBar = supportActionBar
        val colorDrawable = ColorDrawable(Color.parseColor("#FD0000"))
        actionBar?.setBackgroundDrawable(colorDrawable)

        var currentYear = 2021

        get_MonthList(currentYear)

        _numberpicker?.setOnValueChangedListener(object : NumberPicker.OnValueChangeListener {
            override fun onValueChange(p0: NumberPicker?, p1: Int, p2: Int) {
                //_textview?.setText("user has selected: " + p2)
                monthArryList.clear()
                defaultYear = p2
                get_MonthList(p2)

            }
        })
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val selectedMonth = p1?.findViewById(R.id.item_name) as TextView
        var selYear = findViewById<NumberPicker>(R.id.expense_yarpicker).value.toString()
        var monthname = selectedMonth.text.toString().substring(0,3)
        

        val iteamId = p1?.findViewById(R.id.item_id) as TextView
        val addeddate = p1?.findViewById(R.id.added_date) as TextView
        val itemamount = p1?.findViewById(R.id.item_amount) as TextView

        // Custom dialog layout
        val income_DialogView =
            LayoutInflater.from(this).inflate(R.layout.activity_viewincome, null)
        val income_Builder = AlertDialog.Builder(this,R.style.CustomAlertDialog)
            .setView(income_DialogView)
        val expitemid = income_DialogView.findViewById(R.id.item_id) as TextView
        val expSelectDate = income_DialogView.findViewById(R.id.txt_selected_Date) as TextView
        val expensevalue = income_DialogView.findViewById(R.id.income_value) as TextView


        expitemid.text = iteamId.text.toString()
        expSelectDate.text = addeddate.text.toString()
        expensevalue.text = itemamount.text.toString()
        val mAlertDialog = income_Builder.show()




        val btn_cancel: View = income_DialogView.findViewById(R.id.btn_cancel_income)
        btn_cancel.setOnClickListener {
            mAlertDialog.dismiss()
        }

        val edit_button: View = income_DialogView.findViewById(R.id.floatingActionButton3)
        edit_button.setOnClickListener {
            mAlertDialog.dismiss()
            val EditDialogView = LayoutInflater.from(this).inflate(R.layout.activity_editincome, null)
            val expense_editBuilder = AlertDialog.Builder(this,R.style.CustomAlertDialog)
                .setView(EditDialogView)
            val expitemid = EditDialogView.findViewById(R.id.item_id) as TextView
            val expSelectDate = EditDialogView.findViewById(R.id.txt_selected_Date) as TextView
            val expensevalue = EditDialogView.findViewById(R.id.expense_value) as TextView

            expitemid.text = iteamId.text.toString()
            expSelectDate.text = addeddate.text.toString()
            expensevalue.text = itemamount.text.toString()

            val editAlertDialog = expense_editBuilder.show()
            val edit_submit_button: View = EditDialogView.findViewById(R.id.btn_add_income)
            edit_submit_button.setOnClickListener {
                val expitemid = EditDialogView.findViewById(R.id.item_id) as TextView
                val expSelectDate =
                    EditDialogView.findViewById(R.id.txt_selected_Date) as TextView
                val expensevalue =
                    EditDialogView.findViewById(R.id.expense_value) as TextView

                update_Income_Data(
                    expitemid.text.toString().toInt(),
                    expensevalue.text.toString().toInt(),
                    expSelectDate.text.toString()
                )
                monthArryList.clear()
                get_MonthList(defaultYear)
                editAlertDialog.dismiss()
            }

            val btn_cancel_inedit: View = EditDialogView.findViewById(R.id.btn_cancel_income)
            btn_cancel_inedit.setOnClickListener {
                editAlertDialog.dismiss()
            }





            txt_selected_Date = EditDialogView.findViewById(R.id.txt_selected_Date)
            fn_today_date()
            val btn_choose_Date: View = EditDialogView.findViewById(R.id.btn_choose_Date)
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

        val fabdelete: View = income_DialogView.findViewById(R.id.fab_delete)
        fabdelete.setOnClickListener {


            val builder = AlertDialog.Builder(this)
            builder.setMessage("Are you sure you want to Delete?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->

                    val delexpitemid = income_DialogView.findViewById(R.id.item_id) as TextView
                    delete_IncomeData(delexpitemid.text.toString().toInt())
                    monthArryList.clear()
                    get_MonthList(defaultYear)
                    mAlertDialog.dismiss()
                }
                .setNegativeButton("No") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()



            mAlertDialog.dismiss()
        }

       


       // val intent = Intent(this, IncomeviewActivity::class.java)
        //intent.putExtra("selectedMonth", monthname)
        //intent.putExtra("selecedYear", selYear);
        //startActivity(intent)
    }

    private fun delete_IncomeData(Id: Int) {
        var dbData = DataBaseHelper(applicationContext)
        var dataBase = dbData.writableDatabase
        val whereclause = "_idIncome = ?"
        val whereargs = arrayOf(Id.toString())
        dataBase.delete("monthlyIncome",whereclause,whereargs )
    }

    private fun update_Income_Data(incomitemid: Int, incomvalue: Int, incomSelectDate: String) {
        var dbData = DataBaseHelper(applicationContext)
        var dataBase = dbData.readableDatabase
        var cv = ContentValues()
        cv.put("date_added" , incomSelectDate)
        cv.put("income_amount" , incomvalue)
        val whereclause = "_idIncome=?"
        val whereargs = arrayOf(incomitemid.toString())
        dataBase.update("monthlyIncome",cv,whereclause,whereargs )
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

    fun get_MonthList(currentYear: Int) {
        monthListview = findViewById(R.id.expense_monthview)
        _numberpicker = findViewById<NumberPicker>(R.id.expense_yarpicker)
        _numberpicker?.setMinValue(2015)
        _numberpicker?.setMaxValue(2030)
        _numberpicker?.setValue(currentYear)


        // var incomeDatalist = arrayListOf<IncomeData>()
        var str = "select * from monthlyIncome where date_added like '%$currentYear%'"

        var dbData = DataBaseHelper(applicationContext)
        var db = dbData.readableDatabase
        var incomeData = db.rawQuery(str , null)

        for (i in monthList.indices) {
            incomeData.moveToFirst()
            var obj: IncomeMonthData = IncomeMonthData()
            obj.month = monthList[i]

            var totalamount:Int=0
            val mon = monthList[i].substring(0,3).toString()


            for(i in 0 until incomeData.count) {
                if (incomeData.getString(2).contains(mon)) {

                    totalamount =  incomeData.getInt(1)
                    obj.itemAddedDate=incomeData.getString(2)
                    obj.itemId=incomeData.getInt(0)

                }
                incomeData.moveToNext()
            }



            obj.amount = totalamount
            monthArryList.add(obj)
        }




        mothlyincomeAdapter = MonthlyIncomeAdaptor(this, monthArryList)
        monthListview.adapter = mothlyincomeAdapter
        monthListview.onItemClickListener = this
    }
}
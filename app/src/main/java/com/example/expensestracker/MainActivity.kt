package com.example.expensestracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val add_btn: View = findViewById(R.id.add_btn)
        val add_income: View = findViewById(R.id.add_income)
        val add_expenses: View = findViewById(R.id.add_expenses)
        add_btn.setOnClickListener{
            onAddButtonClick()
        }

        add_income.setOnClickListener{
            Toast.makeText(this,"add_income buttom Clicked", Toast.LENGTH_SHORT).show()
        }

        add_expenses.setOnClickListener{
            Toast.makeText(this,"add_expenses edit buttom Clicked", Toast.LENGTH_SHORT).show()
        }

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
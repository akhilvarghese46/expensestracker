package com.example.expensestracker

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.security.AccessControlContext

class DataBaseHelper( context: Context) : SQLiteOpenHelper(context,"expensetraker.db",null,1){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE monthlyExpenses(_id integer primary key autoincrement,item_name TEXT,amount number,description text,date_added text,isRegular number )")
       db?.execSQL("CREATE TABLE monthlyIncome(_idIncome integer primary key autoincrement,income_amount number,date_added text )")
        //db?.execSQL("INSERT INTO monthlyExpenses(item_name,amount,description,date_added)values('Tesco',100,'banana,tea','25-aug-2021')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("drop table monthlyExpenses")
        db?.execSQL("drop table monthlyIncome")
        db?.execSQL("CREATE TABLE monthlyExpenses(_id integer primary key autoincrement,item_name TEXT,amount number,description text,date_added text,isRegular number )")
        db?.execSQL("CREATE TABLE monthlyIncome(_idIncome integer primary key autoincrement,income_amount number,date_added text )")
        //db?.execSQL("INSERT INTO monthlyExpenses(item_name,amount,description,date_added)values('DropTesco',100,'banana,tea',25-aug-2021)")

    }

}
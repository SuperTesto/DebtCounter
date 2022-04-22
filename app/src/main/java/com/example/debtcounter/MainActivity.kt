package com.example.debtcounter

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow



class MainActivity : AppCompatActivity() {

  private lateinit var buttonPlus: View

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    buttonPlus = findViewById(R.id.buttonPlus)

    buttonPlus.setOnClickListener() {
      addRow()
    }
  }

  @SuppressLint("InflateParams")
  private fun addRow() {
    val tl: TableLayout = findViewById(R.id.tableLayout)
    val inflater: LayoutInflater = LayoutInflater.from(this)
    val tr: TableRow = inflater.inflate(R.layout.table_row, null) as TableRow

    tl.addView(tr)
  }


}
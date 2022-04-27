package com.example.debtcounter

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import java.math.BigDecimal
import java.math.RoundingMode


class MainActivity : AppCompatActivity() {
  private lateinit var buttonPlus: View
  private lateinit var testBut: View

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    setContentView(R.layout.activity_main)

    buttonPlus = findViewById(R.id.buttonPlus)
    testBut = findViewById(R.id.button3)

    buttonPlus.setOnClickListener() {
      addRow()
    }

    findViewById<EditText>(R.id.editTextPrice).addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(s: Editable) {
        countAll()
      }
      override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
      override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })

    testBut.setOnClickListener() {
      countAll()
    }
  }

  private fun countAllTest() {
    val tl: TableLayout = findViewById(R.id.tableLayout)
    val tableRow: TableRow = tl.getChildAt(1) as TableRow
    val tableColumn: TextView = tableRow.getChildAt(2) as TextView
    val pluhs : Double = tableColumn.text.toString().toDouble()
    Log.d("pluhs", pluhs.toString())
  }

  private fun strToDouble(str : String) : Double {
    if (str != "") {
      return str.toDouble()
    } else {
      return 0.0
    }
  }

  private fun strToInt(str : String) : Int {
    if (str != "") {
      return str.toInt()
    } else {
      return 0
    }
  }
  
  private fun countAll() {
    val editTextPrice = findViewById<EditText>(R.id.editTextPrice)
    val textViewPluhAll = findViewById<TextView>(R.id.textViewPluhAll)
    val textViewPluhaCost = findViewById<TextView>(R.id.textViewPluhaCost)

    val price: Int = strToInt(editTextPrice.text.toString())

    val tl: TableLayout = findViewById(R.id.tableLayout)
    var pluhs : Double = 0.0

    for (i in 1 until tl.childCount) {
      val tableRow: TableRow = tl.getChildAt(i) as TableRow
      val tableColumn: TextView = tableRow.getChildAt(2) as TextView
      pluhs += strToDouble(tableColumn.text.toString())
    }
    if (pluhs == 0.0) {
      textViewPluhaCost.text =  "0"
    } else {
      textViewPluhAll.text = pluhs.toString()
      var priceOfPluha: Double = (price / pluhs)
      val bd: BigDecimal = BigDecimal(priceOfPluha).setScale(2, RoundingMode.HALF_EVEN)
      priceOfPluha = bd.toDouble()
      textViewPluhaCost.text = priceOfPluha.toString()
    }
  }

  @SuppressLint("InflateParams", "SetTextI18n")
  private fun addRow() {
    val tl: TableLayout = findViewById(R.id.tableLayout)
    val inflater: LayoutInflater = LayoutInflater.from(this)
    val tr: TableRow = inflater.inflate(R.layout.table_row, null) as TableRow

    val countOfPluhs: TextView = tr.getChildAt(2) as TextView

    countOfPluhs.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(s: Editable) {
        countAll()
      }
      override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
      override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })

    val minusButton: TextView = tr.getChildAt(1) as Button
    minusButton.setOnClickListener() {
      countOfPluhs.text = (strToDouble(countOfPluhs.text.toString()) - 0.5).toString()
      if (countOfPluhs.text.toString().toDouble() < 0)
        countOfPluhs.text = "0"
    }

    val plusButton: TextView = tr.getChildAt(3) as Button
    plusButton.setOnClickListener() {
      countOfPluhs.text = (strToDouble(countOfPluhs.text.toString()) + 0.5).toString()
      if (countOfPluhs.text.toString().toDouble() < 0)
        countOfPluhs.text = "0"
    }
    dialogSetName(tr)
    tl.addView(tr)
  }

  private fun dialogSetName(tr: TableRow) {
    val listener: CustomDialog.FullNameListener = CustomDialog.FullNameListener() { fullName ->
      tr.findViewById<TextView>(R.id.textView6).text = fullName
    }
    val dialog = CustomDialog(this, listener)
    dialog.show()
  }

}
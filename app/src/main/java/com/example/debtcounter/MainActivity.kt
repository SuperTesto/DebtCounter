package com.example.debtcounter

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.debtcounter.bd.BdHelper
import com.example.debtcounter.bd.BdManager
import org.w3c.dom.Text
import java.math.BigDecimal
import java.math.RoundingMode


class MainActivity : AppCompatActivity() {
  private lateinit var buttonPlus: View
  private val bdManager = BdManager(this)


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    setContentView(R.layout.activity_main)

    buttonPlus = findViewById(R.id.buttonAdd)

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

    //window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    bdManager.openBd()
    exportFromBd()
    countAll()
  }

  private fun exportFromBd() {
    val textViewItog = findViewById<EditText>(R.id.editTextPrice)
    textViewItog.setText(bdManager.readPriceTable().toString())
    val humans : ArrayList<Human> = bdManager.readHumanTable()
    for (human in humans) {
      addRowFromBd(human)
    }
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
  
  @SuppressLint("CutPasteId", "SetTextI18n")
  private fun countAll() {
    val editTextPrice = findViewById<EditText>(R.id.editTextPrice)
    val textViewPluhAll = findViewById<TextView>(R.id.textViewPluhAll)
    val textViewPluhaCost = findViewById<TextView>(R.id.textViewPluhaCost)

    val price: Int = strToInt(editTextPrice.text.toString())

    val tl: TableLayout = findViewById(R.id.tableLayout)
    var pluhs = 0.0

    for (i in 1 until tl.childCount) {
      val tableRow: TableRow = tl.getChildAt(i) as TableRow
      val countOfPluhs: EditText =  tableRow.findViewById<EditText>(R.id.editTextCountOfPluhs)
      pluhs += strToDouble(countOfPluhs.text.toString())
    }
    if (pluhs == 0.0) {
      textViewPluhaCost.text =  "0"
      textViewPluhAll.text = "0"
    }
    else {
      textViewPluhAll.text = pluhs.toString()
      val priceOfPluha: Double = (price / pluhs)
      var bd: BigDecimal = BigDecimal(priceOfPluha).setScale(2, RoundingMode.HALF_EVEN)
      textViewPluhaCost.text = bd.toDouble().toString()
      for (i in 1 until tl.childCount) {
        val tableRow: TableRow = tl.getChildAt(i) as TableRow
        val TextViewItog =  tableRow.findViewById<TextView>(R.id.textViewItog)
        val EditTextCountOfPluhs = tableRow.findViewById<EditText>(R.id.editTextCountOfPluhs)
        val itog : Double = strToDouble(EditTextCountOfPluhs.text.toString())* priceOfPluha
        bd = BigDecimal(itog).setScale(1, RoundingMode.HALF_EVEN)
        TextViewItog.text = bd.toDouble().toString()
      }
    }
  }

  @SuppressLint("InflateParams", "SetTextI18n")
  private fun addRowFromBd(human : Human) {
    val tl: TableLayout = findViewById(R.id.tableLayout)
    val inflater: LayoutInflater = LayoutInflater.from(this)
    val tr: TableRow = inflater.inflate(R.layout.table_row, null) as TableRow

    val textViewName = tr.findViewById<TextView>(R.id.textViewName)
    val textViewPluhs = tr.findViewById<EditText>(R.id.editTextCountOfPluhs)
    val textViewItog = tr.findViewById<TextView>(R.id.textViewItog)

    textViewName.text = human.getName()
    textViewPluhs.setText(human.getAmount().toString())
    textViewItog.text = human.getTotal().toString()

    textViewPluhs.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(s: Editable) {
        countAll()
      }
      override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
      override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })

    val minusButton: Button = tr.findViewById<Button>(R.id.buttonMinus)
    minusButton.setOnClickListener() {
      textViewPluhs.setText((strToDouble(textViewPluhs.text.toString()) - 0.5).toString())
      if (textViewPluhs.text.toString().toDouble() < 0)
        textViewPluhs.setText("0")
    }

    val plusButton: Button = tr.findViewById<Button>(R.id.buttonPlus)
    plusButton.setOnClickListener() {
      textViewPluhs.setText((strToDouble(textViewPluhs.text.toString()) + 0.5).toString())
      if (textViewPluhs.text.toString().toDouble() < 0)
        textViewPluhs.setText("0")
    }

    val delButton: ImageButton = tr.findViewById<ImageButton>(R.id.delButton)
    delButton.setOnClickListener() {
      tl.removeView(tr)
      countAll()
    }
    tl.addView(tr)
  }

  @SuppressLint("InflateParams", "SetTextI18n")
  private fun addRow() {
    val tl: TableLayout = findViewById(R.id.tableLayout)
    val inflater: LayoutInflater = LayoutInflater.from(this)
    val tr: TableRow = inflater.inflate(R.layout.table_row, null) as TableRow

    val countOfPluhs: TextView = tr.findViewById<TextView>(R.id.editTextCountOfPluhs)

    countOfPluhs.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(s: Editable) {
        countAll()
      }
      override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
      override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })

    val minusButton: Button = tr.findViewById<Button>(R.id.buttonMinus)
    minusButton.setOnClickListener() {
      countOfPluhs.text = (strToDouble(countOfPluhs.text.toString()) - 0.5).toString()
      if (countOfPluhs.text.toString().toDouble() < 0)
        countOfPluhs.text = "0"
    }

    val plusButton: Button = tr.findViewById<Button>(R.id.buttonPlus)
    plusButton.setOnClickListener() {
      countOfPluhs.text = (strToDouble(countOfPluhs.text.toString()) + 0.5).toString()
      if (countOfPluhs.text.toString().toDouble() < 0)
        countOfPluhs.text = "0"
    }

    val delButton: ImageButton = tr.findViewById<ImageButton>(R.id.delButton)
    delButton.setOnClickListener() {
      tl.removeView(tr)
      countAll()
    }
    dialogSetName(tr)
    tl.addView(tr)
  }
  
  private fun dialogSetName(tr: TableRow) {
    val listener: CustomDialog.FullNameListener = CustomDialog.FullNameListener() { fullName ->
      tr.findViewById<TextView>(R.id.textViewName).text = fullName
    }
    val dialog = CustomDialog(this, listener)
    dialog.show()
  }

  override fun onPause() {
    super.onPause()
    bdManager.clearBd()
    val tl: TableLayout = findViewById(R.id.tableLayout)

    for (i in 1 until tl.childCount) {
      val tr: TableRow = tl.getChildAt(i) as TableRow
      val name = tr.findViewById<TextView>(R.id.textViewName).text.toString()
      val pluhs : Double = strToDouble(tr.findViewById<EditText>(R.id.editTextCountOfPluhs).text.toString())
      val itog : Double = strToDouble(tr.findViewById<TextView>(R.id.textViewItog).text.toString())
      bdManager.insertToHumansTable(name, pluhs, itog)
    }
    val textViewItog = findViewById<EditText>(R.id.editTextPrice)
    bdManager.insertToPriceTable(strToInt(textViewItog.text.toString()))
    bdManager.closeBd()
  }

}
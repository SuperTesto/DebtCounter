package com.example.debtcounter.bd

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.debtcounter.Human

class BdManager (context: Context) {
  val bdHelper = BdHelper(context)
  var bd: SQLiteDatabase? = null

  fun openBd() {
    bd = bdHelper.writableDatabase
    //bdHelper.onUpgrade(bd!!, 1, 2)
  }

  fun insertToHumansTable(name: String, pluhs: Double, itog: Double, ) {
    bd = bdHelper.writableDatabase
    val values = ContentValues().apply {
      put(NameClass.HumansTable.COLUMN1, name)
      put(NameClass.HumansTable.COLUMN2, pluhs)
      put(NameClass.HumansTable.COLUMN3, itog)
    }
    bd?.insert(NameClass.HumansTable.TABLE_NAME, null, values)
  }

  fun insertToPriceTable(price: Int) {
    bd = bdHelper.writableDatabase
    val values = ContentValues().apply {
      put(NameClass.PriceTable.COLUMN1, price)
    }
    bd?.insert(NameClass.PriceTable.TABLE_NAME, null, values)
  }

  @SuppressLint("Range")
  fun readPriceTable() : Int {
    bd = bdHelper.readableDatabase
    val cursor = bd?.query(
      NameClass.PriceTable.TABLE_NAME,   // The table to query
      null,             // The array of columns to return (pass null to get all)
      null,              // The columns for the WHERE clause
      null,          // The values for the WHERE clause
      null,                   // don't group the rows
      null,                   // don't filter by row groups
      null               // The sort order
    )
    var price : Int = 0
    with (cursor) {
      while (this?.moveToNext()!!) {
        price = cursor?.getInt(cursor.getColumnIndex(NameClass.PriceTable.COLUMN1))!!
      }
    }
    cursor?.close()
    return price
  }

  @SuppressLint("Range")
  fun readHumanTable() : ArrayList<Human> {
    bd = bdHelper.readableDatabase
    //val projection = arrayOf(BaseColumns._ID, NameClass.COLUMN1, NameClass.COLUMN2, NameClass.COLUMN3)

    val cursor = bd?.query(
      NameClass.HumansTable.TABLE_NAME,   // The table to query
      null,             // The array of columns to return (pass null to get all)
      null,              // The columns for the WHERE clause
      null,          // The values for the WHERE clause
      null,                   // don't group the rows
      null,                   // don't filter by row groups
      null               // The sort order
    )

    val humans = ArrayList<Human>()

    with (cursor) {
      while (this?.moveToNext()!!) {
        val name = cursor?.getString(cursor.getColumnIndex(NameClass.HumansTable.COLUMN1))
        val pluhs = cursor?.getDouble(cursor.getColumnIndex(NameClass.HumansTable.COLUMN2))
        val itog = cursor?.getDouble(cursor.getColumnIndex(NameClass.HumansTable.COLUMN3))
        val human = Human(name!!, pluhs!!, itog!!)
        humans.add(human)
      }
    }
    cursor?.close()
    return humans
  }

  fun clearBd() {
    bd?.delete(NameClass.HumansTable.TABLE_NAME, null, null)
    bd?.delete(NameClass.PriceTable.TABLE_NAME, null, null)
  }

  fun closeBd() {
    bd?.close()
  }
}
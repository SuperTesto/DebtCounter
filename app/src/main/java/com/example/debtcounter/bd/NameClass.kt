package com.example.debtcounter.bd

import android.provider.BaseColumns

object NameClass {
  object HumansTable {
    const val TABLE_NAME = "persons"
    const val COLUMN1 = "name"
    const val COLUMN2 = "pluhs"
    const val COLUMN3 = "itog"
    const val SQL_CREATE_ENTRIES =
      "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
              "${BaseColumns._ID} INTEGER PRIMARY KEY," +
              "${COLUMN1} TEXT," +
              "${COLUMN2} REAL," +
              "${COLUMN3} REAL)"
    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
  }

  object PriceTable : BaseColumns {
    const val TABLE_NAME = "prices"
    const val COLUMN1 = "price"
    const val SQL_CREATE_ENTRIES =
      "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
              "${BaseColumns._ID} INTEGER PRIMARY KEY," +
              "${COLUMN1} INTEGER)"
    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
  }

}
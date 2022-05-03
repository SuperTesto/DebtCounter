package com.example.debtcounter.bd

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class BdHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
  override fun onCreate(db: SQLiteDatabase) {
    db.execSQL(NameClass.PriceTable.SQL_CREATE_ENTRIES)
    db.execSQL(NameClass.HumansTable.SQL_CREATE_ENTRIES)
    Log.d("create", "create")
  }
  override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    // This database is only a cache for online data, so its upgrade policy is
    // to simply to discard the data and start over
    db.execSQL(NameClass.HumansTable.SQL_DELETE_ENTRIES)
    db.execSQL(NameClass.PriceTable.SQL_DELETE_ENTRIES)
    onCreate(db)
  }
  override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    onUpgrade(db, oldVersion, newVersion)
  }
  companion object {
    // If you change the database schema, you must increment the database version.
    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "data_base.db"
  }
}


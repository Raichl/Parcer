package com.example.parcer.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper (context: Context) : SQLiteOpenHelper(context, DbNameClass.DATABASE_NAME,
    null, DbNameClass.DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DbNameClass.CREATE_TABLE_MAGICK)
        db?.execSQL(DbNameClass.CREATE_TABLE_MONSTER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DbNameClass.SQL_DELETE_TABLE_MAGICK)
        db?.execSQL(DbNameClass.SQL_DELETE_TABLE_MONSTER)
        onCreate(db)
    }

}
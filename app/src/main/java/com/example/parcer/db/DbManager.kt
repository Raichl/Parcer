package com.example.parcer.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.parcer.Magick
import com.example.parcer.Monster

class DbManager (context: Context){
    private val dbHelper = DbHelper(context)
    private var db: SQLiteDatabase? = null

    fun openDb(){
        db = dbHelper.writableDatabase
    }

    fun insertToDBMagick(magick: ArrayList<Magick>){
        for (index in magick.indices) {
            val values = ContentValues().apply {
                put(DbNameClass.COLUMN_NAME_MAGICK_NAME,magick[index].name)
                put(DbNameClass.COLUMN_NAME_MAGICK_SPELLLVL,magick[index].spellLvl)
                put(DbNameClass.COLUMN_NAME_MAGICK_RANGE,magick[index].range)
                put(DbNameClass.COLUMN_NAME_MAGICK_SCHOOL,magick[index].school)
                put(DbNameClass.COLUMN_NAME_MAGICK_TIME,magick[index].time)
                put(DbNameClass.COLUMN_NAME_MAGICK_COMPONENT,magick[index].component)
                put(DbNameClass.COLUMN_NAME_MAGICK_DURATION,magick[index].duration)
                put(DbNameClass.COLUMN_NAME_MAGICK_PERSCLASSES,magick[index].persClasses)
                put(DbNameClass.COLUMN_NAME_MAGICK_SOORCE,magick[index].soorce)
                put(DbNameClass.COLUMN_NAME_MAGICK_DESCRIPTION,magick[index].description)
            }
            db?.insert(DbNameClass.TABLE_NAME_MAGICK,null,values)
        }
    }

    fun insertToDBMonster(monster: ArrayList<Monster>){
        for (index in monster.indices){
            var stat = ""
            for(i in monster[index].stats.indices){
                stat += (monster[index].stats[i].name + " " + monster[index].stats[i].modif + ",")
            }
            val values = ContentValues().apply{
                put(DbNameClass.COLUMN_NAME_MONSTER_NAME,monster[index].name)
                put(DbNameClass.COLUMN_NAME_MONSTER_SIZE,monster[index].size)
                put(DbNameClass.COLUMN_NAME_MONSTER_TYPE,monster[index].type)
                put(DbNameClass.COLUMN_NAME_MONSTER_HITS,monster[index].hits)
                put(DbNameClass.COLUMN_NAME_MONSTER_KD,monster[index].kd)
                put(DbNameClass.COLUMN_NAME_MONSTER_SPEED,monster[index].speed)
                put(DbNameClass.COLUMN_NAME_MONSTER_STATS,stat)
                put(DbNameClass.COLUMN_NAME_MONSTER_SKILLS,monster[index].skills)
                put(DbNameClass.COLUMN_NAME_MONSTER_ACTIONANDDESCRIPTION,monster[index].actionAndDescription)
            }
            db?.insert(DbNameClass.TABLE_NAME_MONSTER,null,values)
        }

    }

    fun closeDb() {
        dbHelper.close()
    }
}
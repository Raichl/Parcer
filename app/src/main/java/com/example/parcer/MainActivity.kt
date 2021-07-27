package com.example.parcer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.example.parcer.db.DbManager
import java.io.IOException

data class Magick(val name : String, val spellLvl : String, val range : String, val school : String,
                  val time : String, val component : String, val duration : String ,
                  val persClasses : String, val soorce : String, val description : String )



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val tvTest : TextView = findViewById(R.id.textView)
        val btnTest : Button = findViewById(R.id.button)
        val progressBar : ProgressBar = findViewById(R.id.progressBar)
        val dbManager = DbManager(this)
        dbManager.openDb()
        btnTest.setOnClickListener {
            tvTest.text = ""
            progressBar.visibility = ProgressBar.VISIBLE

            Thread {
                try {
                    val spellArray = getDbMagick()
                    val monsterArray = getDbMonster()

                    dbManager.insertToDBMagick(spellArray)
                    dbManager.insertToDBMonster(monsterArray)
                    tvTest.text = "Ready"

                } catch (e: IOException) {
                    runOnUiThread{tvTest.text = "ERROR"}
                }
                runOnUiThread{
                    progressBar.visibility = ProgressBar.INVISIBLE}

            }.start()



        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val dbManager = DbManager(this)
        dbManager.closeDb()
    }
}

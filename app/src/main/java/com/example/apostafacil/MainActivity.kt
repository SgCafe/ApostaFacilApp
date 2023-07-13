package com.example.apostafacil

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*
import kotlin.math.sign

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Mega-Sena
        val megaEdt: EditText = findViewById(R.id.megaEditTxt)
        val megaNumbers: TextView = findViewById(R.id.megaNumbers)
        val megaBtn: Button = findViewById(R.id.megaBtn)

        megaBtn.setOnClickListener {
            val text = megaEdt.text.toString()
            ButtonMegaActive(text, megaNumbers)

        }

        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val resultMega = prefs.getString("result", "Nenhum registro salvo.")
        resultMega?.let {
            megaNumbers.text = "$resultMega"
        }

        //LotoFácil
        val lotoEdt: EditText = findViewById(R.id.lotoEditTxt)
        val lotoNumbers: TextView = findViewById(R.id.lotoNumbers)
        val lotoBtn: Button = findViewById(R.id.lotoBtn)

        lotoBtn.setOnClickListener {
            val text = lotoEdt.text.toString()
            ButtonLotoActive(text, lotoNumbers)
        }

        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val resultLoto = prefs.getString("result", "Nenhum registro salvo.")
        resultLoto?.let {
            lotoNumbers.text = "$resultLoto"
        }

        //Quina
        val quinaEdt: EditText = findViewById(R.id.quinaEditTxt)
        val quinaNumbers: TextView = findViewById(R.id.quinaNumbers)
        val quinaBtn: Button = findViewById(R.id.quinaBtn)

        quinaBtn.setOnClickListener {
            val text = quinaEdt.text.toString()
            ButtonQuinaActive(text, quinaNumbers)
        }

        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val resultQuina = prefs.getString("result", "Nenhum registro salvo.")
        resultQuina?.let {
            quinaNumbers.text = "$resultQuina"
        }
    }

    private fun ButtonMegaActive(text: String, txtResult: TextView) {
        if (text.isEmpty()) {
            Toast.makeText(this, "Valor inválido, digite outro.", Toast.LENGTH_LONG).show()
            return
        }

        val qnt = text.toInt()
        if (qnt < 6 || qnt > 15) {
            Toast.makeText(this, "Digite um valor entre 6 e 15.", Toast.LENGTH_LONG).show()
            return
        }

        val random = Random()
        val list = mutableSetOf<Int>()

        while (true) {
            val number = random.nextInt(60)
            list.add(number + 1)

            if (list.size == qnt) {
                break
            }
        }

        txtResult.text = list.joinToString(" | ")

        prefs.edit().apply {
            putString("result", txtResult.text.toString())
            apply()
        }
    }

    private fun ButtonLotoActive(text: String, txtResult: TextView) {
        if (text.isEmpty()) {
            Toast.makeText(this, "Valor inválido, digite outro.", Toast.LENGTH_LONG).show()
            return
        }

        val qnt = text.toInt()
        if (qnt < 15 || qnt > 18) {
            Toast.makeText(this, "Digite um valor entre 15 e 18.", Toast.LENGTH_LONG).show()
            return
        }

        val random = Random()
        val list = mutableSetOf<Int>()

        while (true) {
            val number = random.nextInt(25)
            list.add(number + 1)

            if (list.size == qnt) {
                break
            }
        }

        txtResult.text = list.joinToString(" | ")

        prefs.edit().apply {
            putString("result", txtResult.text.toString())
            apply()
        }
    }

    private fun ButtonQuinaActive(text: String, txtResult: TextView) {
        if (text.isEmpty()) {
            Toast.makeText(this, "Valor inválido, digite outro.", Toast.LENGTH_LONG).show()
            return
        }

        val qnt = text.toInt()
        if (qnt < 5 || qnt > 15) {
            Toast.makeText(this, "Digite um valor entre 5 e 15.", Toast.LENGTH_LONG).show()
            return
        }

        val random = Random()
        val list = mutableSetOf<Int>()

        while (true) {
            val number = random.nextInt(80)
            list.add(number + 1)

            if (list.size == qnt) {
                break
            }
        }

        txtResult.text = list.joinToString(" | ")

        prefs.edit().apply {
            putString("result", txtResult.text.toString())
            apply()
        }
    }
}
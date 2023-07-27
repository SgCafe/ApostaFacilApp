package com.example.apostafacil

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    private lateinit var rvMain: RecyclerView

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)

        val listCards = mutableListOf<MainItem>()
        listCards.add(
            MainItem(
                1,
                R.drawable.megaimg,
                R.string.megaNome,
                R.color.greenBack,
                R.color.green500,
                R.string.InfoNumberMega
            )
        )
        listCards.add(
            MainItem(
                2,
                R.drawable.lotoimg,
                R.string.Lotofacil,
                R.color.pink300,
                R.color.pink500,
                R.string.InfoNumberLoto
            )
        )
        listCards.add(
            MainItem(
                3,
                R.drawable.quinaimg,
                R.string.Quina,
                R.color.purple300,
                R.color.purple500,
                R.string.InfoNumberQuina
            )
        )

        val adapter = MainAdapter(listCards) { id ->
            when (id) {
                1 -> {
                    val entryTxtMega: EditText = findViewById(R.id.txt_aposta)
                    val respMega: TextView = findViewById(R.id.numeros_sorteados)

                    val text = entryTxtMega.text.toString()
                    ButtonMegaActive(text, respMega)

                    val resultMega = prefs.getString("resultUm", "Nenhum registro salvo.")
                    resultMega?.let {
                        respMega.text = "$resultMega"
                    }
                }
                2 -> {
                    val entryTxtLoto: EditText = findViewById(R.id.txt_aposta)
                    val respLoto: TextView = findViewById(R.id.numeros_sorteados)

                    val txtLoto = entryTxtLoto.text.toString()
                    ButtonLotoActive(txtLoto, respLoto)

                    val resultLoto = prefs.getString("resultDois", "Nenhum registro salvo.")
                    resultLoto?.let {
                        respLoto.text = "$resultLoto"
                    }
                }
                3 -> {
                    val extryTxtQuina: EditText = findViewById(R.id.txt_aposta)
                    val respQuina: TextView = findViewById(R.id.numeros_sorteados)

                    val txtQuina = extryTxtQuina.text.toString()
                    ButtonQuinaActive(txtQuina, respQuina)

                    val resultQuina = prefs.getString("resultTres", "Nenhum registro salvo.")
                    resultQuina?.let {
                        respQuina.text = "$resultQuina"
                    }
                }
            }
        }
        rvMain = findViewById(R.id.rv_main)
        rvMain.adapter = adapter
        rvMain.layoutManager = LinearLayoutManager(this)
    }

    private inner class MainAdapter(
        private val listCards: List<MainItem>,
        private val onItemClickListener: (Int) -> Unit,
    ) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.main_item, parent, false)
            val viewHolder = MainViewHolder(view)
            return viewHolder
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemCurrent = listCards[position]
            holder.bind(itemCurrent)
        }

        override fun getItemCount(): Int {
            return listCards.size
        }

        private inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(item: MainItem) {
                val img: ImageView = itemView.findViewById(R.id.img_card)
                val nomeImg: TextView = itemView.findViewById(R.id.txt_img)
                val txtAposta: TextView = itemView.findViewById(R.id.txt_aposta)
                val colorContainer: CardView = itemView.findViewById(R.id.item_container_bg)
                val colorBgMiniCards: CardView = itemView.findViewById(R.id.item_mini_card)
                val colorBtn: Button = itemView.findViewById(R.id.btn_colors)

                img.setImageResource(item.img)
                nomeImg.setText(item.textImg)
                txtAposta.setHint(item.textAposta)
                colorContainer.setCardBackgroundColor(ContextCompat.getColor(itemView.context, item.colorBG))
                colorBgMiniCards.setCardBackgroundColor(ContextCompat.getColor(itemView.context, item.colorBtn))
                colorBtn.setBackgroundColor(ContextCompat.getColor(itemView.context, item.colorBtn))

                colorBtn.setOnClickListener {
                    onItemClickListener.invoke(item.id)
                }
            }
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
            putString("resultUm", txtResult.text.toString())
            apply()
        }
    }

    private fun ButtonLotoActive(text: String, txtResult: TextView) {
        if (text.isEmpty()) {
            Toast.makeText(this, "Valor inválido, digite outro.", Toast.LENGTH_LONG).show()
            return
        }

        val qnt = text.toInt()
        if (qnt < 5 || qnt > 18) {
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
            putString("resultDois", txtResult.text.toString())
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
            putString("resultTres", txtResult.text.toString())
            apply()
        }
    }
}
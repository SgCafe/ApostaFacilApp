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

        val adapter = MainAdapter(listCards) { id, text ->
            return@MainAdapter when (id) {
                1 -> {
                    ButtonMegaActive(id, text)
                }
                2-> {
                    ButtonLotoActive(id, text)
                }
                else -> {
                    ButtonQuinaActive(id, text)
                }
            }
        }
        rvMain = findViewById(R.id.rv_main)
        rvMain.adapter = adapter
        rvMain.layoutManager = LinearLayoutManager(this)
    }

    private inner class MainAdapter(
        private val listCards: List<MainItem>,
        private val onItemClickListener: (Int, String) -> String?,
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
                val txtResponse: TextView = itemView.findViewById(R.id.numeros_sorteados)
                val colorContainer: CardView = itemView.findViewById(R.id.item_container_bg)
                val colorBgMiniCards: CardView = itemView.findViewById(R.id.item_mini_card)
                val colorBtn: Button = itemView.findViewById(R.id.btn_colors)

                img.setImageResource(item.img)
                nomeImg.setText(item.textImg)
                txtAposta.setHint(item.textAposta)
                colorContainer.setCardBackgroundColor(ContextCompat.getColor(itemView.context, item.colorBG))
                colorBgMiniCards.setCardBackgroundColor(ContextCompat.getColor(itemView.context, item.colorBtn))
                colorBtn.setBackgroundColor(ContextCompat.getColor(itemView.context, item.colorBtn))

                val res = prefs.getString(item.id.toString(), "Nenhum registro salvo.")
                res?.let {
                    txtResponse.text = it
                }

                colorBtn.setOnClickListener {
                    val result = onItemClickListener.invoke(item.id, txtAposta.text.toString())
                    txtResponse.text = result
                }
            }
        }
    }

    private fun ButtonMegaActive(id: Int, text: String): String? {
        if (text.isEmpty()) {
            Toast.makeText(this, "Valor inválido, digite outro.", Toast.LENGTH_LONG).show()
            return null
        }

        val qnt = text.toInt()
        if (qnt < 6 || qnt > 15) {
            Toast.makeText(this, "Digite um valor entre 6 e 15.", Toast.LENGTH_LONG).show()
            return null
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

        val result = list.joinToString(" | ")

        prefs.edit().apply {
            putString(id.toString(), result)
            apply()
        }
        return result
    }

    private fun ButtonLotoActive(id: Int, text: String): String? {
        if (text.isEmpty()) {
            Toast.makeText(this, "Valor inválido, digite outro.", Toast.LENGTH_LONG).show()
            return null
        }

        val qnt = text.toInt()
        if (qnt < 5 || qnt > 18) {
            Toast.makeText(this, "Digite um valor entre 15 e 18.", Toast.LENGTH_LONG).show()
            return null
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

        val result = list.joinToString(" | ")

        prefs.edit().apply {
            putString(id.toString(), result)
            apply()
        }
        return result
    }

    private fun ButtonQuinaActive(id: Int, text: String): String? {
        if (text.isEmpty()) {
            Toast.makeText(this, "Valor inválido, digite outro.", Toast.LENGTH_LONG).show()
            return null
        }

        val qnt = text.toInt()
        if (qnt < 5 || qnt > 15) {
            Toast.makeText(this, "Digite um valor entre 5 e 15.", Toast.LENGTH_LONG).show()
            return null
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

        val result = list.joinToString(" | ")

        prefs.edit().apply {
            putString(id.toString(), result)
            apply()
        }
        return result
    }
}
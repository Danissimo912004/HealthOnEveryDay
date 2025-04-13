package com.example.healthoneveryday

import AdviceAdapter
import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adviceList = getShuffledAdviceForToday(this)
        val adapter = AdviceAdapter(this, adviceList)
        recyclerView.adapter = adapter
    }

    fun getShuffledAdviceForToday(context: Context): List<AdviceCard> {
        val prefs = context.getSharedPreferences("advice_prefs", Context.MODE_PRIVATE)

        val calendar = Calendar.getInstance()
        val today = "${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH)}-${calendar.get(Calendar.DAY_OF_MONTH)}"

        val savedDate = prefs.getString("date", null)

        return if (savedDate == today) {
            val order = prefs.getString("order", null)
            val indices = order?.split(",")?.mapNotNull { it.toIntOrNull() } ?: (0 until 30).toList()
            val all = AdviceRepository.getAllCards()
            indices.map { all[it] }
        } else {
            val all = AdviceRepository.getAllCards()
            val indices = all.indices.shuffled()
            val order = indices.joinToString(",")

            prefs.edit()
                .putString("date", today)
                .putString("order", order)
                .apply()

            indices.map { all[it] }
        }
    }

}

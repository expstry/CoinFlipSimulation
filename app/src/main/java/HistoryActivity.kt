package com.example.coinflipsimulation

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class HistoryActivity : AppCompatActivity() {

    private lateinit var clearHistoryButton: Button
    private lateinit var historyListView: ListView
    private val history = mutableListOf<Pair<String, List<String>>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        // Инициализация элементов
        clearHistoryButton = findViewById(R.id.clearHistoryButton)
        historyListView = findViewById(R.id.historyListView)

        // Получаем данные из Intent
        val gameHistory = intent.getSerializableExtra("gameHistory") as? List<Pair<String, List<String>>>
        if (gameHistory != null) {
            history.addAll(gameHistory)
        }

        // Настройка адаптера для списка истории
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, history.map { it.first })
        historyListView.adapter = adapter

        // Очистка истории
        clearHistoryButton.setOnClickListener {
            history.clear()
            adapter.clear()
            adapter.notifyDataSetChanged()
        }

        // Открытие деталей игры
        historyListView.setOnItemClickListener { _, _, position, _ ->
            val selectedGame = history[position]
            val intent = Intent(this, GameDetailsActivity::class.java)
            intent.putExtra("gameDetails", ArrayList(selectedGame.second))
            startActivity(intent)
        }
    }
}

package com.example.coinflipsimulation

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class HistoryActivity : AppCompatActivity() {

    private lateinit var clearHistoryButton: Button
    private lateinit var backButton: Button
    private lateinit var historyListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        clearHistoryButton = findViewById(R.id.clearHistoryButton)
        historyListView = findViewById(R.id.historyListView)
        backButton = findViewById(R.id.backButton)

        backButton.setOnClickListener {
            onBackPressed()
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, GlobalVariables.gameHistory.map { it.first })
        historyListView.adapter = adapter


        clearHistoryButton.setOnClickListener {
            GlobalVariables.gameHistory.clear()
            adapter.clear()
            adapter.notifyDataSetChanged()
        }

        historyListView.setOnItemClickListener { _, _, position, _ ->
            val selectedGame = GlobalVariables.gameHistory[position]
            val intent = Intent(this, GameDetailsActivity::class.java)
            intent.putExtra("gameDetails", ArrayList(selectedGame.second))
            startActivity(intent)
        }

    }
}

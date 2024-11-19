package com.example.coinflipsimulation

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class GameDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)

        // Инициализация ListView
        val gameDetailsListView: ListView = findViewById(R.id.gameDetailsListView)

        // Получаем данные из Intent
        val gameDetails = intent.getStringArrayListExtra("gameDetails") ?: ArrayList()

        // Настраиваем адаптер
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, gameDetails)
        gameDetailsListView.adapter = adapter
    }
}

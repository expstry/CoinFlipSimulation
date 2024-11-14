package com.example.coinflipsimulation

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        // Получаем подробную историю из Intent
        val flipHistory = intent.getStringArrayListExtra("flipHistory") ?: ArrayList()

        // ListView для отображения истории
        val listView: ListView = findViewById(R.id.historyListView)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, flipHistory)
        listView.adapter = adapter
    }
}

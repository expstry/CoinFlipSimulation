package com.example.coinflipsimulation

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var resultText: TextView
    private lateinit var coinImage: ImageView
    private lateinit var inputCount: EditText
    private lateinit var flipButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация Views
        resultText = findViewById(R.id.resultText)
        coinImage = findViewById(R.id.coinImage)
        inputCount = findViewById(R.id.inputCount)
        flipButton = findViewById(R.id.flipButton)

        flipButton.setOnClickListener {
            flipCoin()
        }
    }

    // Функция для подбрасывания монеты
    private fun flipCoin() {
        val count = inputCount.text.toString().toIntOrNull() ?: 1
        var headsCount = 0
        var tailsCount = 0

        for (i in 1..count) {
            if (Random.nextBoolean()) {
                headsCount++
                coinImage.setImageResource(R.drawable.coin_head) // Замените на ресурс орел
            } else {
                tailsCount++
                coinImage.setImageResource(R.drawable.coin_tail) // Замените на ресурс решка
            }
        }

        // Отображение результатов
        resultText.text = "Результат: $headsCount орлов, $tailsCount решек"
    }
}

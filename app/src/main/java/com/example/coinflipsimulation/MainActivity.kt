package com.example.coinflipsimulation

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
    private lateinit var historyButton: Button

    private val headsDrawableId = R.drawable.coin_head
    private val tailsDrawableId = R.drawable.coin_tail
    private val handler = Handler(Looper.getMainLooper())

    // История с порядком выпадения
    private val history = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация Views
        resultText = findViewById(R.id.resultText)
        coinImage = findViewById(R.id.coinImage)
        inputCount = findViewById(R.id.inputCount)
        flipButton = findViewById(R.id.flipButton)
        historyButton = findViewById(R.id.historyButton)

        flipButton.setOnClickListener {
            flipCoin()
        }

        historyButton.setOnClickListener {
            // Переход в Activity для просмотра истории
            val intent = Intent(this, HistoryActivity::class.java)
            intent.putStringArrayListExtra("flipHistory", ArrayList(history)) //  полная история
            startActivity(intent)
        }
    }

    private fun flipCoin() {
        val count = inputCount.text.toString().toIntOrNull() ?: 1
        var headsCount = 0
        var tailsCount = 0

        val results = mutableListOf<String>()
        for (i in 1..count) {
            val result = if (Random.nextBoolean()) {
                headsCount++
                "Орел"
            } else {
                tailsCount++
                "Решка"
            }
            results.add(result)
        }

        val resultText = "Результат: $headsCount орлов, $tailsCount решек"
        this.resultText.text = resultText

        //   результат в историю
        for (i in results.indices) {
            history.add("Бросок ${i + 1}: ${results[i]}")
        }

        // анимация монеты
        coinImage.setBackgroundResource(R.drawable.coin_flip_animation)
        coinImage.setImageResource(0)

        val coinAnimation = coinImage.background as AnimationDrawable
        coinAnimation.start()

        handler.postDelayed({
            coinAnimation.stop()
            val finalResult = if (headsCount > tailsCount) headsDrawableId else tailsDrawableId
            coinImage.setBackgroundResource(finalResult)  //  итоговый результат
        }, (coinAnimation.numberOfFrames * 50).toLong())
    }
}

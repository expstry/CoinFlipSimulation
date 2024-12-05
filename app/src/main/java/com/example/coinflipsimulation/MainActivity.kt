package com.example.coinflipsimulation

import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.random.Random

object GlobalVariables {
    private const val GAME_HISTORY_KEY = "gameHistory"

    val gameHistory = mutableListOf<Pair<String, List<String>>>()

    fun saveGameHistory(context: Context) {
        val sharedPreferences = context.getSharedPreferences("CoinFlipApp", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(gameHistory)
        editor.putString(GAME_HISTORY_KEY, json)
        editor.apply()
    }

    fun loadGameHistory(context: Context) {
        val sharedPreferences = context.getSharedPreferences("CoinFlipApp", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString(GAME_HISTORY_KEY, null)
        val type = object : TypeToken<MutableList<Pair<String, List<String>>>>() {}.type

        if (json != null) {
            val loadedHistory: MutableList<Pair<String, List<String>>> = gson.fromJson(json, type)
            gameHistory.clear()
            gameHistory.addAll(loadedHistory)
        }
    }
}



class MainActivity : AppCompatActivity() {

    private lateinit var resultText: TextView
    private lateinit var coinImage: ImageView
    private lateinit var inputCount: EditText
    private lateinit var flipButton: Button
    private lateinit var historyButton: Button

    private val headsDrawableId = R.drawable.coin_head
    private val tailsDrawableId = R.drawable.coin_tail
    private val handler = Handler(Looper.getMainLooper())

    private lateinit var mediaPlayer: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalVariables.loadGameHistory(this)

        mediaPlayer = MediaPlayer.create(this, R.raw.coin_sound)
        resultText = findViewById(R.id.resultText)
        coinImage = findViewById(R.id.coinImage)
        inputCount = findViewById(R.id.inputCount)
        flipButton = findViewById(R.id.flipButton)
        historyButton = findViewById(R.id.historyButton)

        flipButton.setOnClickListener {
            flipCoin()

            mediaPlayer.start()
        }

        historyButton.setOnClickListener {

            val intent = Intent(this, HistoryActivity::class.java)
            intent.putExtra("gameHistory", ArrayList(GlobalVariables.gameHistory)) // Передаём полную историю
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        GlobalVariables.saveGameHistory(this)
        mediaPlayer.release()

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
            results.add("Бросок $i: $result")
        }

        val resultText = "Игра: $headsCount орлов, $tailsCount решек"
        this.resultText.text = resultText

        GlobalVariables.gameHistory.add(Pair(resultText, results))

        GlobalVariables.saveGameHistory(this)
        coinImage.setBackgroundResource(R.drawable.coin_flip_animation)
        coinImage.setImageResource(0)

        val coinAnimation = coinImage.background as AnimationDrawable
        coinAnimation.start()

        handler.postDelayed({
            coinAnimation.stop()
            val finalResult = if (headsCount > tailsCount) headsDrawableId else tailsDrawableId
            coinImage.setBackgroundResource(finalResult)
        }, (coinAnimation.numberOfFrames * 50).toLong())
    }
}

package com.example.coinflipsimulation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val startGameButton: Button = findViewById(R.id.startGameButton)
        startGameButton.setOnClickListener {
            // Переход в MainActivity при нажатии на кнопку
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        }

    }


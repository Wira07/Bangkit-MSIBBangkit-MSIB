package com.example.latihan1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bangkit_1)

        val nameEditText: EditText = findViewById(R.id.NameEditText)
        val button: Button = findViewById(R.id.Button)
        val textView: TextView = findViewById(R.id.TextView)

        textView.text = "hai"

        button.setOnClickListener {
            val name = nameEditText.text.toString()
            textView.text = "hai $ name"
        }

    }

}
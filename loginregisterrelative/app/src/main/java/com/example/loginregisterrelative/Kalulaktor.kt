package com.example.loginregisterrelative

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.loginregisterrelative.R

class Kalulaktor : AppCompatActivity() {

    private lateinit var editTextResult: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextResult = findViewById(R.id.editTextResult)
    }

    fun onButtonClick(view: View) {
        val button = view as Button
        val buttonText = button.text.toString()
        val currentText = editTextResult.text.toString()

        if (buttonText == "=") {
            // Evaluasi ekspresi matematika
            try {
                val result = evaluateExpression(currentText)
                editTextResult.setText(result.toString())
            } catch (e: Exception) {
                editTextResult.setText("Error")
            }
        } else {
            // Tambahkan teks tombol ke EditText
            editTextResult.setText("$currentText$buttonText")
        }
    }

    private fun evaluateExpression(expression: String): Double {
        // Kode untuk mengevaluasi ekspresi matematika, misalnya menggunakan JavaScript engine atau evaluasi manual.
        // Di sini, Anda dapat menggunakan metode yang sesuai untuk mengevaluasi ekspresi matematika.
        // Contoh: menggunakan JavaScript engine Rhino (tidak direkomendasikan untuk produksi):
        // return ScriptEngineManager().getEngineByName("rhino").eval(expression) as Double
        // Catatan: penggunaan JavaScript engine harus berhati-hati untuk keamanan aplikasi.
        // Pilihan lain adalah mengimplementasikan evaluator sendiri dengan parsing dan evaluasi ekspresi matematika.
        // Namun, implementasi ini di luar cakupan contoh sederhana ini.
        // Dalam proyek nyata, perlu diimplementasikan dengan lebih cermat dan aman.
        return 0.0
    }
}

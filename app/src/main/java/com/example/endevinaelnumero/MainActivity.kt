package com.example.endevinaelnumero

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val numeroUsuario = findViewById<EditText>(R.id.editTextNumber)
        val respuestas = findViewById<TextView>(R.id.textView2)
        val b = findViewById<Button>(R.id.button2)

        var todasRespuestas = ""

        var numeroAdivinar = Random.nextInt(1,100)
        android.util.Log.i("INFO", "El número secreto es: " + numeroAdivinar)

        var contador = 0
        b.setOnClickListener {
            contador += 1

            val respuestaUsuario = numeroUsuario.text.toString()

            var text = ""
            val duration = Toast.LENGTH_SHORT
            val numero = respuestaUsuario.toIntOrNull() ?: 0

            if (numero < 1 || numero > 100) {
                todasRespuestas += "Numero invalido, pon uno del 1 al 100"
                respuestas.text = todasRespuestas
            } else if (numero == numeroAdivinar) {
                val builder = android.app.AlertDialog.Builder(this)
                builder.setTitle("¡Felicidades!")
                builder.setMessage("Has adivinado el número en " + contador + " intentos.")
                builder.setCancelable(false) // Para que no pueda cerrar la ventana tocando fuera
                builder.setPositiveButton("Volver a jugar") { dialog, which ->
                    numeroAdivinar = Random.nextInt(1,100)
                    android.util.Log.i("INFO", "El número secreto es: " + numeroAdivinar)
                    contador = 0
                    todasRespuestas = ""
                    respuestas.text = ""
                }
                builder.show()
            } else if (numeroAdivinar > numero) {
                todasRespuestas += respuestaUsuario + ", El numero es mayor\n"
                respuestas.text = todasRespuestas
            } else {
                todasRespuestas += respuestaUsuario + ", El numero es menor\n"
                respuestas.text = todasRespuestas
            }
            numeroUsuario.setText("")
        }
    }
}
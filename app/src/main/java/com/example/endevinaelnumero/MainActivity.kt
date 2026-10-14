package com.example.endevinaelnumero

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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

        // cojo los widgets del xml con su id (val es como una variable que no cambia)
        val numeroUsuario = findViewById<EditText>(R.id.editTextNumber) // caja donde escribe el numero
        val respuestas = findViewById<TextView>(R.id.textView2) // texto donde sale el historial
        val b = findViewById<Button>(R.id.button2) // boton Endevina

        // aqui voy guardando todo el historial de intentos para enseñarlo
        var todasRespuestas = ""

        // si el numero todavia es 0 (primera vez) genero uno aleatorio
        if (numeroAdivinar == 0) {
            // el 101 es porque el ultimo numero no entra (1 a 100)
            numeroAdivinar = Random.nextInt(1, 101)
            // Log en vez de print, para verlo en el Logcat
            android.util.Log.i("INFO", "El número secreto es: " + numeroAdivinar)
        }

        // cuenta los intentos del jugador
        var contador = 0

        // esto se ejecuta cada vez que se pulsa el boton
        b.setOnClickListener {
            contador += 1 // un intento mas

            // cojo lo que ha escrito y lo paso a texto
            val respuestaUsuario = numeroUsuario.text.toString()
            // lo convierto a numero, si esta vacio o falla pongo 0 (el ?: es como un "si es null usa esto")
            val numero = respuestaUsuario.toIntOrNull() ?: 0

            if (numero < 1 || numero > 100) {
                // numero fuera de rango, aviso en el historial
                todasRespuestas += "Numero invalido, pon uno del 1 al 100\n"
                respuestas.text = todasRespuestas
            } else if (numero == numeroAdivinar) {
                // ha acertado
                // guardo los intentos en otra variable porque el contador se pone a 0 al reiniciar
                val intentosFinales = contador

                // creo una caja de texto por codigo para que escriba su nombre
                val cajaNombre = EditText(this)
                cajaNombre.hint = "Tu nombre" // texto gris de ayuda

                // preparo la ventana emergente (AlertDialog)
                val builder = android.app.AlertDialog.Builder(this)
                builder.setTitle("¡Felicidades!")
                builder.setMessage("Has adivinado el número en " + intentosFinales + " intentos.\n¿Quieres guardar tu récord?")
                builder.setView(cajaNombre) // meto la caja de nombre dentro de la ventana
                builder.setCancelable(false) // para que no se cierre tocando fuera

                // boton de guardar el record
                builder.setPositiveButton("Guardar") { dialog, which ->
                    val nombre = cajaNombre.text.toString()

                    // reinicio la partida: nuevo numero y todo a cero
                    numeroAdivinar = Random.nextInt(1, 101)
                    android.util.Log.i("INFO", "El número secreto es: " + numeroAdivinar)
                    contador = 0
                    todasRespuestas = ""
                    respuestas.text = ""

                    // abro la pantalla de records con un Intent
                    val intent = Intent(this, RecordsActivity::class.java)
                    // putExtra sirve para pasarle datos a la otra pantalla
                    intent.putExtra("nombre", nombre)
                    intent.putExtra("intentos", intentosFinales)
                    startActivity(intent)
                }

                // boton para no guardar el record
                builder.setNegativeButton("No, gracias") { dialog, which ->
                    // solo reinicio la partida (esto es lo mismo que arriba, esta repetido)
                    numeroAdivinar = Random.nextInt(1, 101)
                    android.util.Log.i("INFO", "El número secreto es: " + numeroAdivinar)
                    contador = 0
                    todasRespuestas = ""
                    respuestas.text = ""
                }

                builder.show() // enseño la ventana
            } else if (numeroAdivinar > numero) {
                // el numero que busca es mas grande que el que ha puesto
                todasRespuestas += respuestaUsuario + ", El numero es mayor\n"
                respuestas.text = todasRespuestas
            } else {
                // si no es ninguna de las anteriores, es mas pequeño
                todasRespuestas += respuestaUsuario + ", El numero es menor\n"
                respuestas.text = todasRespuestas
            }

            // borro la caja para que no tenga que borrarlo a mano
            numeroUsuario.setText("")
        }
    }

    companion object {
        var numeroAdivinar = 0
    }
}
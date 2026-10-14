package com.example.endevinaelnumero

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// esta es la segunda pantalla, la del Hall of Fame
class RecordsActivity : AppCompatActivity() {

    // se ejecuta al abrir la pantalla
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_records) // carga el xml de esta pantalla
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // cojo la tabla y el boton del xml
        val tabla = findViewById<TableLayout>(R.id.tablaRecords)
        val botonVolver = findViewById<Button>(R.id.buttonVolver)

        // recojo lo que me ha pasado el MainActivity con putExtra
        // el 0 es el valor por defecto si no llega nada
        val nombre = intent.getStringExtra("nombre")
        val intentos = intent.getIntExtra("intentos", 0)

        // si viene un nombre lo añado a la lista
        // (si abro esta pantalla sin jugar, nombre es null y no se añade nada)
        if (nombre != null) {
            if (nombre == "") {
                // si no ha escrito nada le pongo Anonimo
                records.add(Record("Anonimo", intentos))
            } else {
                records.add(Record(nombre, intentos))
            }
        }

        // ordeno la lista de menos a mas intentos, porque cuantos menos intentos mejor
        // "it" es cada record de la lista
        records.sortBy { it.intentos }

        // recorro la lista y creo una fila por cada record
        for (r in records) {
            // creo la fila por codigo, no esta en el xml
            val fila = TableRow(this)

            // texto del nombre
            val textoNombre = TextView(this)
            textoNombre.text = r.nombre
            textoNombre.textSize = 20f // la f es porque tiene que ser un Float
            textoNombre.setPadding(16, 8, 16, 8) // espacio por dentro del texto

            // texto de los intentos
            val textoIntentos = TextView(this)
            textoIntentos.text = r.intentos.toString() // hay que pasarlo a String
            textoIntentos.textSize = 20f
            textoIntentos.setPadding(16, 8, 16, 8)
            textoIntentos.gravity = Gravity.CENTER // lo centro en la columna

            // meto los dos textos dentro de la fila
            fila.addView(textoNombre)
            fila.addView(textoIntentos)

            // y la fila dentro de la tabla
            tabla.addView(fila)
        }

        // boton para volver al juego
        botonVolver.setOnClickListener {
            finish() // cierra esta pantalla y vuelve a la anterior
        }
    }

    // la lista esta aqui para que no se borre al cambiar de pantalla (como un static)
    companion object {
        var records = ArrayList<Record>()
    }
}

// clase para guardar los datos de cada record (nombre e intentos)
// va fuera de RecordsActivity, despues de la llave que la cierra
class Record(val nombre: String, val intentos: Int)
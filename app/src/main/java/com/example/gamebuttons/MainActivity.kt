package com.example.gamebuttons

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gamebuttons.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var estadoBotones: Array<IntArray> // inicializamos un array en paralelo para q marque los botones coloreados

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // iniciamos el binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Iniciar el estado de los botones
        estadoBotones = Array(3) { IntArray(3) { 0 } }

        // iniciar listeners en cada boton
        inicializarListeners()

        // boton de reiniciar el juego
        binding.refreshButton.setOnClickListener {
            reinicarBotones()
        }
    }

    // iniciar listeners para cada boton en el 3x3 grid
    private fun inicializarListeners() {
        val buttons = listOf(
            listOf(binding.button00, binding.button01, binding.button02),
            listOf(binding.button10, binding.button11, binding.button12),
            listOf(binding.button20, binding.button21, binding.button22)
        )

        for (i in buttons.indices) {
            for (j in buttons[i].indices) {
                buttons[i][j].setOnClickListener {
                    // cambiar color del botón clicado y sus adyacentes
                    cambiarcolorBotonesAdyacentes(buttons, i, j)

                    // comprobar si hay victoria
                    if (comprobarVictoria(estadoBotones)) {
                        Toast.makeText(applicationContext, "HAS GANADO!! OMG", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    // comprobamos si todos los botones estan coloreados a rojo
    private fun comprobarVictoria(estadoBotones: Array<IntArray>): Boolean {
        for (row in estadoBotones) {
            for (estado in row) {
                if (estado != 1) {
                    return false
                }
            }
        }
        return true // victoria
    }

    // cambiar botones adyacentes
    private fun cambiarcolorBotonesAdyacentes(buttons: List<List<Button>>, i: Int, j: Int) {
        // cambiamos el color del botón clickeado !! (REVISAR SI FUNCIONA)
        toggleButtonColor(buttons[i][j], i, j)

        // Lista de las posiciones adyacentes
        val posicionesAdyacentes = listOf(
            Pair(i - 1, j), // arriba
            Pair(i + 1, j), // abajo
            Pair(i, j - 1), // izquierda
            Pair(i, j + 1)  // derecha
        )

        // Cambiar color de los botones adyacentes
        for (pos in posicionesAdyacentes) {
            val x = pos.first
            val y = pos.second

            // Verificar si la posición es válida
            if (x in buttons.indices && y in buttons[0].indices) {
                toggleButtonColor(buttons[x][y], x, y) // cambiar el color basado en el estado actual
            }
        }
    }

    // funcion que cambia el color de un boton segun su estado --!! (REVISAR!) !!--
    private fun toggleButtonColor(button: Button, i: Int, j: Int) {
        if (estadoBotones[i][j] == 1) {
            button.setBackgroundColor(Color.LTGRAY) // si estaba rojo, cambia a gris
            estadoBotones[i][j] = 0 // actualiza el estado a "no coloreado"
        } else {
            button.setBackgroundColor(Color.RED) // si estaba gris, cambia a rojo
            estadoBotones[i][j] = 1 // actualiza el estado a "coloreado"
        }
    }

    // funcion para resetear los colores de los botones
    private fun reinicarBotones() {
        val buttons = listOf(
            listOf(binding.button00, binding.button01, binding.button02),
            listOf(binding.button10, binding.button11, binding.button12),
            listOf(binding.button20, binding.button21, binding.button22)
        )

        for (i in buttons.indices) {
            for (j in buttons[i].indices) {
                buttons[i][j].setBackgroundColor(Color.LTGRAY) // cambiar color a gris
                estadoBotones[i][j] = 0 // reiniciar el estado del botón
            }
        }
    }
}

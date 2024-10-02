package com.example.gamebuttons

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gamebuttons.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var estadoBotones: Array<IntArray> // Array paralelo que marca los botones coloreados
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // iniciamos el binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Iniciar el estado de los botones
        estadoBotones = Array(3) { IntArray(3) { 0 } }
        // inicar listeners en cada boton
        InicializarListeners()

        // boton de reiniciar el juego
        binding.refreshButton.setOnClickListener {
            reinicarBotones()
        }
    }

    // iniciar listeners para cada boton en el 3x3 grid
    private fun InicializarListeners() {
        val buttons = listOf(
            listOf(binding.button00, binding.button01, binding.button02),
            listOf(binding.button10, binding.button11, binding.button12),
            listOf(binding.button20, binding.button21, binding.button22)
        )

        for (i in buttons.indices) {
            for (j in buttons[i].indices) {
                buttons[i][j].setOnClickListener {
                    //LO MAS IMPORTANTE
                    //1. cambia el color del boton clickeado
                    //2. cambia el color de los botones adyacentes
                    CambiarColorBotonesAdyacentes(buttons, i, j)
                    buttons[i][j].setBackgroundColor(Color.RED)
                    estadoBotones[i][j] = 1
                    if (ComprobarVictoria(estadoBotones)) {
                        // Mostrar mensaje de victoria
                        Toast.makeText(applicationContext, "victoria!", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }
    private fun ComprobarVictoria(estadoBotones: Array<IntArray>): Boolean {
        for (row in estadoBotones) {
            for (estado in row) {

                if (estado != 1) {
                    return false
                }
            }
        }
        return true //hay victoria
    }


    // cambiar botones adyacentes a rojo dependiendo de la posicion del boton clickeado
    private fun CambiarColorBotonesAdyacentes(buttons: List<List<Button>>, i: Int, j: Int) {
        // Cambia el color del botón clicado
        buttons[i][j].setBackgroundColor(Color.RED)
        estadoBotones[i][j] = 1 // Marcar también el botón clicado

        // Lista de las posiciones adyacentes excluyendo las diagonales
        val posicionesAdyacentes = listOf(
            Pair(i - 1, j), // arriba
            Pair(i + 1, j), // abajo
            Pair(i, j - 1), // izquierda
            Pair(i, j + 1)  // derecha
        )

        // Cambia el color de los botones adyacentes (excluyendo diagonales)
        for (pos in posicionesAdyacentes) {
            val x = pos.first
            val y = pos.second

            // Validar si la posición es válida (dentro de los límites del grid)
            if (x in buttons.indices && y in buttons[0].indices) {
                buttons[x][y].setBackgroundColor(Color.RED)
                estadoBotones[x][y] = 1 // Marcar los adyacentes también
            }
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
                buttons[i][j].setBackgroundColor(Color.LTGRAY) // Cambiar color a gris
                estadoBotones[i][j] = 0 // Reiniciar el estado del botón
            }
        }
    }
}

package com.example.gamebuttons

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.gamebuttons.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // iniciamos el binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                    //1. reinicia los botones CADA vez que se toque uno
                    //2. cambia el color del boton clickeado
                    //3. cambia el color de los botones adyacentes
                    reinicarBotones()
                    CambiarColorBotonesAdyacentes(buttons, i, j)
                    buttons[i][j].setBackgroundColor(Color.RED)

                }
            }
        }
    }

    // cambiar botones adyacentes a rojo dependiendo de la posicion del boton clickeado
    private fun CambiarColorBotonesAdyacentes(buttons: List<List<Button>>, i: Int, j: Int) {
        // lista de todas las posiciones adyacentes
        val PosicionesAdyacentes = listOf(
            Pair(i - 1, j), // arriba
            Pair(i + 1, j), // abajo
            Pair(i, j - 1), // izquierda
            Pair(i, j + 1)  // derecha
        )

        for (pos in PosicionesAdyacentes) {
            val x = pos.first
            val y = pos.second

            // Validar si la posicion es valida
            if (x in buttons.indices && y in buttons[0].indices) {
                buttons[x][y].setBackgroundColor(Color.RED)

            }
        }
    }

    // funcion para resetear los colores de los botones
    private fun reinicarBotones() {
        binding.button00.setBackgroundColor(Color.LTGRAY)
        binding.button01.setBackgroundColor(Color.LTGRAY)
        binding.button02.setBackgroundColor(Color.LTGRAY)
        binding.button10.setBackgroundColor(Color.LTGRAY)
        binding.button11.setBackgroundColor(Color.LTGRAY)
        binding.button12.setBackgroundColor(Color.LTGRAY)
        binding.button20.setBackgroundColor(Color.LTGRAY)
        binding.button21.setBackgroundColor(Color.LTGRAY)
        binding.button22.setBackgroundColor(Color.LTGRAY)
    }
}

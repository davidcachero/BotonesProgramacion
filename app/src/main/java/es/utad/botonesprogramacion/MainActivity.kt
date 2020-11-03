package es.utad.botonesprogramacion

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import es.utad.botonesprogramacion.dialogs.TextDialog
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class MainActivity : AppCompatActivity() {
    val SHARED_PREFERENCES = "botones"
    val FILE_NAME = "NombreBotones.txt"
    val NUMERO_BOTONES = "numeroBotones"

    lateinit var sharedPreferences: SharedPreferences
    var numeroBotones = 0

    lateinit var linearLayoutBotonera: LinearLayout
    lateinit var editTextBotones: EditText
    var arrayBotones = ArrayList<Button>()
    var arrayTextos = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        linearLayoutBotonera = findViewById(R.id.linearLayoutBotonera)
        editTextBotones = findViewById<EditText>(R.id.editTextBotones)

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        numeroBotones = sharedPreferences.getInt(NUMERO_BOTONES, 0)
        editTextBotones.setText(numeroBotones.toString())

        if (numeroBotones > 0) {
            leerTextos()
            refrescarNumeroBotones()
        }
    }

    fun onActualizar(view: View) {
        numeroBotones = editTextBotones.text.toString().toInt()
        var editor = sharedPreferences.edit()
        editor.putInt(NUMERO_BOTONES, numeroBotones)
        editor.commit()
        refrescarNumeroBotones()
        escribirTextos()
    }

    fun refrescarNumeroBotones(){
        linearLayoutBotonera.removeAllViews()

        if (numeroBotones > 0){
            for (b in 0 until numeroBotones){
                var boton = Button(this)
                boton.tag = b
                if (b < arrayTextos.size){
                    boton.text = arrayTextos.get(b)
                }
                else{
                    boton.text = b.toString()
                    arrayTextos.add(b, b.toString())
                }
                boton.setOnClickListener { onPulsarBoton(it) }
                linearLayoutBotonera.addView(boton)
                arrayBotones.add(boton)
            }
            if (arrayTextos.size > numeroBotones){
                var numeroTexto = arrayTextos.size
                for (indice in numeroTexto-1 downTo numeroBotones){
                    arrayTextos.removeAt(indice)
                }
            }
        }
    }

    fun onPulsarBoton(view: View){
        Toast.makeText(this, view.tag.toString(), Toast.LENGTH_LONG).show()
        var textDialog = TextDialog();
        textDialog.button = view as Button
        textDialog.mainActivity = this
        textDialog.show(supportFragmentManager, "TextDialog_Tag")
    }

    fun actualizarTexto(indice: Int, texto: String) {
        arrayTextos.removeAt(indice)
        arrayTextos.add(indice, texto)
        escribirTextos()
    }

    fun escribirTextos(){
        var bufferedWriter = BufferedWriter(OutputStreamWriter(openFileOutput(FILE_NAME, Context.MODE_PRIVATE)))

        for (texto: String in arrayTextos){
            bufferedWriter.write(texto)
            bufferedWriter.newLine()
        }

        bufferedWriter.close()
    }

    fun leerTextos(){
        var bufferedReader = BufferedReader(InputStreamReader(openFileInput(FILE_NAME)))
        bufferedReader.forEachLine { linea -> arrayTextos.add(linea) }
        bufferedReader.close()
    }
}
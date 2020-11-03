package es.utad.botonesprogramacion.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import es.utad.botonesprogramacion.MainActivity
import es.utad.botonesprogramacion.R

class TextDialog: DialogFragment() {

    lateinit var button: Button
    lateinit var mainActivity: MainActivity
    lateinit var et_texto: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var viewDialog = inflater.inflate(R.layout.text_dialog, container, false)

        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)


        var botonAceptar = viewDialog.findViewById<Button>(R.id.buttonAceptar)
        et_texto = viewDialog.findViewById<EditText>(R.id.et_texto)
        et_texto.setText(button.text)
        botonAceptar.setOnClickListener{view->cambiarTexto(view)}
        return viewDialog
    }

    fun cambiarTexto(view: View) {

        var texto =  et_texto.text.toString()
        button.text = texto
        var indice = button.tag.toString().toInt()
        mainActivity.actualizarTexto(indice, texto)
        dismiss()
    }
}
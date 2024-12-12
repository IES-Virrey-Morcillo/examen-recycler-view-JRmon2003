package com.example.examen_recycler_joseramon

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActivityAnadirMonte : AppCompatActivity() {
    private lateinit var btnVolver: Button
    private lateinit var btnEnviar: Button
    private lateinit var editTextid: EditText
    private lateinit var editTextNombre: EditText
    private lateinit var editTextContienente: EditText
    private lateinit var editTextAltura: EditText
    private lateinit var editTextURL: EditText
    private lateinit var editTextURLinfo: EditText
    private lateinit var editTextUrl: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pedir_info)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initComponents()
        initListeners()
    }

    private fun initComponents() {
        btnVolver = findViewById(R.id.volver)
        btnEnviar = findViewById(R.id.buttonEnviar)
        editTextid =  findViewById(R.id.etID)
        editTextNombre = findViewById(R.id.etNombre)
        editTextContienente = findViewById(R.id.etContinente)
        editTextAltura = findViewById(R.id.etAltura)
        editTextUrl = findViewById(R.id.editTextUrl)
        editTextURLinfo = findViewById(R.id.etInfoMonte)
    }

    private fun initListeners() {
        btnVolver.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnEnviar.setOnClickListener {
            val id = editTextid.text.toString().toIntOrNull()
            val nombre = editTextNombre.text.toString()
            val continente = editTextContienente.text.toString()
            val altura = editTextAltura.text.toString()
            val url = editTextUrl.text.toString()
            val info = editTextURLinfo.text.toString()

            val intent = Intent().apply {
                putExtra("id", id)
                putExtra("nombre", nombre)
                putExtra("continente", continente)
                putExtra("altura", altura)
                putExtra("url", url)
                putExtra("urlinfo", info)
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}

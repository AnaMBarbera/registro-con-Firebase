package es.anabarbera.basesdedatos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var nameEditText:EditText
    private lateinit var emailEditText:EditText
    private lateinit var provinciaEditText:EditText
    private lateinit var saveButton:Button
    private lateinit var consultaButton: Button
    private lateinit var textViewConsulta: TextView
    private lateinit var desplegable:Spinner

    private lateinit var db:DatabaseHandler
    private lateinit var distinctProvincias: List<String>
    //para actualizar el spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameEditText=findViewById(R.id.nameEditText)
        emailEditText=findViewById(R.id.emailEditText)
        provinciaEditText=findViewById(R.id.provinciaEditText)
        saveButton = findViewById(R.id.saveButton)
        consultaButton = findViewById(R.id.consultaButton)
        textViewConsulta = findViewById(R.id.textViewConsulta)
        desplegable = findViewById(R.id.desplegable)

        db=DatabaseHandler(this)

        //para actualizar el spinner
        distinctProvincias = db.getDistinctProvincias()
        // Agrego una opción vacía al principio de la lista
        val opciones = listOf("") + distinctProvincias
        // Utilizo esa lista como fuente de datos del Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opciones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val desplegable = findViewById<Spinner>(R.id.desplegable)
        desplegable.adapter = adapter

        saveButton.setOnClickListener {
            val name=nameEditText.text.toString().trim()
            val email=emailEditText.text.toString().trim()
            val provincia=provinciaEditText.text.toString().trim()

            if (name.isNotEmpty() && email.isNotEmpty()) {
                val id= db.addContact(name,email,provincia)
                if (id==-1L){
                    //error al guardar en base de datos
                    Toast.makeText(applicationContext, "error en base de datos", Toast.LENGTH_SHORT).show()
                } else {
                    //toast para avisar que se ha guardado el registro en la bd
                    Toast.makeText(applicationContext, "el registro se ha guardado", Toast.LENGTH_SHORT).show()
                    nameEditText.text.clear()
                    emailEditText.text.clear()
                    provinciaEditText.text.clear()

                    // Actualiza la lista de provincias
                    distinctProvincias = db.getDistinctProvincias()
                    // Agrego una opción vacía al principio de la lista
                    val opciones = listOf("") + distinctProvincias
                    // Notifica al adaptador del Spinner sobre el cambio
                    val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opciones)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    desplegable.adapter = adapter
                }
            } else {//cuando el usuario no ha introducido algún dato
                Toast.makeText(applicationContext, "Te falta algún dato", Toast.LENGTH_SHORT).show()
            }
        }

        consultaButton.setOnClickListener {
            val contactList = db.getAllContacts()
            // Limpiar el contenido existente en el TextView antes de agregar nuevos elementos
            textViewConsulta.text = ""
            for(contact in contactList) {
                // Utilizar append para agregar cada detalle de contacto individualmente
                textViewConsulta.append("Contacto - ID: ${contact.id}, Nombre: ${contact.name}, Email: ${contact.email}, Provincia: ${contact.provincia} \n")
            }
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        desplegable.adapter = adapter

        desplegable.onItemSelectedListener = object:AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val valor = opciones[position]
                val campo = "provincia"

                if (valor.isNotEmpty()) {
                    val contactList = db.queryProvinciaContacts(campo, valor)
                    if (contactList.isNotEmpty()) {
                        val texto = contactList.joinToString("\n") {
                            "ID: ${it.id}, Nombre: ${it.name}, Email: ${it.email}, Provincia: ${it.provincia}"
                        }
                        textViewConsulta.text = texto
                    } else {
                        textViewConsulta.text = "No se encontraron registros con el valor proporcionado."
                    }
                } else {
                    Toast.makeText(applicationContext, "Introduce un valor para realizar la consulta", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Acciones cuando no se selecciona nada (opcional)
            }
        }
    }
}
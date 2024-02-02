package es.anabarbera.basesdedatos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var nameEditText:EditText
    private lateinit var emailEditText:EditText
    private lateinit var provinciaEditText:EditText
    private lateinit var saveButton:Button
    private lateinit var consultaButton: Button
    private lateinit var textViewConsulta: TextView
    private lateinit var textoConsultaProvincia: EditText
    private lateinit var botonConsultaProvincia: Button

    private lateinit var db:DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameEditText=findViewById(R.id.nameEditText)
        emailEditText=findViewById(R.id.emailEditText)
        provinciaEditText=findViewById(R.id.provinciaEditText)
        saveButton = findViewById(R.id.saveButton)
        consultaButton = findViewById(R.id.consultaButton)
        textViewConsulta = findViewById(R.id.textViewConsulta)
        textoConsultaProvincia = findViewById(R.id.textoConsultaProvincia)
        botonConsultaProvincia = findViewById(R.id.botonConsultaProvincia)

        db=DatabaseHandler(this)

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

        botonConsultaProvincia.setOnClickListener {
            val campo = "provincia" //
            val valor = textoConsultaProvincia.text.toString().trim()

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

    }
}
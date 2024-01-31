package es.anabarbera.basesdedatos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var nameEditText:EditText
    private lateinit var emailEditText:EditText
    private lateinit var saveButton:Button

    private lateinit var db:DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameEditText=findViewById(R.id.nameEditText)
        emailEditText=findViewById(R.id.emailEditText)
        saveButton = findViewById(R.id.saveButton)

        db=DatabaseHandler(this)

        saveButton.setOnClickListener {
            val name=nameEditText.text.toString().trim()
            val email=emailEditText.text.toString().trim()

            if (name.isNotEmpty() && email.isNotEmpty()) {
                val id= db.addContact(name,email)
                if (id==-1L){
                    //error al guardar en base de datos
                    Toast.makeText(applicationContext, "error en base de datos", Toast.LENGTH_SHORT).show()
                } else {
                    //toast para avisar que se ha guardado el registro en la bd
                    Toast.makeText(applicationContext, "el registro se ha guardado", Toast.LENGTH_SHORT).show()
                    nameEditText.text.clear()
                    emailEditText.text.clear()
                }

            } else {//cuando el usuario no ha introducido algún dato
                Toast.makeText(applicationContext, "Te falta algún dato", Toast.LENGTH_SHORT).show()



            }
        }
    }
}
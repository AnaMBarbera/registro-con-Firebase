package es.anabarbera.basesdedatos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ProgressBar
import com.google.firebase.auth.FirebaseAuth

class Registro : AppCompatActivity() {

    //Variables para firebase
    private lateinit var registroNombre:EditText
    private lateinit var registroApellido:EditText
    private lateinit var registroEmail:EditText
    private lateinit var registroContrasena:EditText
    private lateinit var progressBar:ProgressBar

  //  private lateinit var dbReference:DatabaseReference
    // private lateinit var database:FirebaseDatabase
    private lateinit var auth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        registroNombre=findViewById(R.id.registroNombre)
        registroApellido=findViewById(R.id.registroApellido)
        registroEmail=findViewById(R.id.registroEmail)
        registroContrasena=findViewById(R.id.registroContrasena)

        progressBar=findViewById(R.id.progressBar)

      //  database=FirebaseDatabase.getInstance()
        auth=FirebaseAuth.getInstance()

       // dbReference = database.reference.child("User")



    }
}
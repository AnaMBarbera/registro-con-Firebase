package es.anabarbera.basesdedatos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Registro : AppCompatActivity() {

    //Variables para firebase
    private lateinit var registroNombre:EditText
    private lateinit var registroApellido:EditText
    private lateinit var registroEmail:EditText
    private lateinit var registroContrasena:EditText
    private lateinit var progressBar:ProgressBar

    //Variables de clases firebase (import com.google.firebase....)
    private lateinit var dbReference:DatabaseReference //Instancia: Objeto para realizar acciones y acceder a los métodos de la base de datos Firebase
    private lateinit var database:FirebaseDatabase // Base de datos Firebase
    private lateinit var auth:FirebaseAuth // Instancia del autenticador para acceder a sus métodos


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        registroNombre=findViewById(R.id.registroNombre)
        registroApellido=findViewById(R.id.registroApellido)
        registroEmail=findViewById(R.id.registroEmail)
        registroContrasena=findViewById(R.id.registroContrasena)

        progressBar=findViewById(R.id.progressBar)

        database=FirebaseDatabase.getInstance()
        auth=FirebaseAuth.getInstance() //clase de Firebase (importada)

       dbReference = database.reference.child("User")

    }
    //función onClick del botón, llama a la función privada por seguridad
    fun registrar(view: View){
        createNewAccount()
    }

    //función privada por seguridad
    private fun createNewAccount(){
        val name:String = registroNombre.text.toString()
        val lastName:String = registroApellido.text.toString()
        val email:String = registroEmail.text.toString()
        val password:String = registroContrasena.text.toString()

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            progressBar.visibility = View.VISIBLE
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){
                    task-> //se hace fuera de nuestro programa (en Firebase)
                if(task.isComplete){
                    val user:FirebaseUser?=auth.currentUser
                    verifyEmail(user) //función más abajo

                    val userBD = dbReference.child(user!!.uid) //se crea en Firebase
                    userBD.child("Name").setValue(name)
                    userBD.child("Apellido").setValue(lastName)
                    action() //llamada al Login (función privada. No haría falta porque ya estamos en una función private. Mayor seguridad)
                }
            }
        }
    }
    private fun action(){
        startActivity(Intent(this,Login::class.java)) //saltamos a la vista login con un intent
    }
    private fun verifyEmail(user:FirebaseUser?){
        user?.sendEmailVerification()?.addOnCompleteListener(this){
                    task->
                if(task.isComplete) {
                    Toast.makeText(this, "Email enviado",Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this, "Error al enviar el email",Toast.LENGTH_SHORT).show()
                }
            }
    }
}
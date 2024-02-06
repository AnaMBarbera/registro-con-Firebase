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
import com.google.firebase.pdatabase.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Registro : AppCompatActivity() {

    //Variables para firebase
    private lateinit var registroNombre:EditText
    private lateinit var registroApellido:EditText
    private lateinit var registroEmail:EditText
    private lateinit var registroContrasena:EditText
    private lateinit var progressBar:ProgressBar

    private lateinit var dbReference:DatabaseReference
    private lateinit var database:FirebaseDatabase
    private lateinit var auth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        registroNombre=findViewById(R.id.registroNombre)
        registroApellido=findViewById(R.id.registroApellido)
        registroEmail=findViewById(R.id.registroEmail)
        registroContrasena=findViewById(R.id.registroContrasena)

        progressBar=findViewById(R.id.progressBar)

        database=FirebaseDatabase.getInstance()
        auth=FirebaseAuth.getInstance()

       dbReference = database.reference.child("User")

    }
    fun registrar(view: View){
        createNewAccount()
    }

    private fun createNewAccount(){
        val name:String = registroNombre.text.toString()
        val lastName:String = registroApellido.text.toString()
        val email:String = registroEmail.text.toString()
        val password:String = registroContrasena.text.toString()

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            progressBar.visibility = View.VISIBLE
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){
                    task->
                if(task.isComplete){
                    val user:FirebaseUser?=auth.currentUser
                    verifyEmail(user)

                    val userBD = dbReference.child(user!!.uid)
                    userBD.child("Name").setValue(name)
                    userBD.child("Apellido").setValue(lastName)
                    action()
                }
            }

        }
    }
    private fun action(){
        startActivity(Intent(this,Login::class.java))
    }
    private fun verifyEmail(user:FirebaseUser?){
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this){
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
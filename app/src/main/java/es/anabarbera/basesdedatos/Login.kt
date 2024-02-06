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

class Login : AppCompatActivity() {
    private lateinit var editTextUser:EditText
    private lateinit var editTextPsswd:EditText
    private lateinit var progressBarLogin:ProgressBar

   private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextUser=findViewById(R.id.editTextUser)
        editTextPsswd=findViewById(R.id.editTextPsswd)
        progressBarLogin=findViewById(R.id.progressBarLogin)

        auth = FirebaseAuth.getInstance()

    }
    fun forgotPassword(view: View) {

    }
    fun registrar(view:View) {
        startActivity(Intent(this,Registro::class.java))
    }
    fun login(view:View) {
        loginUser()
    }

    private fun loginUser(){

        val user:String = editTextUser.text.toString()
        val password:String = editTextPsswd.text.toString()

        if(!TextUtils.isEmpty(user)  && !TextUtils.isEmpty(password)){
            progressBarLogin.visibility = View.VISIBLE
            auth.signInWithEmailAndPassword(user,password).addOnCompleteListener(this){
                    task->
                if (task.isSuccessful) {
                    action()
                }else{
                    Toast.makeText(this,"Error en la autenticación",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun action(){
        startActivity(Intent(this,MainActivity::class.java))
    }
}
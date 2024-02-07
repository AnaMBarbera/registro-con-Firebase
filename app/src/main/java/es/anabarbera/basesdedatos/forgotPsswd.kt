package es.anabarbera.basesdedatos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.ProgressBar
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import android.view.View


class forgotPsswd : AppCompatActivity() {

    private lateinit var editTextUser:EditText
    private lateinit var progressBarPsswd: ProgressBar
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_psswd)

        editTextUser=findViewById(R.id.editTextUser)
        progressBarPsswd=findViewById(R.id.progressBarPsswd)
        auth = FirebaseAuth.getInstance()
    }

    fun send(view:View){
        val editTextUser=editTextUser.text.toString()
        if(!TextUtils.isEmpty(editTextUser)) {
            progressBarPsswd.visibility = View.VISIBLE
            auth.sendPasswordResetEmail(editTextUser)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, Login::class.java))
                    } else {
                        Toast.makeText(
                            this, "Error al enviar el Correo de recuperación",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

}
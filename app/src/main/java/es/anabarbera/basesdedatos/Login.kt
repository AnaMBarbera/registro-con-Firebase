package es.anabarbera.basesdedatos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ProgressBar

class Login : AppCompatActivity() {
    private lateinit var editTextUser:EditText
    private lateinit var editTextPsswd:EditText
    private lateinit var progressBarLogin:ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextUser=findViewById(R.id.editTextUser)
        editTextPsswd=findViewById(R.id.editTextPsswd)
        progressBarLogin=findViewById(R.id.progressBarLogin)

    }
}
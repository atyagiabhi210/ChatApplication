package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import com.example.chatapplication.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var mAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
        supportActionBar?.hide()
        mAuth= FirebaseAuth.getInstance()
        binding.btnSignup.setOnClickListener {
            val intent=Intent(this,SignUpActivity::class.java)
            startActivity(intent)

        }
        binding.btnLogin.setOnClickListener {
            val email=binding.edtEmail.text.toString()
            val password=binding.edtPassword.text.toString()
            login(email,password)
        }

    }

    private fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent=Intent(this@LoginActivity,MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@LoginActivity,"This user does not exist",Toast.LENGTH_LONG).show()
                }
            }
    }
}
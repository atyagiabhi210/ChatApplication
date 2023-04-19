package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.chatapplication.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var mAuth:FirebaseAuth
    private lateinit var myDbRef:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        mAuth= FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        binding=ActivitySignUpBinding.inflate(layoutInflater)
        val view=binding.root
        supportActionBar?.hide()
        setContentView(view)
        binding.btnSignup.setOnClickListener {
            val name=binding.edtName.text.toString()
            val email=binding.edtEmail.text.toString()
            val password=binding.edtPassword.text.toString()
            signUp(name,email,password)
        }
    }

    private fun signUp(name:String,email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    //code for jumping to home activity
                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                    val intent=Intent(this@SignUpActivity,MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@SignUpActivity,"Some error occurred",Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
        myDbRef=FirebaseDatabase.getInstance().getReference()
        myDbRef.child("user").child(uid).setValue(User(name,email,uid))
    }
}
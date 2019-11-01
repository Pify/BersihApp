package com.myapps.pickrubbish

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.admin_signup_page.*
import kotlinx.android.synthetic.main.user_register.*

class RegistUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_register)

        sign_up.setOnClickListener {
            val email = editEml.text.toString()
            val password = editPw.text.toString()

            val intent = Intent(this, LoginActivity::class.java)


            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email/password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (!it.isSuccessful) {
                            return@addOnCompleteListener

                        } else {
                            Toast.makeText(this, "Register berhasil!", Toast.LENGTH_SHORT).show()
                            Log.d("Main", "Successfully created user with uid: ${it.result}")
                        }
                    }
                    .addOnFailureListener {
                        Log.d("Main", "Failed to create user: ${it.message}")
                    }

                Log.d("RegistUserActivity", "Email is: " + email)
                Log.d("RegistUserActivity", "Password: $password")

                saveUserDatabase(it.toString())
                startActivity(intent)
                finish()
            }
        }

        txtAkun.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    /*uid, editNm.text.toString(), editEml.text.toString()*/
    private fun saveUserDatabase(email : String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("users/$uid")
//tess
        val user = User(
            uid,
            editNm.text.toString(),
            editPw.text.toString()
        )
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("user_data", "Success gan")
            }
            .addOnFailureListener {
                Log.d("Fail", "failed")
            }
    }

}

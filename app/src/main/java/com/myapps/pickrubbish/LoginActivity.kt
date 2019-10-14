package com.myapps.pickrubbish

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.oldlex.whatsappclone.RegistUserActivity
import kotlinx.android.synthetic.main.login_page.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        sign_in.setOnClickListener {
            val email = editLogin.text.toString()
            val password = editPass.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "email/pw tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                Log.d("Login", "Attempt login with email/pw: $email/$password")

                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (!it.isSuccessful) {
                                return@addOnCompleteListener

                            } else {
                                Toast.makeText(this, "Login berhasil!", Toast.LENGTH_SHORT).show()

                                val intent = Intent(this, Welcome::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Berhasil tapi bo'ong", Toast.LENGTH_SHORT).show()
                        }
            }
        }

        txtDaftar.setOnClickListener {
            val intent = Intent(this, RegistUserActivity::class.java)
            startActivity(intent)
        }

        txtLoginAdmin.setOnClickListener {
            val intent = Intent(this, LoginAdmin::class.java)
            startActivity(intent)
        }
    }
}
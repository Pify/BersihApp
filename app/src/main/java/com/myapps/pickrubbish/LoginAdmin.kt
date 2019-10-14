package com.myapps.pickrubbish

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login_admin.*

class LoginAdmin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_admin)

        sign_in_admin.setOnClickListener {
            val eml = editLogin_admin.text.toString()
            val pass = editPass_admin.text.toString()

            if ( eml.isEmpty() || pass.isEmpty()){
                Toast.makeText(this, "email / pass tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(eml, pass)
                    .addOnCompleteListener {
                        if (!it.isSuccessful) {
                            return@addOnCompleteListener
                        } else {
                            Toast.makeText(this, "Login berhasil!!", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this, WelcomeAdmin::class.java)
                            startActivity(intent)
                            finish()
                         }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "success'nt :p", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        txtDaftar_admin.setOnClickListener {
            val intent = Intent(this, RegistAdminActivity::class.java)
            startActivity(intent)
        }
    }
}

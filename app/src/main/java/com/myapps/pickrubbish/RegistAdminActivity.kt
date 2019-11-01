package com.myapps.pickrubbish

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.admin_signup_page.*

class RegistAdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_signup_page)

        sign_upAdmin.setOnClickListener{
            val eml = editEml_admin.text.toString()
            val pass = editPw_admin.text.toString()

            val intent = Intent(this, LoginAdmin::class.java)

            if (eml.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "email dan password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(eml, pass)
                    .addOnCompleteListener {
                        if(!it.isSuccessful) {
                            return@addOnCompleteListener
                        } else {
                            Toast.makeText(this, "Register berhasil", Toast.LENGTH_SHORT).show()
                            Log.d("admin", "Successfully created user with uid: ${it.result}")
                        }
                    }
                    .addOnFailureListener {
                        Log.d("Main", "Failed to create user: ${it.message}")
                    }

                Log.d("RegistUserActivity", "Email is: " + eml)
                Log.d("RegistUserActivity", "Password: $pass")

                saveAdminDatabase(it.toString())
                startActivity(intent)
                finish()
            }
        }
        txtAkun_admin.setOnClickListener {
            val intent = Intent(this, LoginAdmin::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun saveAdminDatabase(email : String){
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("kurir/$uid")

        val admin = Admin(
            uid,
            editNm_admin.text.toString(),
            editEml_admin.text.toString()
        )

        ref.setValue(admin)
            .addOnSuccessListener {
                Log.d("admin_data", "Success gan")
            }
            .addOnFailureListener {
                Log.d("Fail", "failed")
            }
    }
}
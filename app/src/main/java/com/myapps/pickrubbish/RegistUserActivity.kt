package com.oldlex.whatsappclone

import androidx.appcompat.app.AppCompatActivity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.myapps.pickrubbish.LoginActivity
import com.myapps.pickrubbish.R

class RegistUserActivity : AppCompatActivity() {
    private var CreateAccountButton: Button? = null
    private var UserEmail: EditText? = null
    private var UserPassword: EditText? = null
    private var AlreadyHaveAccountLink: TextView? = null
    private var mAuth: FirebaseAuth? = null
    private var loadingBar: ProgressDialog? = null
    private var RootRef: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_register)
        mAuth = FirebaseAuth.getInstance()
        RootRef = FirebaseDatabase.getInstance().reference

        InitializeFields()

        AlreadyHaveAccountLink!!.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        CreateAccountButton!!.setOnClickListener { CreateNewAccount() }
    }

    private fun CreateNewAccount() {
        val email = UserEmail!!.text.toString()
        val password = UserPassword!!.text.toString()

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please Enter an Email", Toast.LENGTH_SHORT).show()
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please Enter an Password", Toast.LENGTH_SHORT).show()
        } else {
            loadingBar!!.setTitle("Creating New Account")
            loadingBar!!.setMessage("Please Wait while we creating account")
            loadingBar!!.setCanceledOnTouchOutside(true)
            loadingBar!!.show()


            mAuth!!.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val currentUserId = mAuth!!.currentUser!!.uid
                        RootRef!!.child("Users").child(currentUserId).setValue("")

                        SendUserToMainActivity()
                        Toast.makeText(this, "Akun Berhasil didaftarkan", Toast.LENGTH_SHORT).show()
                        loadingBar!!.dismiss()
                    } else {
                        val message = task.exception!!.toString()
                        Toast.makeText(this, "Error : $message", Toast.LENGTH_SHORT).show()
                        loadingBar!!.dismiss()
                    }
                }
        }
    }

    private fun SendUserToMainActivity() {
        val mainIntent = Intent(this@RegisterActivity, MainActivity::class.java)
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(mainIntent)
        finish()
    }

    private fun InitializeFields() {
        CreateAccountButton = findViewById(R.id.register_button) as Button
        UserEmail = findViewById(R.id.register_email) as EditText
        UserPassword = findViewById(R.id.register_password) as EditText
        AlreadyHaveAccountLink = findViewById(R.id.already_have_account_link) as TextView

        loadingBar = ProgressDialog(this)


    }
}

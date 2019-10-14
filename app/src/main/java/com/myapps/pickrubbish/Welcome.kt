package com.myapps.pickrubbish

import android.app.ProgressDialog.show
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.welcome_message.*

class Welcome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_message)

        btn_touser.setOnClickListener {
            val intent = Intent(this, UserPage::class.java)
            startActivity(intent)
            finish()
        }

        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

//        showUsername(uid)
        val show = FirebaseDatabase.getInstance().getReference("users/")
        show.addChildEventListener(object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
//                val open = p0.getValue(User::class.java)
//                val ids = open!!.username
//                txtUserTampil.text = ids
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

        })
    }

    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser == null){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

//    private fun showUsername(uid: String) {
//        val ref = FirebaseDatabase.getInstance().getReference("/users")
//        ref.child(uid).addValueEventListener(object : ValueEventListener{
//            override fun onCancelled(p0: DatabaseError) {
//
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//                val userName = p0.child("username").getValue().toString()
//                txtUserTampil.text = userName
//            }
//        })
//    }
}
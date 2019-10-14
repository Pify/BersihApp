package com.myapps.pickrubbish

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.user_page.*

class UserPage : AppCompatActivity() {
    lateinit var ref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_page)

        ref = FirebaseDatabase.getInstance().getReference("SAMPAH")

        btnSubmit_data.setOnClickListener {
            saveDefault()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveDefault() {
        val alamat = edtAlamat_user.text.toString()
        val deskripsi = edtDeskrip_user.text.toString()

        val trash = Trash(alamat, deskripsi)
        val trashId = ref.push().key.toString()

        if (alamat.isEmpty() || deskripsi.isEmpty()){
            Toast.makeText(this, "Insert data", Toast.LENGTH_SHORT).show()
        } else {
            ref.child(trashId).setValue(trash).addOnCompleteListener{
                Toast.makeText(this, "sukses mengirim data", Toast.LENGTH_SHORT).show()
                edtAlamat_user.setText("")
                edtDeskrip_user.setText("")
            }
        }
    }
}

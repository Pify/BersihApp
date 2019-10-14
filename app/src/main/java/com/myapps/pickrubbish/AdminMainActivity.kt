package com.myapps.pickrubbish

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.internal.ListenerHolder
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.myapps.pickrubbish.R
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_admin_main.*
import kotlinx.android.synthetic.main.item_database.view.*
import kotlinx.android.synthetic.main.user_page.*
import java.security.acl.Group
import java.text.FieldPosition

class AdminMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)

        fetchUsers()
    }

    companion object {
        val KEY = "DATA_KEY"
    }

    private fun fetchUsers() {
        val ref = FirebaseDatabase.getInstance().getReference("/SAMPAH")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()

                p0.children.forEach {
                    Log.d("NewData", it.toString())
                    val user = it.getValue(Trash::class.java)
                    if (user != null) {
                        adapter.add(ListItem(user))
                    }
                }
                adapter.setOnItemClickListener { item, view ->
                    val intent = Intent(view.context, ItemView::class.java)
                    startActivity(intent)
                }
                recyclerItem.adapter = adapter

            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }
}

class ListItem(val user: Trash) : com.xwray.groupie.Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder,  position: Int) {
        viewHolder.itemView.ItemAlamat.text = user.alamat
        viewHolder.itemView.ItemDeskrip.text = user.deskripsi


    }

    override fun getLayout(): Int {
        return R.layout.item_database
    }
}

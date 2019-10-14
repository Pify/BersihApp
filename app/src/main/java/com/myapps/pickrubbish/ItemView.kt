package com.myapps.pickrubbish

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ItemView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_view)

        val dataClick = intent.getStringExtra("data")
        supportActionBar?.title = dataClick
    }
}

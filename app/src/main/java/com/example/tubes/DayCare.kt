package com.example.tubes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.day_care.*

class DayCare : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.day_care)

        val catBreeds = arrayOf("Persia","Anggora","Domestik")
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, catBreeds)

        spinner2.adapter = arrayAdapter
        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //textView33.text = catBreeds[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        button6.setOnClickListener {
            val intent = Intent(this, BottomNavMenu::class.java)
            startActivity(intent)
        }
    }
}
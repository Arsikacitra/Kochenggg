package com.example.tubes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.detail_treatment.*

class DetailTreatment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_treatment)

        val catBreeds = arrayOf("Persia","Anggora","Domestik")
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, catBreeds)

        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //textView27.text = catBreeds[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        button5.setOnClickListener {
            startActivity(Intent(this,BottomNavMenu::class.java))
            finish()
        }
    }
}
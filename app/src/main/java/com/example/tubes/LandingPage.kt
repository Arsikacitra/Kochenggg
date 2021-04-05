package com.example.tubes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.landing_page.*

class LandingPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landing_page)

        val context = this
        button.setOnClickListener {
            startActivity(Intent(this,SignIn::class.java))
        }

        button2.setOnClickListener {
            startActivity(Intent(this,SignUp::class.java))
        }

    }
}
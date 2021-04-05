package com.example.tubes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.landing_page.*
import kotlinx.android.synthetic.main.landing_page.button
import kotlinx.android.synthetic.main.sign_up.*

class SignUp : AppCompatActivity() {

    lateinit var databaseHelper: DatabaseHelper
    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up)

        val editTextUsername = findViewById<EditText>(R.id.editText3)
        val editTextEmail = findViewById<EditText>(R.id.editText4)
        val editTextPassword = findViewById<EditText>(R.id.editText5)
        val editTextConfirmPassword = findViewById<EditText>(R.id.editText6)
        val buttonSignUp = findViewById<Button>(R.id.button4)
        val textViewLinkSignIn = findViewById<TextView>(R.id.textView14)
        databaseHelper = DatabaseHelper(this)

        buttonSignUp.setOnClickListener {
            if (editTextUsername.text.toString() == "" || editTextEmail.text.toString() == "" ||
                    editTextPassword.text.toString() == "" || editTextConfirmPassword.text.toString() == "") {
                Toast.makeText(this, "Tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }

            else if (databaseHelper.checkUser(editTextEmail.text.toString().trim())) {
                Toast.makeText(this, "Email anda sudah terdatar", Toast.LENGTH_SHORT).show()
            }

            else if (editTextPassword.text.toString() != editTextConfirmPassword.text.toString()) {
                Toast.makeText(this, "Password tidak sama", Toast.LENGTH_SHORT).show()
            }

            else if (editTextPassword.text.toString() == editTextConfirmPassword.text.toString()) {
                var user = User(
                        username = editTextUsername.text.toString().trim(),
                        email = editTextEmail.text.toString().trim(),
                        password = editTextPassword.text.toString().trim(),
                        image = ""
                )

                databaseHelper.addUser(user)
                Toast.makeText(this, "Registrasi berhasil", Toast.LENGTH_SHORT).show()

                handler = Handler()
                handler.postDelayed({
                    startActivity(Intent(this, SignIn::class.java))
                    finish()
                }, 1000)
            }

            else {
                    Toast.makeText(this, "Registrasi gagal", Toast.LENGTH_SHORT).show()
            }
        }

        textViewLinkSignIn.setOnClickListener {
            startActivity(Intent(this,SignIn::class.java))
            finish()
        }
    }
}
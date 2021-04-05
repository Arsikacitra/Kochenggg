package com.example.tubes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.tubes.fragments.CartFragment
import com.example.tubes.fragments.HomeFragment
import com.example.tubes.fragments.ProfileFragment
import com.example.tubes.helper.Constant
import com.example.tubes.helper.PreferencesHelper
import kotlinx.android.synthetic.main.bottom_nav_menu.*

class BottomNavMenu : AppCompatActivity() {

    var username: String? = ""
    var email: String? = ""
    var password: String? = ""
    var image: String? = ""

    var email_pref: String? = ""
    lateinit var sharedPref: PreferencesHelper
    lateinit var databaseUser: DatabaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bottom_nav_menu)

        sharedPref = PreferencesHelper(this)
        databaseUser = DatabaseUser(this)

        email_pref = sharedPref.getString(Constant.PREF_EMAIL)

        val data = databaseUser.readUser()
        for (i in 0 until data.size) {
            if(email_pref == data[i].email){
                username = data[i].username
                email = data[i].email
                password = data[i].password
                image = data[i].image
            }
        }

        val homeFragment = HomeFragment()
        val cartFragment = CartFragment()
        val profileFragment = ProfileFragment()

        makeCurrentFragment(homeFragment)

        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_home -> makeCurrentFragment(homeFragment)
                R.id.ic_cart -> makeCurrentFragment(cartFragment)
                R.id.ic_profile -> makeCurrentFragment(profileFragment)
            }
            true
        }

        textView19.setOnClickListener {
            sharedPref.clear()
            val intent = Intent(this, LandingPage::class.java)
            startActivity(intent)
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            val bundle = Bundle()
            bundle.putString("Username", username)
            bundle.putString("Email", email)
            bundle.putString("Password", password)
            bundle.putString("Image", image)
            fragment.arguments = bundle
            replace(R.id.container, fragment)
            commit()
        }
}
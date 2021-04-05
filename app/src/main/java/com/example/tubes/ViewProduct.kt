package com.example.tubes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.view_product.*
import java.util.ArrayList

class ViewProduct : AppCompatActivity() {

    private lateinit var databaseProduct: DatabaseProduct
    private lateinit var productAdapter: ProductAdapter
    private lateinit var gridLayoutManager: GridLayoutManager

    private var product_id: ArrayList<String>? = null
    private var product_name: ArrayList<String>? = null
    private var product_price: ArrayList<String>? = null
    private var product_image: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_product)

        databaseProduct = DatabaseProduct(this)
        product_id = ArrayList()
        product_name = ArrayList()
        product_price = ArrayList()
        product_image = ArrayList()

        storeDataInArrays()

        gridLayoutManager = GridLayoutManager(applicationContext,2, LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.setHasFixedSize(true)
        productAdapter = ProductAdapter(this, this, product_id!!, product_name!!, product_price!!, product_image!!)
        recyclerView.adapter = productAdapter

        textView35.setOnClickListener {
            startActivity(Intent(this,LandingPage::class.java))
            finish()
        }

        add_button.setOnClickListener{
            val intent = Intent(this, AddProduct::class.java)
            startActivity(intent)
        }
    }

    private fun storeDataInArrays() {
        val cursor = databaseProduct.readProduct()
        if (cursor!!.count != 0) {
            while (cursor.moveToNext()) {
                product_id!!.add(cursor.getString(0))
                product_name!!.add(cursor.getString(1))
                product_price!!.add(cursor.getString(2))
                product_image!!.add(cursor.getString(3))
            }
        }
    }
}
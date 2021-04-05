package com.example.tubes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.shop.*
import java.util.ArrayList

class Shop : AppCompatActivity() {

    private lateinit var databaseProduct: DatabaseProduct
    private lateinit var shopAdapter: ShopAdapter
    private lateinit var gridLayoutManager: GridLayoutManager

    private var shop_id: ArrayList<String>? = null
    private var shop_name: ArrayList<String>? = null
    private var shop_price: ArrayList<String>? = null
    private var shop_image: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shop)

        databaseProduct = DatabaseProduct(this)

        shop_id = ArrayList()
        shop_name = ArrayList()
        shop_price = ArrayList()
        shop_image = ArrayList()

        storeDataInArrays()

        gridLayoutManager = GridLayoutManager(applicationContext,2, LinearLayoutManager.VERTICAL,false)
        recyclerView1.layoutManager = gridLayoutManager
        recyclerView1.setHasFixedSize(true)
        shopAdapter = ShopAdapter(this, this, shop_id!!, shop_name!!, shop_price!!, shop_image!!)
        recyclerView1.adapter = shopAdapter
    }

    private fun storeDataInArrays() {
        val cursor = databaseProduct.readProduct()
        if (cursor!!.count != 0) {
            while (cursor.moveToNext()) {
                shop_id!!.add(cursor.getString(0))
                shop_name!!.add(cursor.getString(1))
                shop_price!!.add(cursor.getString(2))
                shop_image!!.add(cursor.getString(3))
            }
        }
    }
}
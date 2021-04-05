package com.example.tubes

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class ShopAdapter internal constructor(
    private val activity: Activity, private val context: Context,
    private val shop_id: ArrayList<*>,
    private val shop_name: ArrayList<*>,
    private val shop_price: ArrayList<*>,
    private val shop_image: ArrayList<*>
)
    : RecyclerView.Adapter<ShopAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.shop_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.shop_name_txt.text= shop_name[position].toString()
        holder.shop_price_txt.text = shop_price[position].toString()

        val uri = shop_image[position].toString().toUri()
        holder.shop_image_view.setImageURI(uri)
    }

    override fun getItemCount(): Int {
        return shop_id.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val shop_name_txt : TextView = itemView.findViewById(R.id.shop_name_txt)
        val shop_price_txt : TextView = itemView.findViewById(R.id.shop_price_txt)
        val shop_image_view : ImageView = itemView.findViewById(R.id.shop_image_view)
        val shopLayout = itemView.findViewById<CardView>(R.id.shopLayout)
    }
}
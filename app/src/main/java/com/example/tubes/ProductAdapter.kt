package com.example.tubes

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class ProductAdapter internal constructor(
    private val activity: Activity, private val context: Context,
    private val product_id: ArrayList<*>,
    private val product_name: ArrayList<*>,
    private val product_price: ArrayList<*>,
    private val product_image: ArrayList<*>
)
    : RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.product_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.product_name_txt.text = product_name[position].toString()
        holder.product_price_txt.text = product_price[position].toString()

        val uri = product_image[position].toString().toUri()
        holder.product_image_view.setImageURI(uri)

        holder.productLayout.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, UpdateProduct::class.java)
            intent.putExtra("id", product_id[position].toString())
            intent.putExtra("name", product_name[position].toString())
            intent.putExtra("price", product_price[position].toString())
            intent.putExtra("image", product_image[position].toString())
            activity.startActivityForResult(intent, 1)
        })
    }

    override fun getItemCount(): Int {
        return product_id.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val product_name_txt: TextView = itemView.findViewById(R.id.product_name_txt)
        val product_price_txt: TextView = itemView.findViewById(R.id.product_price_txt)
        val product_image_view: ImageView = itemView.findViewById(R.id.product_image_view)
        val productLayout = itemView.findViewById<CardView>(R.id.productLayout)
    }
}
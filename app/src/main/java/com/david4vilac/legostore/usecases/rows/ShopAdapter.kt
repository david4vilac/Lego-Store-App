package com.david4vilac.legostore.usecases.rows

import android.content.Context
import android.content.DialogInterface.OnClickListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.david4vilac.legostore.model.Product
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.david4vilac.legostore.R
import com.david4vilac.legostore.model.ProductList

//import com.david4vilac.lifecycle.SongPrefs.Companion.prefs


class ShopAdapter(val products: List<Product>,
                  val onClickListener: (Product) -> Unit,
                  val onClickDelete:(Int) -> Unit)
    :RecyclerView.Adapter<ShopAdapter.ViewHolder>(){

    private lateinit var context: Context
    class ViewHolder(v: View): RecyclerView.ViewHolder(v){
        var songName: TextView
        var ivProduct: ImageView
        var tvShock: TextView
        var tvPrice: TextView
        var rowProductLinear: RelativeLayout
        var imBtnRemoveCard: ImageButton
        init {
            songName = v.findViewById(R.id.tvNameProduct)
            ivProduct = v.findViewById(R.id.ivProduct)
            rowProductLinear = v.findViewById(R.id.rowProductLinear)
            imBtnRemoveCard = v.findViewById(R.id.imBtnRemoveCard)
            tvShock = v.findViewById(R.id.tvShock)
            tvPrice = v.findViewById(R.id.tvPrice)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context=parent.context
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout_shop, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = products[position]
        holder.songName.text = item.name
        holder.tvShock.text = "Cantidad: ${item.stock}"
        holder.tvPrice.text = "Precio: $${item.unit_price}"
        Glide.with(context)
            .load(item.image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.ivProduct)

        with(holder){

            imBtnRemoveCard.setOnClickListener {
                onClickListener(item)
                onClickDelete(adapterPosition)
               // ShopActivity().onClickDelete(item)
            }
        }

    }

    override fun getItemCount(): Int = products.size
}
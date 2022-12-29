package com.david4vilac.legostore.usecases.rows

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.david4vilac.legostore.model.Product
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.david4vilac.legostore.HomeActivity
import com.david4vilac.legostore.R
import com.david4vilac.legostore.usecases.preferences.ProductPrefs.Companion.prefs

//import com.david4vilac.lifecycle.SongPrefs.Companion.prefs


class ProductAdapter(val products: List<Product>, val con: HomeActivity)
    :RecyclerView.Adapter<ProductAdapter.ViewHolder>(){

    private lateinit var context: Context

    class ViewHolder(v: View): RecyclerView.ViewHolder(v){
        var songName: TextView
        var ivSong: ImageView
        var rowProductLinear: RelativeLayout
        init {
            songName = v.findViewById(R.id.tvNameProduct)
            ivSong = v.findViewById(R.id.ivProduct)
            rowProductLinear = v.findViewById(R.id.rowProductLinear)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context=parent.context
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p = products[position]
        holder.songName.text = p.name
        Glide.with(context)
            .load(p.image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            //.centerCrop()
            //.circleCrop()
            .into(holder.ivSong)

        //Picasso.get().load(p.image).into(holder.ivSong)

        with(holder){
            rowProductLinear.setOnClickListener {
                Toast.makeText(context, "${p.name}", Toast.LENGTH_SHORT).show()
                con.searchByName(p.id)
                /*prefs.saveSongAuthor(p.author)

                prefs.saveSong(p.mediaPlayer)*/

            }
        }

    }

    override fun getItemCount(): Int = products.size
}
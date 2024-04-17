package com.example.newsapp2.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp2.MyApplication
import com.example.newsapp2.R
import com.example.newsapp2.entity.NewsEntity
import com.example.newsapp2.view.MainActivity
import com.example.newsapp2.view.MainActivity3

class NewsAdapter2(private val mContext: MainActivity, var articles: List<NewsEntity>): RecyclerView.Adapter<NewsAdapter2.MyViewHolder>() {

    inner class MyViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        var txtTitle = view.findViewById<TextView>(R.id.txtTitle)
        var txtDesc = view.findViewById<TextView>(R.id.txtDesc)
        var imgView = view.findViewById<ImageView>(R.id.imgView)
        var mLayout = view.findViewById<LinearLayout>(R.id.mLayout)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item3, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return articles!!.size
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {

        var data = articles[position]

        holder.txtTitle.text = data.title
        holder.txtDesc.text = data.description

        Glide.with(mContext).load(data.imageUrl).placeholder(R.drawable.ic_launcher_background).into(holder.imgView)

        holder.mLayout.setOnClickListener {

            MyApplication.fromRoom = true
            mContext.startActivity(Intent(mContext, MainActivity3::class.java).putExtra("article_data", data).addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP))
        }

    }
}
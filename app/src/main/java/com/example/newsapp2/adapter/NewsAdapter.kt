package com.example.newsapp2.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp2.R
import com.example.newsapp2.model.Articles
import com.example.newsapp2.view.MainActivity2
import com.example.newsapp2.view.MainActivity3

class NewsAdapter(val mContext: Context, var articles: ArrayList<Articles>): RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        var txtTitle = view.findViewById<TextView>(R.id.txtTitle)
        var txtDesc = view.findViewById<TextView>(R.id.txtDesc)
        var imgView = view.findViewById<ImageView>(R.id.imgView)
        var mLayout = view.findViewById<LinearLayout>(R.id.mLayout)

    }

    fun notifyAdapter(articles: ArrayList<Articles>){
        this.articles = articles
        notifyDataSetChanged()

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

        Glide.with(mContext).load(data.urlToImage).into(holder.imgView)

        holder.mLayout.setOnClickListener {

            mContext.startActivity(Intent(mContext, MainActivity2::class.java).putExtra("article_data", data))
        }

    }
}
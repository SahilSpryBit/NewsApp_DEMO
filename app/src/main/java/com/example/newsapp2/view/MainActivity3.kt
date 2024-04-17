package com.example.newsapp2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.newsapp2.R
import com.example.newsapp2.entity.NewsEntity
import com.example.newsapp2.model.Articles

class MainActivity3 : AppCompatActivity() {

    lateinit var articles: NewsEntity

    lateinit var imageView: ImageView
    lateinit var title: TextView
    lateinit var desc: TextView
    lateinit var content: TextView
    lateinit var authorName: TextView
    lateinit var publishedAt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        imageView = findViewById(R.id.imgView)
        title = findViewById(R.id.title)
        desc = findViewById(R.id.desc)
        content = findViewById(R.id.content)
        authorName = findViewById(R.id.authorName)
        publishedAt = findViewById(R.id.publishedAt)

        getData()

        Glide.with(this).load(articles.imageUrl).placeholder(R.drawable.ic_launcher_background).into(imageView)
        title.text = articles.title
        desc.text = articles.description
        content.text = articles.content
        authorName.text = articles.author
        publishedAt.text = articles.publishedAt


    }

    fun getData(){

        val intent = intent
        if (intent != null) {
            articles = (intent.getParcelableExtra("article_data") as? NewsEntity)!!
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }
}
package com.example.newsapp2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.newsapp2.R
import com.example.newsapp2.model.Articles

class MainActivity2 : AppCompatActivity() {

    lateinit var articles: Articles

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

        Glide.with(this).load(articles.urlToImage).into(imageView)
        title.text = articles.title
        desc.text = articles.description
        content.text = articles.content
        authorName.text = articles.author
        publishedAt.text = articles.publishedAt


    }

    fun getData(){

        val intent = intent
        if (intent != null) {
            articles = (intent.getParcelableExtra("article_data") as? Articles)!!
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }
}
package com.aldyjrz.mountaindo.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.aldyjrz.mountaindo.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions


class NewsAdapter(private val dataNews: ArrayList<NewsModels>) : RecyclerView.Adapter<NewsAdapter.CardViewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return CardViewViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewViewHolder, pos: Int) {
        val news = dataNews[pos]

        Glide.with(holder.itemView.context)
                .load(news.getThumbnail())
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions().override(350, 550))

                .into(holder.imgPhoto)


        holder.tvTitle.text = news.getTitle()
        holder.tvDetail.text = news.getDescription()


        holder.readMore.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Share " + dataNews[holder.adapterPosition].getTitle(), Toast.LENGTH_SHORT).show() }
    }

    override fun getItemCount(): Int {
        return dataNews.size
    }

    inner class CardViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.news_img)
        var tvTitle: TextView = itemView.findViewById(R.id.tv_news_title)
        var tvDetail: TextView = itemView.findViewById(R.id.tv_news_detail)
        var readMore: TextView = itemView.findViewById(R.id.tv_author)
    }
}
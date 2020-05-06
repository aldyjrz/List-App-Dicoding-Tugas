package com.aldyjrz.mountaindo.Adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.aldyjrz.mountaindo.DetailActivity
import com.aldyjrz.mountaindo.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class NewsAdapter(private val dataNews: ArrayList<NewsModels>) : RecyclerView.Adapter<NewsAdapter.CardViewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return CardViewViewHolder(view)
    }




    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CardViewViewHolder, pos: Int) {
        val news = dataNews[pos]

        Glide.with(holder.itemView.context)
                .load(news.getImage())
                .apply(RequestOptions().override(150, 220))
                .into(holder.imgPhoto)

        holder.tvTitle.text = news.getTitle()
        holder.tvDetail.text = news.getDescription()
        holder.author.text = news.getAuthor()
        if(news.getAuthor().equals("null")){
            holder.author.text = "Tidak Diketahui"
        }
        holder.tvDate.text = news.getPublishDate()
        holder.cardView.setOnClickListener {
            val i = Intent(holder.itemView.context, DetailActivity::class.java)



            val tanggal = dataNews[holder.adapterPosition].getPublishDate()
            val img = dataNews[holder.adapterPosition].getImage()
            val judul = dataNews[holder.adapterPosition].getTitle()
            val link = dataNews[holder.adapterPosition].getLink()
            val nama = dataNews[holder.adapterPosition].getAuthor()
            val source = dataNews[holder.adapterPosition].getSourceName()
            val konten = dataNews[holder.adapterPosition].getContent()

            i.putExtra("img", img)
            i.putExtra("nama", nama)
            i.putExtra("source", source)
            i.putExtra("tanggal", tanggal)
            i.putExtra("judul", judul)
            i.putExtra("link", link)
            i.putExtra("konten", konten)

            holder.itemView.context.startActivity(i)
            //goto detailact
        }
    }

    override fun getItemCount(): Int {
        return dataNews.size
    }

    inner class CardViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //inisialisasi resource dengan id masing2
        var imgPhoto: ImageView = itemView.findViewById(R.id.news_img)
        var tvTitle: TextView = itemView.findViewById(R.id.tv_news_title2)
        var tvDetail: TextView = itemView.findViewById(R.id.tv_news_detail2)
        var author: TextView = itemView.findViewById(R.id.tv_author)
        var tvDate: TextView = itemView.findViewById(R.id.tv_date)
        var cardView: CardView = itemView.findViewById(R.id.cardView)
    }
}
package com.example.wnews

import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wnews.databinding.NewsItemBinding
import com.example.wnews.model.News


class DiffCallback : DiffUtil.ItemCallback<News>() {

    override fun areItemsTheSame(oldItem: News, newItem: News) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: News, newItem: News) =
        oldItem == newItem
}

class NewsAdapter2 (private val items: MutableList<News>, val cellClickListener: (news: News)->Unit) : ListAdapter<News,NewsAdapter2.NewsViewHolder>(DiffCallback()) {

    lateinit var binding: NewsItemBinding

     var UserId : Int = 0




    fun updateUser(userId: Int) {
        UserId = userId
    }

    fun updateData(arrayList: ArrayList<News>) {
        items.clear()
        println(items)
        items.addAll(arrayList)
        println(items)
    }

   // fun updateLikes(pressedNewsId: Int){
     //   if(pressedNewsId == items.id){
       //     items.likes.add(UserId)
       // }
   // }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        binding = NewsItemBinding.inflate(layoutInflater, parent, false)

        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        val news = items[position]

        binding.likeButton.setOnClickListener{

            cellClickListener(news)
            // updateLikes(UserId, news)

            val userLikes = news.likes.contains(UserId)
            if (userLikes) {
                binding.likeButton.setBackgroundResource(R.mipmap.ic_like_on)

            } else binding.likeButton.setBackgroundResource(R.mipmap.ic_like_off)
            notifyItemChanged(position)
        }


        Glide.with(binding.newsImage)
            .load("https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/No-Image-Placeholder.svg/1665px-No-Image-Placeholder.svg.png")
            .into(binding.newsImage)

        binding.commenter.text = news.commenter
        binding.comment.text = news.comment



        val userLikes = news.likes.contains(UserId)

        if (userLikes){
            binding.likeButton.setBackgroundResource(R.mipmap.ic_like_on)
        }
    }

    class NewsViewHolder(binding: NewsItemBinding) : RecyclerView.ViewHolder( binding.root ){
    }

    override fun getItemCount(): Int {
        return items.size
    }
}








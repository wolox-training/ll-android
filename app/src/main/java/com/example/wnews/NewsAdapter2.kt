package com.example.wnews

import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wnews.databinding.NewsItemBinding
import com.example.wnews.model.News
import okhttp3.internal.notify

private const val ARG_DONE = "arg.done"

class DiffCallback : DiffUtil.ItemCallback<News>() {


    override fun areItemsTheSame(oldItem: News, newItem: News) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: News, newItem: News) =
        oldItem == newItem

    override fun getChangePayload(oldItem: News, newItem: News): Any? {
        if (oldItem.id == newItem.id) {
            return if (oldItem.done == newItem.done) {
                super.getChangePayload(oldItem, newItem)
            } else {
                val diff = Bundle()
                diff.putBoolean(ARG_DONE, newItem.done)
                diff
            }
        }

        return super.getChangePayload(oldItem, newItem)
    }
}



class NewsAdapter2 (private val items: ArrayList<News>, val cellClickListener: (news: News)->Unit) : ListAdapter<News,NewsAdapter2.NewsViewHolder>(DiffCallback()) {

    lateinit var binding: NewsItemBinding

     var UserId : Int = 0

    fun updateUser(userId: Int) {
        UserId = userId

    }

    fun updateData(arrayList: ArrayList<News>) {
        items.addAll(arrayList)
    }


    class NewsViewHolder(binding: NewsItemBinding) : RecyclerView.ViewHolder( binding.root ){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        binding = NewsItemBinding.inflate(layoutInflater, parent, false)

        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        val news = items[position]

      fun test (position: Int) {

          val userLikes = news.likes.contains(UserId)

          if (userLikes) {
              news.likes.remove(UserId)
              binding.likeButton.setBackgroundResource(R.mipmap.ic_like_off)
              notifyItemChanged(position)
              getItemCount()
              getItemViewType(position)

          } else {
              news.likes.add(UserId)
              binding.likeButton.setBackgroundResource(R.mipmap.ic_like_on)
              notifyItemChanged(position)
              getItemCount()
              getItemViewType(position)
          }
      }

        binding.commenter.text = news.commenter
        binding.comment.text = news.comment


        binding.likeButton.setOnClickListener{
             cellClickListener(news)
            test(position)
        }

        Glide.with(binding.newsImage)
            .load("https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/No-Image-Placeholder.svg/1665px-No-Image-Placeholder.svg.png")
            .into(binding.newsImage)


        val userLikes = news.likes.contains(UserId)

        if (userLikes){
            binding.likeButton.setBackgroundResource(R.mipmap.ic_like_on)
        }

    }



    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return position
    }
    }










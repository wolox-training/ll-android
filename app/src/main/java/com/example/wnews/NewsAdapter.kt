package com.example.wnews
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wnews.databinding.NewsItemBinding
import com.example.wnews.model.News


class NewsAdapter(private val mList: ArrayList<News> , private var UserId : Int,  private val cellClickListener: (news: News)->Unit) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(){
    lateinit var binding: NewsItemBinding

    fun updateUser (userId : Int) {
        UserId = userId
    }

    fun updateData (arrayList : ArrayList<News>){
        mList.addAll(arrayList)
    }

    class NewsViewHolder(binding: NewsItemBinding) : RecyclerView.ViewHolder( binding.root ){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        binding = NewsItemBinding.inflate(layoutInflater, parent, false)

        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(newsholder: NewsViewHolder, position: Int) {

        val news = mList[position]

        val userLikes = news.likes.contains(UserId)

        if (userLikes) {
            binding.likeButton.setBackgroundResource(R.mipmap.ic_like_on)
        }

        Glide.with(binding.newsImage)
            .load("https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/No-Image-Placeholder.svg/1665px-No-Image-Placeholder.svg.png")
            .into(binding.newsImage)

        binding.commenter.text = news.commenter
        binding.comment.text = news.comment


        binding.likeButton.setOnClickListener() {
            cellClickListener.invoke(news)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}

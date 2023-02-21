package com.example.moviesearch

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesearch.databinding.ItemListBinding
import java.security.AccessController.getContext

class MovieAdapter(private val viewModel: MyViewModel) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun setContents(pos: Int) {
            with (viewModel.getItem(pos)) {
                val one = title.replace("<b>", "")
                val two = one.replace("</b>", "")

                binding.contents.text = "제목 : ${two}\n출시 : $pubDate\n평점 : $userRating\n"

                Glide.with(binding.movieImage).load(image).into(binding.movieImage)

                binding.movieImage.setOnClickListener {
                    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                    itemView.context.startActivity(webIntent)
                }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemListBinding.inflate(layoutInflater, viewGroup, false)
        val viewHolder = ViewHolder(binding)

        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.setContents(position)

    }

    override fun getItemCount() = viewModel.itemsSize
}
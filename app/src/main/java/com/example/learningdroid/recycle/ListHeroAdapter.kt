package com.example.learningdroid.recycle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.learningdroid.databinding.ItemRowHeroBinding
import com.example.learningdroid.datapass.Hero

class ListHeroAdapter(private val listHero: ArrayList<Hero>) : RecyclerView.Adapter<ListHeroAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowHeroBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val hero = listHero[position]

        Glide.with(holder.itemView.context)
            .load(hero.photo)
            .into(holder.binding.imgItemPhoto)

        holder.binding.tvItemName.text = hero.name
        holder.binding.tvItemDescription.text = hero.description

        holder.itemView.setOnClickListener {
            if (::onItemClickCallback.isInitialized) {
                onItemClickCallback.onItemClicked(hero, holder.binding.imgItemPhoto)
            }
        }
    }

    override fun getItemCount(): Int = listHero.size

    class ListViewHolder(var binding: ItemRowHeroBinding) : RecyclerView.ViewHolder(binding.root)
    interface OnItemClickCallback {
        fun onItemClicked(data: Hero, sharedView: android.view.View)
    }
}
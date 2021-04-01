package com.rahat.blog_test.view.blogs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.rahat.blog_test.R
import com.rahat.blog_test.data.Blog

class BlogAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Blog>() {

        override fun areItemsTheSame(oldItem: Blog, newItem: Blog): Boolean {
            return oldItem.p_id == oldItem.p_id
        }

        override fun areContentsTheSame(oldItem: Blog, newItem: Blog): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return BlogViewholder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_blog,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BlogViewholder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Blog>) {
        differ.submitList(list)
    }

    class BlogViewholder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.text_view_name)
        val blogImage: ImageView = itemView.findViewById(R.id.blog_image)

        fun bind(item: Blog) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            textView.text = item.title
            blogImage.load(item.cover_photo) {
                crossfade(true)

            }

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Blog)
    }
}

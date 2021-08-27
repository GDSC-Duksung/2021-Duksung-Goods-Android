package com.example.duksunggoodsplatform_2021_android.community

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.duksunggoodsplatform_2021_android.R

class GoodsCommunityAdapter(private val onClick: (CommunityPost) -> Unit) :
    ListAdapter<CommunityPost, GoodsCommunityAdapter.GoodsCommunityViewHolder>(GoodsCommunityDiffCallback) {

    class GoodsCommunityViewHolder(itemView: View, val onClick: (CommunityPost) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val postUsernameTextView: TextView = itemView.findViewById(R.id.tv_depositor_name)
        private val postContentsTextView: TextView = itemView.findViewById(R.id.tv_post_contents)
        private var currentCommunityPost: CommunityPost? = null

        init {
            Log.d("ummm", "init?")
            itemView.setOnClickListener {
                // TODO: 게시글 자세히 + 댓글 프레그먼트
            }
        }

        fun bind(communityPost: CommunityPost) {
            currentCommunityPost = communityPost

            postUsernameTextView.text = communityPost.username
            postContentsTextView.text = communityPost.contents
            Log.d("ummm", "is it working?")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsCommunityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.goods_community_post_item, parent, false)
        return GoodsCommunityViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: GoodsCommunityViewHolder, position: Int) {
        val communityPost = getItem(position)
        holder.bind(communityPost)
    }
}

object GoodsCommunityDiffCallback : DiffUtil.ItemCallback<CommunityPost>() {
    override fun areItemsTheSame(oldItem: CommunityPost, newItem: CommunityPost): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CommunityPost, newItem: CommunityPost): Boolean {
        return oldItem.id == newItem.id
    }
}

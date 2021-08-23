package com.nomadev.aplikasigithubuser_submission2.ui.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nomadev.aplikasigithubuser_submission2.R
import com.nomadev.aplikasigithubuser_submission2.databinding.ItemUserBinding
import com.nomadev.aplikasigithubuser_submission2.model.ItemsModel

import android.content.Intent
import android.net.Uri


class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private val mData = ArrayList<ItemsModel>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(items: ArrayList<ItemsModel>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(mView)
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemUserBinding.bind(itemView)

        fun bind(itemsModel: ItemsModel) {
            with(itemView) {

                binding.root.setOnClickListener {
                    onItemClickCallback?.onItemClicked(itemsModel)
                }

                Glide.with(itemView)
                    .load(itemsModel.avatar_url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.ivAvatar)
                binding.tvName.text = itemsModel.login
                binding.tvProfileUrl.text = itemsModel.html_url
                binding.ibLink.setOnClickListener {
                    val uri = Uri.parse(itemsModel.html_url)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    context.startActivity(intent)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsModel)
    }
}
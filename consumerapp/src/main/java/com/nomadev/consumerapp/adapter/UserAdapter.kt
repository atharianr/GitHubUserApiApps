package com.nomadev.consumerapp.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nomadev.consumerapp.R
import com.nomadev.consumerapp.databinding.ItemUserBinding
import com.nomadev.consumerapp.model.ItemsModel


class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private val mData = ArrayList<ItemsModel>()

    fun setData(items: ArrayList<ItemsModel>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(mView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemUserBinding.bind(itemView)

        fun bind(itemsModel: ItemsModel) {
            with(itemView) {
                Glide.with(itemView)
                    .load(itemsModel.avatarUrl)
                    .into(binding.ivAvatar)
                binding.tvName.text = itemsModel.login
                binding.tvProfileUrl.text = itemsModel.htmlUrl
                binding.ibLink.setOnClickListener {
                    val uri = Uri.parse(itemsModel.htmlUrl)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    context.startActivity(intent)
                }
            }
        }
    }
}
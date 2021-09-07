package com.nomadev.consumerapp.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nomadev.consumerapp.R
import com.nomadev.consumerapp.adapter.UserAdapter
import com.nomadev.consumerapp.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.app_name)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setLogo(R.drawable.github_logo_action_bar)
        supportActionBar?.setDisplayUseLogoEnabled(true)

        adapter = UserAdapter()

        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.adapter = adapter

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        viewModel.setFavoriteUser(this)

        viewModel.getFavoriteUser().observe(this, {
            if (it != null) {
                if (it.size > 0) {
                    showInfo(false)
                    adapter.setData(it)
                    adapter.notifyDataSetChanged()
                } else {
                    showInfo(true)
                    binding.rvUser.visibility = View.GONE
                }
            }
        })
    }

    private fun showInfo(state: Boolean) {
        if (state) {
            binding.ivInfo.visibility = View.VISIBLE
            binding.tvInfo.visibility = View.VISIBLE
            binding.tvInfo2.visibility = View.VISIBLE
        } else {
            binding.ivInfo.visibility = View.GONE
            binding.tvInfo.visibility = View.GONE
            binding.tvInfo2.visibility = View.GONE
        }
    }
}
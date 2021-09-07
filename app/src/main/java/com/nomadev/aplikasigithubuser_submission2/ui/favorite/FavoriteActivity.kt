package com.nomadev.aplikasigithubuser_submission2.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nomadev.aplikasigithubuser_submission2.R
import com.nomadev.aplikasigithubuser_submission2.data.local.FavoriteUser
import com.nomadev.aplikasigithubuser_submission2.databinding.ActivityFavoriteBinding
import com.nomadev.aplikasigithubuser_submission2.domain.model.ItemsModel
import com.nomadev.aplikasigithubuser_submission2.ui.adapter.UserAdapter
import com.nomadev.aplikasigithubuser_submission2.ui.detailuser.DetailUserActivity

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = UserAdapter()
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsModel) {
                Intent(this@FavoriteActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_AVATAR_URL, data.avatarUrl)
                    startActivity(it)
                }
            }
        })

        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.adapter = adapter

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        viewModel.getFavoriteUser()?.observe(this, {
            if (it != null) {
                if (it.isNotEmpty()) {
                    showInfo(false)
                    val list = mapList(it)
                    adapter.setData(list)
                    adapter.notifyDataSetChanged()
                } else {
                    showInfo(true)
                    binding.rvUser.visibility = View.GONE
                }

            }
        })
    }

    private fun mapList(it: List<FavoriteUser>): ArrayList<ItemsModel> {
        val listUser = ArrayList<ItemsModel>()
        for (user in it) {
            val userMapped = ItemsModel(
                user.login,
                user.id,
                user.avatarUrl,
                user.htmlUrl
            )
            listUser.add(userMapped)
        }
        return listUser
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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
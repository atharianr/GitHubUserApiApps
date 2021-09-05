package com.nomadev.aplikasigithubuser_submission2.ui.detailuser.follow

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nomadev.aplikasigithubuser_submission2.R
import com.nomadev.aplikasigithubuser_submission2.databinding.FragmentFollowBinding
import com.nomadev.aplikasigithubuser_submission2.domain.model.ItemsModel
import com.nomadev.aplikasigithubuser_submission2.ui.detailuser.DetailUserActivity
import com.nomadev.aplikasigithubuser_submission2.ui.adapter.UserAdapter

class FollowersFragment : Fragment(R.layout.fragment_follow) {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var followersViewModel: FollowersViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username: String

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()
        _binding = FragmentFollowBinding.bind(view)

        adapter = UserAdapter()

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ItemsModel) {
                Intent(activity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    startActivity(it)
                }
            }
        })

        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.layoutManager = LinearLayoutManager(this.activity)
        binding.rvUser.adapter = adapter
        adapter.notifyDataSetChanged()

        showLoading(true)

        followersViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowersViewModel::class.java)
        followersViewModel.setListFollowers(username)
        followersViewModel.getListFollowers().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setData(it)
                showLoading(false)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}
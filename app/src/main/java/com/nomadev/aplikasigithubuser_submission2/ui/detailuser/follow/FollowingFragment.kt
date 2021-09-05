package com.nomadev.aplikasigithubuser_submission2.ui.detailuser.follow

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nomadev.aplikasigithubuser_submission2.R
import com.nomadev.aplikasigithubuser_submission2.databinding.FragmentFollowBinding
import com.nomadev.aplikasigithubuser_submission2.domain.model.ItemsModel
import com.nomadev.aplikasigithubuser_submission2.ui.detailuser.DetailUserActivity
import com.nomadev.aplikasigithubuser_submission2.ui.adapter.UserAdapter

class FollowingFragment : Fragment(R.layout.fragment_follow) {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var followingViewModel: FollowingViewModel
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

        followingViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewModel::class.java)
        followingViewModel.setListFollowing(username)
        followingViewModel.getListFollowing().observe(viewLifecycleOwner, {
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
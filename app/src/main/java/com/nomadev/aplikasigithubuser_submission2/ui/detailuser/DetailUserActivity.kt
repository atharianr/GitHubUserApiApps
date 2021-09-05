package com.nomadev.aplikasigithubuser_submission2.ui.detailuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.nomadev.aplikasigithubuser_submission2.R
import com.nomadev.aplikasigithubuser_submission2.databinding.ActivityDetailUserBinding
import com.nomadev.aplikasigithubuser_submission2.ui.detailuser.follow.SectionPagerAdapter

class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following,
        )
    }

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var detailUserViewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.username)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        detailUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailUserViewModel::class.java)

//        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
//        binding.viewPager.adapter = sectionPagerAdapter
//        binding.tabLayout.setupWithViewPager(binding.viewPager)

        getDetailUser(username.toString())
        setUpViewPager(username.toString())
    }

    private fun setUpViewPager(username: String) {
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)
        val sectionsPagerAdapter = SectionPagerAdapter(this, bundle)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getDetailUser(username: String) {
        detailUserViewModel.setUserDetail(username)
        detailUserViewModel.status.observe(this, {
            if (it != null) {
                Log.d("STATUS_DETAIL", it.toString())
                if (it == false) {
                    Toast.makeText(this, R.string.check_connection_warning, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })

        detailUserViewModel.getUserDetail().observe(this, {
            if (it != null) {
                Glide.with(this)
                    .load(it.avatarUrl)
                    .into(binding.ivAvatar)
                binding.tvName.text = it.name
                supportActionBar?.title = "@${it.login}"
                binding.tvFollowersAngka.text = it.followers.toString()
                binding.tvFollowingAngka.text = it.following.toString()
                binding.tvRepositoriesAngka.text = it.publicRepos.toString()
                binding.tvBio.text = it.bio
                binding.tvCompany.text = it.company
                binding.tvLocation.text = it.location
                if (it.bio == null) binding.tvBio.visibility = View.GONE
                if (it.company == null) binding.tvCompany.visibility = View.GONE
                if (it.location == null) binding.tvLocation.visibility = View.GONE
            }
        })
    }
}
package com.nomadev.aplikasigithubuser_submission2.ui.detailuser

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import android.widget.ToggleButton
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.nomadev.aplikasigithubuser_submission2.R
import com.nomadev.aplikasigithubuser_submission2.databinding.ActivityDetailUserBinding
import com.nomadev.aplikasigithubuser_submission2.ui.detailuser.follow.SectionPagerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following,
        )
    }

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var detailUserViewModel: DetailUserViewModel
    private var username: String = ""
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.username)

        username = intent.getStringExtra(EXTRA_USERNAME).toString()
        id = intent.getIntExtra(EXTRA_ID, 0)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        detailUserViewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)

        getDetailUser(username)
        setUpViewPager(username)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.detail_menu, menu)

        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val actionViewItem = menu?.findItem(R.id.toggle_favorite)
        val view = actionViewItem?.actionView as View

        val toggleFav = view.findViewById(R.id.btn_toggle_fav) as ToggleButton

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = detailUserViewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        toggleFav.isChecked = true
                        _isChecked = true
                    } else {
                        toggleFav.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        toggleFav.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked) {
                detailUserViewModel.addToFavorite(username, id)
                Toast.makeText(this, "saved to db", Toast.LENGTH_SHORT).show()
            } else {
                detailUserViewModel.removeFromFavorite(id)
                Toast.makeText(this, "removed to db", Toast.LENGTH_SHORT).show()
            }

            toggleFav.isChecked = _isChecked
        }

        return super.onPrepareOptionsMenu(menu)
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.toggle_favorite -> {
//                var _isChecked = false
//                CoroutineScope(Dispatchers.IO).launch {
//                    val count = detailUserViewModel.checkUser(id)
//                    withContext(Dispatchers.Main) {
//                        if (count != null) {
//                            if (count > 0) {
//                                bindingFav.btnToggleFav.isChecked = true
//                                _isChecked = true
//                            } else {
//                                bindingFav.btnToggleFav.isChecked = false
//                                _isChecked = false
//                            }
//                        }
//                    }
//                }
//
//                bindingFav.btnToggleFav.setOnClickListener {
//                    _isChecked = !_isChecked
//                    if (_isChecked) {
//                        detailUserViewModel.addToFavorite(username, id)
//                    } else {
//                        detailUserViewModel.removeFromFavorite(id)
//                    }
//
//                    bindingFav.btnToggleFav.isChecked = _isChecked
//                }
//
//                return true
//            }
//            else -> return true
//        }
//    }

//    private fun setUpFavorite(username: String, id: Int) {
//        var _isChecked = false
//        CoroutineScope(Dispatchers.IO).launch {
//            val count = detailUserViewModel.checkUser(id)
//            withContext(Dispatchers.Main) {
//                if (count != null) {
//                    if (count > 0) {
//                        binding.btnToggleFav.isChecked = true
//                        _isChecked = true
//                    } else {
//                        binding.btnToggleFav.isChecked = false
//                        _isChecked = false
//                    }
//                }
//            }
//        }
//
//        binding.btnToggleFav.setOnClickListener {
//            _isChecked = !_isChecked
//            if (_isChecked) {
//                detailUserViewModel.addToFavorite(username, id)
//
//            } else {
//                detailUserViewModel.removeFromFavorite(id)
//            }
//
//            binding.btnToggleFav.isChecked = _isChecked
//        }
//    }

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
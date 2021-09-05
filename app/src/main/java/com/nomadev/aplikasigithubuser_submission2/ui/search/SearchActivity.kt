package com.nomadev.aplikasigithubuser_submission2.ui.search

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nomadev.aplikasigithubuser_submission2.R
import com.nomadev.aplikasigithubuser_submission2.databinding.ActivitySearchBinding
import com.nomadev.aplikasigithubuser_submission2.domain.model.ItemsModel
import com.nomadev.aplikasigithubuser_submission2.ui.adapter.UserAdapter
import com.nomadev.aplikasigithubuser_submission2.ui.detailuser.DetailUserActivity

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var allUserViewModel: AllUserViewModel
    private lateinit var adapter: UserAdapter
    var usernameString: String = ""

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.title)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setLogo(R.drawable.github_logo_small)
        supportActionBar?.setDisplayUseLogoEnabled(true)

        showLoading(true)

        adapter = UserAdapter()

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsModel) {
                showLoading(false)
                Intent(this@SearchActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    startActivity(it)
                }
            }
        })

        binding.rvSearch.layoutManager = LinearLayoutManager(this)
        binding.rvSearch.setHasFixedSize(true)
        binding.rvSearch.adapter = adapter
        adapter.notifyDataSetChanged()

        allUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(AllUserViewModel::class.java)

        searchViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(SearchViewModel::class.java)

        getAllUser()
        if (savedInstanceState != null) {
            usernameString = savedInstanceState.getString("USERNAME_STRING").toString()
            startSearch(usernameString)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                usernameString = query.toString()
                startSearch(usernameString)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    private fun getAllUser() {
        showLoading(true)
        allUserViewModel.setListAllUser()
        allUserViewModel.status.observe(this, {
            if (it != null) {
                Log.d("STATUS_SEARCH", it.toString())
                if (it == false) {
                    Toast.makeText(this, R.string.check_connection_warning, Toast.LENGTH_SHORT)
                        .show()
                    binding.rvSearch.visibility = View.GONE
                    showLoading(false)
                    showDisconnected(true)
                }
            }
        })

        allUserViewModel.getListAllUser().observe(this, {
            showLoading(false)
            showDisconnected(false)
            if (it != null) {
                if (it.size == 0) {
                    showInfo(true)
                    binding.rvSearch.visibility = View.GONE
                } else {
                    showInfo(false)
                    binding.rvSearch.visibility = View.VISIBLE
                    adapter.setData(it)
                }
            }
        })
    }

    private fun startSearch(query: String) {
        if (query.isNotEmpty()) {
            showLoading(true)
            searchViewModel.setSearchUser(query)
            searchViewModel.status.observe(this, {
                if (it != null) {
                    Log.d("STATUS_SEARCH", it.toString())
                    if (it == false) {
                        Toast.makeText(this, R.string.check_connection_warning, Toast.LENGTH_SHORT)
                            .show()
                        binding.rvSearch.visibility = View.GONE
                        showLoading(false)
                        showDisconnected(true)
                    }
                }
            })

            searchViewModel.getSearchUser().observe(this@SearchActivity, {
                showLoading(false)
                showDisconnected(false)
                if (it != null) {
                    if (it.size == 0) {
                        showInfo(true)
                        binding.rvSearch.visibility = View.GONE
                    } else {
                        showInfo(false)
                        binding.rvSearch.visibility = View.VISIBLE
                        adapter.setData(it)
                    }
                }
            })
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showDisconnected(state: Boolean) {
        if (state) {
            binding.ivDisconnected.visibility = View.VISIBLE
            binding.tvDisconnected.visibility = View.VISIBLE
            binding.tvDisconnected2.visibility = View.VISIBLE
        } else {
            binding.ivDisconnected.visibility = View.GONE
            binding.tvDisconnected.visibility = View.GONE
            binding.tvDisconnected2.visibility = View.GONE
        }
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("USERNAME_STRING", usernameString)
    }
}
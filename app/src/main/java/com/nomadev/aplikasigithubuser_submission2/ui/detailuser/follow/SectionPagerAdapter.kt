package com.nomadev.aplikasigithubuser_submission2.ui.detailuser.follow

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.nomadev.aplikasigithubuser_submission2.R

class SectionPagerAdapter(private val mContext: Context, fragmentManager: FragmentManager, bundle: Bundle) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var fragmentBundle: Bundle = bundle

    @StringRes
    private val tabTitle = intArrayOf(R.string.followers, R.string.following)

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mContext.resources.getString(tabTitle[position])
    }
}
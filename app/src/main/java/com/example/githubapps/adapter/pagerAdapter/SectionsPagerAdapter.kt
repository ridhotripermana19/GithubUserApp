package com.example.githubapps.adapter.pagerAdapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.githubapps.R
import com.example.githubapps.view.fragment.FollowerFragment
import com.example.githubapps.view.fragment.FollowingFragment

class SectionsPagerAdapter(private val mContext: Context, fm: FragmentManager, private val bundle: Bundle): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.tab_follower, R.string.tab_following)

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment =
                    FollowerFragment()
                fragment.setArguments(bundle)
            }

            1 -> {
                fragment =
                    FollowingFragment()
                fragment.setArguments(bundle)
            }
        }
        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 2
    }
}
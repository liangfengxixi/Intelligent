package com.xixi.intelligent.widget

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import me.yokeyword.fragmentation.SupportFragment

/**
 * Created by xixi on 2019/12/2.
 */
class PagerFragmentAdapter(fm: FragmentManager, val fragments: ArrayList<SupportFragment>?, val mTitles: ArrayList<String>?)
    : FragmentPagerAdapter(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return fragments!![position]
    }

    override fun getCount(): Int {
        return mTitles!!.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mTitles!![position]
    }
}
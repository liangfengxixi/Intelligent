package com.xixi.intelligent.ui.fragment

import android.content.pm.ActivityInfo
import android.os.Bundle
import com.xixi.intelligent.R
import com.xixi.intelligent.base.BaseSupportFragment
import com.xixi.intelligent.utils.L
import com.xixi.intelligent.widget.PagerFragmentAdapter
import kotlinx.android.synthetic.main.fragment_panel.*
import kotlinx.android.synthetic.main.title_panel.*
import me.yokeyword.fragmentation.SupportFragment


/**
 * 看板
 */
class PanelFragment : BaseSupportFragment() {
    override fun getContentRes(): Int {
        return R.layout.fragment_panel
    }

    companion object {
        fun newInstance(): PanelFragment {
            return PanelFragment()
        }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        initTabList()
    }

    private fun initTabList(){

        val mTitles = arrayListOf<String>("生产看板", "设备看板");
        val mFragments = arrayListOf<SupportFragment>(Panel1Fragment.newInstance(),
            Panel2Fragment.newInstance())
        viewPager.adapter = PagerFragmentAdapter(childFragmentManager, mFragments, mTitles)
        viewPager.offscreenPageLimit = mTitles.size
        tabLayout.setViewPager(viewPager)
//        tabLayout.currentTab = mTitles.size-1
    }

}

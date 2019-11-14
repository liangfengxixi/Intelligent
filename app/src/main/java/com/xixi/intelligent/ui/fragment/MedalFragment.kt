package com.xixi.intelligent.ui.fragment

import android.os.Bundle
import com.xixi.intelligent.R
import com.xixi.intelligent.base.BaseSupportFragment
import kotlinx.android.synthetic.main.title_normal.*


/**
 * 我的勋章
 */
class MedalFragment : BaseSupportFragment() {
    override fun getContentRes(): Int {
        return R.layout.fragment_medal
    }

    companion object {
        fun newInstance(): MedalFragment {
            return MedalFragment()
        }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        page_title.text = "我的勋章"
    }
}

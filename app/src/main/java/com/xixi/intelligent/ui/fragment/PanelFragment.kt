package com.xixi.intelligent.ui.fragment

import android.os.Bundle
import com.xixi.intelligent.R
import com.xixi.intelligent.base.BaseSupportFragment
import kotlinx.android.synthetic.main.title_topbar.*


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
        page_title.text = "看板"
    }
}

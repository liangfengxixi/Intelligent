package com.xixi.intelligent.ui.fragment

import android.os.Bundle
import com.xixi.intelligent.R
import com.xixi.intelligent.base.BaseSupportFragment
import kotlinx.android.synthetic.main.title_normal.*


/**
 * 周/月报/年报
 */
class ReportFragment : BaseSupportFragment() {
    override fun getContentRes(): Int {
        return R.layout.fragment_report
    }

    companion object {
        fun newInstance(): ReportFragment {
            return ReportFragment()
        }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        page_title.text = "周/月报/年报"
    }
}

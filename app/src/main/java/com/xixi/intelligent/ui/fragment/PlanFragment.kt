package com.xixi.intelligent.ui.fragment

import android.os.Bundle
import com.xixi.intelligent.R
import com.xixi.intelligent.base.BaseSupportFragment
import kotlinx.android.synthetic.main.title_normal.*


/**
 * 工作计划
 */
class PlanFragment : BaseSupportFragment() {
    override fun getContentRes(): Int {
        return R.layout.fragment_plan
    }

    companion object {
        fun newInstance(): PlanFragment {
            return PlanFragment()
        }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        page_title.text = "工作计划"
    }
}

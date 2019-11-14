package com.xixi.intelligent.ui.fragment

import android.os.Bundle
import android.view.View
import butterknife.OnClick
import com.xixi.intelligent.R
import com.xixi.intelligent.base.BaseSupportFragment
import kotlinx.android.synthetic.main.title_topbar.*


/**
 * 绩效
 */
class PerformanceFragment : BaseSupportFragment() {
    override fun getContentRes(): Int {
        return R.layout.fragment_performance
    }

    companion object {
        fun newInstance(): PerformanceFragment {
            return PerformanceFragment()
        }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        page_title.text = "绩效"
    }

    @OnClick(R.id.layout_medal,R.id.layout_plan,R.id.layout_report)
    fun itemClick(view : View){
        when (view.id) {
            R.id.layout_medal -> start(MedalFragment.newInstance())
            R.id.layout_plan -> start(PlanFragment.newInstance())
            R.id.layout_report -> start(ReportFragment.newInstance())
        }
    }
}

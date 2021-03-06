package com.xixi.intelligent.ui.fragment

import android.os.Bundle
import com.xixi.intelligent.R
import com.xixi.intelligent.base.BaseSupportFragment


/**
 * 任务根布局
 */
class TaskRootFragment : BaseSupportFragment() {
    override fun getContentRes(): Int {
        return R.layout.fragment_root
    }

    companion object {
        fun newInstance(): TaskRootFragment {
            return TaskRootFragment()
        }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        if(findChildFragment(TaskFragment::class.java)==null){
            loadRootFragment(R.id.fl_root_container,TaskFragment.newInstance())
        }
    }

    override fun onBackPressedSupport(): Boolean {
        if(childFragmentManager.backStackEntryCount>1){
            popChild()
            return true
        }
        return super.onBackPressedSupport()
    }

}

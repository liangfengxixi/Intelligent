package com.xixi.intelligent.ui.fragment

import android.content.pm.ActivityInfo
import android.os.Bundle
import com.xixi.intelligent.R
import com.xixi.intelligent.base.BaseSupportFragment
import com.xixi.intelligent.utils.L


/**
 * 看板根布局
 */
class PanelRootFragment : BaseSupportFragment() {
    override fun getContentRes(): Int {
        return R.layout.fragment_root
    }

    companion object {
        fun newInstance(): PanelRootFragment {
            return PanelRootFragment()
        }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        if(findChildFragment(TaskFragment::class.java)==null){
            loadRootFragment(R.id.fl_root_container,PanelFragment.newInstance())
        }
    }

    override fun onBackPressedSupport(): Boolean {
        if(childFragmentManager.backStackEntryCount>1){
            popChild()
            return true
        }
        return super.onBackPressedSupport()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        L.e("root - $hidden")
        _mActivity.requestedOrientation =
            if (hidden) ActivityInfo.SCREEN_ORIENTATION_SENSOR else ActivityInfo.SCREEN_ORIENTATION_NOSENSOR
    }

}

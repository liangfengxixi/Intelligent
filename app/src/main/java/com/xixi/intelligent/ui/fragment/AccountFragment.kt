package com.xixi.intelligent.ui.fragment

import android.os.Bundle
import android.view.View
import butterknife.OnClick
import com.xixi.intelligent.R
import com.xixi.intelligent.base.BaseSupportFragment
import kotlinx.android.synthetic.main.title_normal.*


/**
 * 我的
 */
class AccountFragment : BaseSupportFragment() {
    override fun getContentRes(): Int {
        return R.layout.fragment_account
    }

    companion object {
        fun newInstance(): AccountFragment {
            return AccountFragment()
        }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        page_title.text = "我的"
    }

    @OnClick(R.id.rl_photo,R.id.layout_personal_data,R.id.layout_change_pwd,R.id.layout_opinion,
        R.id.layout_about)
    fun btnClick(view : View){
        when (view.id) {
            R.id.rl_photo -> ""
            R.id.layout_personal_data -> toast("个人资料")
            R.id.layout_change_pwd -> toast("修改密码")
            R.id.layout_opinion -> toast("意见反馈")
            R.id.layout_about -> toast("关于")
        }
    }
}

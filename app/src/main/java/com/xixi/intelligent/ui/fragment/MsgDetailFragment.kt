package com.xixi.intelligent.ui.fragment

import android.os.Bundle
import com.trello.rxlifecycle3.android.lifecycle.kotlin.bindToLifecycle
import com.xixi.intelligent.R
import com.xixi.intelligent.base.BaseSupportFragment
import com.xixi.intelligent.bean.BaseBean
import com.xixi.intelligent.bean.MsgBean
import com.xixi.intelligent.http.NetworkScheduler
import com.xixi.intelligent.http.subscriber.ApiObserver
import com.xixi.intelligent.utils.MyString
import kotlinx.android.synthetic.main.fragment_msg_detail.*
import kotlinx.android.synthetic.main.title_normal.*
import ocom.xixi.intelligent.http.ApiClient


/**
 * 消息详情
 */
class MsgDetailFragment : BaseSupportFragment() {

    var msgBean: MsgBean? = null

    override fun getContentRes(): Int {
        return R.layout.fragment_msg_detail
    }

    companion object {
        fun newInstance(bundle: Bundle?): MsgDetailFragment {
            val fragment = MsgDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        page_title.text = "消息详情"
        initData()
    }

    fun initData(){
        msgBean = arguments!!.getSerializable("taskBean") as MsgBean
        if(msgBean!=null){
            tv_mName.text = MyString.msgStatus(msgBean?.subject?:"")
            tv_time.text = msgBean?.receivedAt?:""
            tv_content.text = msgBean?.text?:""
            if(!msgBean!!.read){
                changeStatus()
            }
        }
    }

    fun changeStatus(){

        ApiClient.instance.kotlinService.changeMsgRead(msgBean?.id)
            .compose(NetworkScheduler.compose())
            .bindToLifecycle(this)
            .subscribe(object : ApiObserver<BaseBean<Any?>>() {
                override fun onSuccess(t: BaseBean<Any?>) {
                    if(t.isSuccess()){
                        setFragmentResult(101,null)
                    }
//                    if(t.isSuccess()){
//                        toast("提交成功")
//                        setFragmentResult(101,null)
//                        pop()
//                    }else{
//                        toast(t.msg?:"")
//                    }
                }

                override fun onError(msg: String?) {
//                    toast(msg)
                }
            })
    }

}

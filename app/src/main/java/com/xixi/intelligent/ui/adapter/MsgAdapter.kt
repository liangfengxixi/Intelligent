package com.xixi.intelligent.ui.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xixi.intelligent.R
import com.xixi.intelligent.bean.MsgBean
import com.xixi.intelligent.utils.MyString

/**
 * Created by xixi on 2019/11/9.
 */
class MsgAdapter(layoutResId: Int, data: List<MsgBean>) : BaseQuickAdapter<MsgBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: MsgBean?) {
        if (item != null) {
            helper?.setText(R.id.tv_mName, MyString.msgStatus(item.subject?:""))
            helper?.setText(R.id.tv_time, item.receivedAt?:"")
            helper?.setText(R.id.tv_content, item.text)

            var dot = helper!!.getView<TextView>(R.id.tv_dot)
            dot.isEnabled = item.read
//            helper?.addOnClickListener(R.id.btn_by)
//            if(item.type.toInt()<7){
//                helper?.setVisible(R.id.arrow_right,true)
//            }else{
//                helper?.setVisible(R.id.arrow_right,false)
//            }

        }
    }



}

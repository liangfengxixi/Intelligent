package com.xixi.intelligent.ui.adapter

import android.widget.TextView
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xixi.intelligent.R
import com.xixi.intelligent.bean.EquipBean

/**
 * Created by xixi on 2019/12/5.
 */
class EquipAdapter(layoutResId: Int, data: List<EquipBean>) : BaseQuickAdapter<EquipBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: EquipBean?) {
        if (item != null) {
            helper?.setText(R.id.tv_mName, item.equipmentName?:"")
            helper?.setText(R.id.tv_eName, "设备类型：${item.equipmentType.equipmentTypeName?:""}")
//            helper?.setText(R.id.tv_time, "计划时间：${item.startTime}")
            helper?.setText(R.id.tv_bl_item, item.equipmentNum?:"")

            var tv_status = helper!!.getView<TextView>(R.id.tv_status)
            when(item.status){
                "USEING"->{
                    tv_status.text = "启用中"
                    tv_status.setTextColor(ContextCompat.getColor(mContext,R.color.brown))
                }
                "UNUSED"->{
                    tv_status.text = "停用中"
                    tv_status.setTextColor(ContextCompat.getColor(mContext,R.color.equipment_status_unused))
                }
            }
//            helper?.addOnClickListener(R.id.btn_by)
//            if(item.type.toInt()<7){
//                helper?.setVisible(R.id.arrow_right,true)
//            }else{
//                helper?.setVisible(R.id.arrow_right,false)
//            }

        }
    }



}

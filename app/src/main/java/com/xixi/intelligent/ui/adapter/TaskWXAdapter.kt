package com.xixi.intelligent.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xixi.intelligent.R
import com.xixi.intelligent.bean.TaskBYBean
import com.xixi.intelligent.bean.TaskWXBean

/**
 * Created by xixi on 2019/11/9.
 */
class TaskWXAdapter(layoutResId: Int, data: List<TaskWXBean>) : BaseQuickAdapter<TaskWXBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: TaskWXBean?) {
        if (item != null) {
            helper?.setText(R.id.tv_mName, item.equEquipmentFault.faultName?:"")
            helper?.setText(R.id.tv_eName, "设备名：${item.equipment.equipmentName?:""}")
            helper?.setText(R.id.tv_time, "报修时间：${item.equEquipmentFault.faultDate}")
            helper?.setText(R.id.tv_bl_item, item.equFaultItem.faultItemName?:"")
            when(item.status){
                "UNFAULT"->{
                    helper?.setText(R.id.tv_status, "未完成")
                    helper?.setVisible(R.id.btn_by, true)
                }
                "FAULTED"->{
                    helper?.setText(R.id.tv_status, "已完成")
                    helper?.setVisible(R.id.btn_by, false)
                }
                "FAUITING"->{
                    helper?.setText(R.id.tv_status, "执行中")
                    helper?.setVisible(R.id.btn_by, false)
                }
            }
            helper?.addOnClickListener(R.id.btn_by)
//            if(item.type.toInt()<7){
//                helper?.setVisible(R.id.arrow_right,true)
//            }else{
//                helper?.setVisible(R.id.arrow_right,false)
//            }

        }
    }



}

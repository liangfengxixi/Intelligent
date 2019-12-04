package com.xixi.intelligent.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xixi.intelligent.R
import com.xixi.intelligent.bean.TaskBYBean

/**
 * Created by xixi on 2019/11/9.
 */
class TaskSBLBAdapter(layoutResId: Int, data: List<TaskBYBean>) : BaseQuickAdapter<TaskBYBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: TaskBYBean?) {
        if (item != null) {
            helper?.setText(R.id.tv_mName, item.equEquipmentMaintenance.equipmentMaintenanceName?:"")
            helper?.setText(R.id.tv_eName, "设备名：${item.equipment.equipmentName?:""}")
//            helper?.setText(R.id.tv_time, "计划时间：${item.startTime}")
            helper?.setText(R.id.tv_bl_item, item.equMaintenanceItem.maintenanceItemName)
            when(item.status){
                "UNMAINTENANCE"->{
                    helper?.setText(R.id.tv_status, "未完成")
                }
                "MAINTENANCED"->{
                    helper?.setText(R.id.tv_status, "已完成")
                }
                "MAINTENANCING"->{
                    helper?.setText(R.id.tv_status, "执行中")
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

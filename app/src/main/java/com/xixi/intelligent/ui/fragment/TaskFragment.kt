package com.xixi.intelligent.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import butterknife.OnClick
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.trello.rxlifecycle3.android.lifecycle.kotlin.bindToLifecycle
import com.xixi.intelligent.R
import com.xixi.intelligent.base.BaseSupportFragment
import com.xixi.intelligent.bean.BaseBean
import com.xixi.intelligent.bean.MainBean
import com.xixi.intelligent.http.NetworkScheduler
import com.xixi.intelligent.http.subscriber.ApiObserver
import com.xixi.intelligent.utils.SizeUtils
import com.xixi.intelligent.utils.TimeUtils
import kotlinx.android.synthetic.main.fragment_task.*
import kotlinx.android.synthetic.main.title_topbar.*
import ocom.xixi.intelligent.http.ApiClient
import java.util.*


/**
 * 任务
 */
class TaskFragment : BaseSupportFragment() {

    var entries = arrayListOf<PieEntry>()

    var colorList = arrayListOf<Int>()

    override fun getContentRes(): Int {
        return R.layout.fragment_task
    }

    companion object {
        fun newInstance(): TaskFragment {
            return TaskFragment()
        }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        page_title.text = "工作台"
        initview()
        initData()
    }

    fun initview(){
        colorList.add(ContextCompat.getColor(_mActivity,R.color.white))
        colorList.add(ContextCompat.getColor(_mActivity,R.color.pie_1))

        init7Day()

        initPie(chart1)
        initPie(chart2)
        initPie(chart3)
        initPie(chart4)
        initline(chart_line1)
        initline(chart_line3)
        initline(chart_line4)
        initline(chart_line5)
        initline(chart_line6)
    }

    fun initData(){
        getMainData()
//        setPieData()
//        setLineData()
    }

    fun init7Day(){
        var today = "${Calendar.getInstance().get(Calendar.MONTH)+1}/${Calendar.getInstance().get(Calendar.DAY_OF_MONTH)}"
        var oldDay = TimeUtils.getOldDate(-6)
        var mDay = "$oldDay~$today"
        time_1.text = mDay
        time_2.text = mDay
        time_3.text = mDay
        time_4.text = mDay
        time_5.text = mDay
        time_6.text = mDay
    }

    fun initPie(pieChart: PieChart){

        pieChart.setDrawHoleEnabled(true)
        pieChart.setHoleColor(ContextCompat.getColor(_mActivity,R.color.brown))
        //设置半透明圆环的半径, 0为透明
        pieChart.setTransparentCircleRadius(0f)
        pieChart.setHoleRadius(80f);  //半径

        // 不显示图例
        pieChart.getLegend().setEnabled(false)
        pieChart.getDescription().setEnabled(false)//取消描述

        // 和四周相隔一段距离,显示数据
        pieChart.setExtraOffsets(6f, 0f, 6f, 16f);
        pieChart.setClickable(false)//设置每项是否可点击

        // 设置pieChart图表是否可以手动旋转
        pieChart.setRotationEnabled(false);
        // 设置piecahrt图表点击Item高亮是否可用
        pieChart.setHighlightPerTapEnabled(false);
        //是否绘制PieChart内部中心文本
        pieChart.setDrawCenterText(true)
        pieChart.setCenterTextSize(SizeUtils.sp2px(24f).toFloat())//文字大小
        pieChart.setCenterTextColor(ContextCompat.getColor(_mActivity,R.color.white))//文字颜色
        pieChart.setUsePercentValues(true) //显示成百分比
        pieChart.setDrawEntryLabels(false) //是否显示饼图label
        pieChart.setRotationAngle(30f) //设置饼状图旋转的角度

    }

    fun setPieData(type:Int,entries:ArrayList<PieEntry>){

        var pieDataSet = PieDataSet(entries, "")
        pieDataSet.setColors(colorList)
        pieDataSet.setDrawValues(false)
        var pieData = PieData(pieDataSet)

        when(type){
            1->{
                chart1.setData(pieData)
                chart1.invalidate()
            }
            2->{
                chart2.setData(pieData)
                chart2.invalidate()
            }
            3->{
                chart3.setData(pieData)
                chart3.invalidate()
            }
            4->{
                chart4.setData(pieData)
                chart4.invalidate()
            }
        }

    }

    fun initline(lineChart: LineChart){

        lineChart.getDescription().setEnabled(false)//去掉描述
        lineChart.legend.isEnabled = false //取消图例
        // enable touch gestures
        lineChart.setTouchEnabled(false)//禁止触摸交互
        lineChart.setDrawGridBackground(false)
        lineChart.xAxis.isEnabled =false //去掉x轴
        lineChart.axisLeft.isEnabled = false//去掉左y轴
        lineChart.axisRight.isEnabled = false//去掉右y轴

    }

    fun setLineData(type:Int,mList:List<Int>){
        var entriesLine = arrayListOf<Entry>()
        for((index,value) in mList.withIndex()){
            entriesLine.add(Entry(index.toFloat(),value.toFloat()))
        }

        var lineDataSet = LineDataSet(entriesLine, "")
        lineDataSet.setColor(ContextCompat.getColor(_mActivity,R.color.linechart_1))
        lineDataSet.setLineWidth(1f)//线条颜色
        lineDataSet.setValueTextSize(9f)//字的大小
        lineDataSet.valueTextColor = ContextCompat.getColor(_mActivity,R.color.grayscale2)
        lineDataSet.setDrawFilled(true)//充满

        lineDataSet.setCircleColor(ContextCompat.getColor(_mActivity,R.color.linechart_1))//圆点颜色
        lineDataSet.setCircleRadius(2f)//圆点半径
        lineDataSet.setDrawCircleHole(false)//圆点实心

        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

        var lineData = LineData(lineDataSet)

        when(type){
            1->{
                chart_line1.setData(lineData)
                chart_line1.invalidate()
            }
            3->{
                chart_line3.setData(lineData)
                chart_line3.invalidate()
            }
            4->{
                chart_line4.setData(lineData)
                chart_line4.invalidate()
            }
            5->{
                chart_line5.setData(lineData)
                chart_line5.invalidate()
            }
            6->{
                chart_line6.setData(lineData)
                chart_line6.invalidate()
            }
        }

    }

    fun getMainData(){

        ApiClient.instance.kotlinService.getMainData()
            .compose(NetworkScheduler.compose())
            .bindToLifecycle(this)
            .subscribe(object : ApiObserver<BaseBean<MainBean>>() {
                override fun onSuccess(t: BaseBean<MainBean>) {
                    if(t.isSuccess()){
                        if(t.data!=null){
                            var entries1 = arrayListOf(PieEntry(t.data.totalTask.toFloat()),PieEntry(t.data.finishTask.toFloat()))
                            var entries2 = arrayListOf(PieEntry(t.data.totalTask.toFloat()),PieEntry(t.data.timeOutTask.toFloat()))
                            var entries3 = arrayListOf(PieEntry(t.data.totalTask.toFloat()),PieEntry(t.data.unStartTask.toFloat()))
                            var entries4 = arrayListOf(PieEntry(t.data.planTime.toFloat()),PieEntry(t.data.averTime.toFloat()))
                            chart1.centerText = t.data.finishTask
                            chart2.centerText = t.data.timeOutTask
                            chart3.centerText = t.data.unStartTask
                            chart4.centerText = t.data.averTime
                            setPieData(1,entries1)
                            setPieData(2,entries2)
                            setPieData(3,entries3)
                            setPieData(4,entries4)

                            equip_num.text = t.data.sblb
                            setLineData(1,t.data.zjrw)
                            setLineData(3,t.data.sbbx)
                            setLineData(4,t.data.byrw)
                            setLineData(5,t.data.wxrw)
                            setLineData(6,t.data.djrw)
                        }
                    }
                }

                override fun onError(msg: String?) {
                    toast(msg)
                }
            })
    }

    @OnClick(R.id.cv_task_zj,R.id.cv_equip_list,R.id.cv_equip_fix,R.id.cv_task_by,R.id.cv_task_ws,R.id.cv_task_dj)
    fun itemClick(view : View){
        when (view.id) {
            R.id.cv_task_zj -> ""
            R.id.cv_equip_list -> ""
            R.id.cv_equip_fix -> ""
            R.id.cv_task_by -> start(TaskBYFragment.newInstance())
            R.id.cv_task_ws -> ""
            R.id.cv_task_dj -> start(TaskDJFragment.newInstance())
        }
    }
}

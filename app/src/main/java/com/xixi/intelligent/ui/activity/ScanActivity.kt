package com.xixi.intelligent.ui.activity

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.zxing.Result
import com.google.zxing.client.result.ParsedResult
import com.mylhyl.zxing.scanner.OnScannerCompletionListener
import com.mylhyl.zxing.scanner.ScannerOptions
import com.xixi.intelligent.R
import com.xixi.intelligent.base.BaseSupportActivity
import com.xixi.intelligent.bean.event.MyMessageEvent
import com.xixi.intelligent.common.ARConstant
import com.xixi.intelligent.common.MData
import com.xixi.intelligent.utils.L
import kotlinx.android.synthetic.main.activity_scan.*
import kotlinx.android.synthetic.main.title_normal.*
import org.greenrobot.eventbus.EventBus


/**
 * 扫一扫
 */
@Route(path = ARConstant.AR_ScanActivity)
class ScanActivity : BaseSupportActivity(), OnScannerCompletionListener {

    @JvmField
    @Autowired()
    var scanType = 0

    override fun getContentRes(): Int {
        return R.layout.activity_scan
    }

    override fun initView(savedInstanceState: Bundle?) {
        page_title.text = "扫描"
                val options = ScannerOptions.Builder().setFrameSize(400,400).build()
        scanner.setScannerOptions(options)
        scanner.setOnScannerCompletionListener(this)
//        scanner.toggleLight(true)//指示灯开关
    }

    override fun onScannerCompletion(rawResult: Result?, parsedResult: ParsedResult?, barcode: Bitmap?) {
        vibrate()
        var result = rawResult?.text?:""
        val bundle = Bundle()
        when(scanType){
            0->{
//                bundle.putString("scanResult",result)
//                setFragmentResult(RESULT_CODE,bundle)
                EventBus.getDefault().post(MyMessageEvent(MData.Event_MSG_SCAN, result))
                finish()
            }
        }
    }

    //振动
    private fun vibrate() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator!!.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        }else{
            vibrator!!.vibrate(200)
        }
    }

    override fun onResume() {
        scanner.onResume()
        scanner.restartPreviewAfterDelay(0)
        super.onResume()

    }

    override fun onPause() {
        scanner.onPause()
        super.onPause()
    }

}

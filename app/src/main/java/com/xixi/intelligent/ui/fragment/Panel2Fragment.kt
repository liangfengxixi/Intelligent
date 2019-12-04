package com.xixi.intelligent.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.xixi.intelligent.R
import com.xixi.intelligent.base.BaseSupportFragment
import com.xixi.intelligent.utils.L
import kotlinx.android.synthetic.main.fragment_panel_2.*


/**
 * 设备看板
 */
class Panel2Fragment : BaseSupportFragment() {

    val url = "http://106.13.160.198"

    override fun getContentRes(): Int {
        return R.layout.fragment_panel_2
    }

    companion object {
        fun newInstance(): Panel2Fragment {
            return Panel2Fragment()
        }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        initWebView()
//        webview.loadUrl(url)
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun initWebView(){
        var ws = webview.settings
        ws.javaScriptEnabled = true
        // 设置可以支持缩放
        ws.setSupportZoom(true);
        // 设置默认缩放方式尺寸是far
        // ws.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        // 设置出现缩放工具
//        ws.builtInZoomControls = true
        ws.useWideViewPort = true
        ws.allowFileAccess = true
        ws.loadWithOverviewMode = true
//        ws.textSize = WebSettings.TextSize.NORMAL

        webview.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                L.e("UrlLoading $url")
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                L.e("onPageFinished $url")
                super.onPageFinished(view, url)
            }
        }

        webview.webChromeClient = WebChromeClient()
    }
}

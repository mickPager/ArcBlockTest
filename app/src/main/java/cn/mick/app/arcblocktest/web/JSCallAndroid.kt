package cn.mick.app.arcblocktest.web

import android.webkit.JavascriptInterface

/**
 * create by Mick when 2023/1/30.
 */
class JSCallAndroid {

    @JavascriptInterface
    fun callAndroid(message: String?) {
        println(message)
    }

}
package cn.mick.app.arcblocktest.activity

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.mick.app.arcblocktest.R
import cn.mick.app.arcblocktest.api.BASE_URL
import cn.mick.app.arcblocktest.web.JSCallAndroid

/**
 * create by Mick when 2023/1/28.
 */
class WebActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_web)

        webView = findViewById(R.id.webView)

        val url = intent.extras?.getString("url")

        if (url != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeButtonEnabled(true)

            webView.apply {
                addJavascriptInterface(JSCallAndroid(), "JsBinding")

                settings.apply {
                    javaScriptEnabled = true
                    javaScriptCanOpenWindowsAutomatically = true
                }

                setOnKeyListener { _, keyCode, event ->
                    if (event.action == KeyEvent.ACTION_DOWN) {
                        //按返回键操作并且能回退网页
                        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                            //后退
                            webView.goBack()
                            true
                        }
                    }
                    false
                }
            }


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WebView.setWebContentsDebuggingEnabled(true);
            }

            webView.webViewClient = object : WebViewClient() {

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    return request?.url?.toString()?.startsWith("http") ?: false
                }
            }

            webView.webChromeClient = object : WebChromeClient() {

                override fun onReceivedTitle(view: WebView?, title: String?) {
                    super.onReceivedTitle(view, title)
                    supportActionBar?.title = title
                }

                override fun onReceivedIcon(view: WebView?, icon: Bitmap?) {
                    super.onReceivedIcon(view, icon)
                    supportActionBar?.setIcon(BitmapDrawable(resources, icon))
                }
            }

            webView.loadUrl(BASE_URL + url)
        } else {
            finish()
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun evaluateJavascript(javascript: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // In KitKat+ you should use the evaluateJavascript method
            webView.evaluateJavascript(javascript) {
                Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
            }
        } else {
            webView.loadUrl("javascript:$javascript")
        }
    }
}
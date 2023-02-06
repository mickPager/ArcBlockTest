package cn.mick.app.arcblocktest.activity

import android.app.IntentService
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.mick.app.arcblocktest.ArticleAdapter
import cn.mick.app.arcblocktest.MainViewModel
import cn.mick.app.arcblocktest.R
import com.tencent.mmkv.MMKV
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.Collections
import java.util.concurrent.ConcurrentHashMap


/**
 * create by Mick when 2023/1/28.
 */
class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        MainViewModel()
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private val adapter: ArticleAdapter by lazy {
        ArticleAdapter()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: Any?) {
        // Do something
        startActivity(Intent())
        val rootDir = MMKV.initialize(this)

        MMKV.defaultMMKV().decodeBool("")
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        EventBus.getDefault().register(this)
        EventBus.getDefault().post(null)
        EventBus.getDefault().postSticky(null)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getArticles()
        }

        viewModel.articleLiveData.observe(this) {
            if (swipeRefreshLayout.isRefreshing) {
                adapter.setData(it)
                swipeRefreshLayout.isRefreshing = false
            } else {
                adapter.addData(it)
            }
        }

        swipeRefreshLayout.isRefreshing = true

        viewModel.getArticles()
    }
}
package cn.mick.app.arcblocktest.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.mick.app.arcblocktest.ArticleAdapter
import cn.mick.app.arcblocktest.MainViewModel
import cn.mick.app.arcblocktest.R

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
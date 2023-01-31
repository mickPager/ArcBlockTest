package cn.mick.app.arcblocktest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.mick.app.arcblocktest.api.ApiManager
import kotlinx.coroutines.launch

/**
 * create by Mick when 2023/1/28.
 */
class MainViewModel(private val model: MainModel = MainModel()) : ViewModel() {

    val articleLiveData = MutableLiveData<List<ArticleInfo>>()

    private val articles: MutableList<ArticleInfo> = mutableListOf()

    private var page: Int = 1
    private val pageNum: Int = 10

    fun getArticles(isRefresh: Boolean = true) {
        if (isRefresh) {
            viewModelScope.launch {
                articleLiveData.value = articles.let {
                    page = 1
                    it.clear()
                    it.addAll(model.getArticles())
                    if (it.size > pageNum) {
                        it.subList(0, 10)
                    } else {
                        it
                    }
                }
            }
        } else {
            page++
            if (articles.size > pageNum * (page - 1)) {
                articleLiveData.value = articles.subList((page - 1) * pageNum + 1, page * pageNum)
            }
        }
    }
}

class MainModel {

    suspend fun getArticles(): List<ArticleInfo> {
        return ApiManager.apiService.getRepoInfo()
    }

}
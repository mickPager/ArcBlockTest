package cn.mick.app.arcblocktest.api

import cn.mick.app.arcblocktest.ArticleInfo
import retrofit2.http.GET

/**
 * create by Mick when 2023/1/28.
 */
interface ApiService {

    @GET("blog/posts.json")
    suspend fun getRepoInfo(): List<ArticleInfo>
}
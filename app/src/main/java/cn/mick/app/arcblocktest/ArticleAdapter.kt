package cn.mick.app.arcblocktest

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import cn.mick.app.arcblocktest.activity.WebActivity
import cn.mick.app.arcblocktest.api.BASE_URL
import com.bumptech.glide.Glide

/**
 * create by mick when 2023/1/28.
 */
class ArticleAdapter : RecyclerView.Adapter<ArticleViewHolder>() {

    private var articleInfos: MutableList<ArticleInfo> = mutableListOf()

    fun setData(data: List<ArticleInfo>?) {
        data?.let {
            articleInfos.clear()
            articleInfos.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun addData(data: List<ArticleInfo>?) {
        data?.let {
            articleInfos.addAll(it)
            notifyItemRangeInserted(articleInfos.size, it.size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            parent
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val articleInfo = articleInfos[position]
        holder.bindData(articleInfo)
        holder.itemView.setOnClickListener {
            it.context.startActivity(Intent(it.context, WebActivity::class.java).apply {
                putExtra("url", articleInfo.frontmatter?.path)
            })
        }
    }

    override fun getItemCount(): Int {
        return articleInfos.size
    }


}

class ArticleViewHolder(parent: ViewGroup) :
    ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_artivle, parent, false)) {

    private val imageView = itemView.findViewById<ImageView>(R.id.image)
    private val title = itemView.findViewById<TextView>(R.id.title)
    private val date = itemView.findViewById<TextView>(R.id.date)


    fun bindData(info: ArticleInfo) {
        val src = info.frontmatter?.banner?.childImageSharp?.fixed?.src ?: ""

        Glide.with(itemView).load(BASE_URL + src).into(imageView)

        title.text = info.frontmatter?.title
        date.text = info.frontmatter?.date
    }


}
package cn.mick.app.arcblocktest

/**
 * create by Mick when 2023/1/28.
 */
data class ArticleInfo(
    val frontmatter: ArticleInfoEntry?
)

data class ArticleInfoEntry(
    val title: String?,
    val date: String?,
    val path: String?,
    val draft: Any?,
    val language: String?,
    val categories: List<String>?,
    val tags: List<String>?,
    val banner: Banner?,
)

data class Banner(val childImageSharp: ChildImageSharp?)

data class ChildImageSharp(val fixed: Fixed?)

data class Fixed(
    val src: String?
)
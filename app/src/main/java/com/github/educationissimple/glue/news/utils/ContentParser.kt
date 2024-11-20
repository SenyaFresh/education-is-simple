package com.github.educationissimple.glue.news.utils

/**
 * Extension function to parse the content of a news article. Removes [+n chars].
 */
fun parseContent(content: String): String {
    val regex = Regex("""\[\+\d+ chars]""")
    val matches = regex.findAll(content).toList()
    if (matches.isEmpty()) return content

    val lastMatch = matches.last()
    return content.removeRange(lastMatch.range)
}
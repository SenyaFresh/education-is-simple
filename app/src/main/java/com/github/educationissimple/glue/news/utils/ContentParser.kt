package com.github.educationissimple.glue.news.utils

fun parseContent(content: String): String {
    val regex = Regex("""\[\+\d+ chars]""")
    val matches = regex.findAll(content).toList()
    if (matches.isEmpty()) return content

    val lastMatch = matches.last()
    return content.removeRange(lastMatch.range)
}
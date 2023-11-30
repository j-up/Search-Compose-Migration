package com.kakao.search.domain.model.remote

import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

data class KakaoImage(val thumbnail: String, val dateTime: String, val isBookmark: Boolean = false): Comparable<KakaoImage> {
    fun dateToString(): String {
        val inputFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val dateTime = OffsetDateTime.parse(dateTime, inputFormatter)

        val outputFormatter = DateTimeFormatter.ofPattern(Const.DATE_PATTERN, Locale.KOREA)

        return dateTime.format(outputFormatter)
    }

    override fun compareTo(other: KakaoImage): Int {
        val dateTime1 = LocalDateTime.parse(this.dateTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        val dateTime2 = LocalDateTime.parse(other.dateTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME)

        return dateTime2.compareTo(dateTime1)
    }
}

object Const {
    const val DATE_PATTERN = "yyyy-MM-dd HH시 mm분"
}
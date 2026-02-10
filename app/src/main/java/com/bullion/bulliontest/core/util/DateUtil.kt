package com.bullion.bulliontest.core.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateUtil {
    fun formatDateDayMonthYear(millis: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(Date(millis))
    }

    fun formatDateMonthDayYear(dateString: String): String {
        if (dateString.isBlank()) return ""

        return try {
            // Try parsing with different input formats
            val inputFormats = listOf(
                SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH),
                SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH),
                SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH),
                SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            )

            val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH)

            for (inputFormat in inputFormats) {
                try {
                    val date = inputFormat.parse(dateString)
                    return date?.let { outputFormat.format(it) } ?: dateString
                } catch (e: Exception) {
                    // Try next format
                }
            }

            // If all parsing failed, return original string
            dateString
        } catch (e: Exception) {
            dateString
        }
    }

    fun formatDateDayMonthYear(iso: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }

            val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }

            val date: Date = inputFormat.parse(iso) ?: return ""
            outputFormat.format(date)
        } catch (e: Exception) {
            ""
        }
    }
}
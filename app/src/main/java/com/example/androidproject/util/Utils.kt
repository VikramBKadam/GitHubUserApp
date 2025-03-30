package com.example.androidproject.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class Utils {
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun formatDateTime(isoString: String): String {
            return try {
                val parsedDate = ZonedDateTime.parse(isoString)
                val formatter =
                    DateTimeFormatter.ofPattern("MMMM dd, yyyy | hh:mm a", Locale.ENGLISH)
                parsedDate.format(formatter)
            } catch (e: Exception) {
                "N/A"  // Handle invalid or null date strings
            }
        }
    }
}
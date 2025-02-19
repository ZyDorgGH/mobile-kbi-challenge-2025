package id.kitabantu.data.remote.converter

import android.os.Build
import androidx.annotation.RequiresApi
import id.kitabantu.model.JobType
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

@OptIn(FormatStringsInDatetimeFormats::class)
@RequiresApi(Build.VERSION_CODES.O)
fun formatToWIB(localDateTime: LocalDateTime): String {
    val jakartaZone = TimeZone.of("Asia/Jakarta")
    val instant = localDateTime.toInstant(TimeZone.UTC)
    val zonedDateTime = instant.toLocalDateTime(jakartaZone)
    val formatter = LocalDateTime.Format { byUnicodePattern("yyyy-MM-dd HH:mm") }
    return zonedDateTime.format(formatter)
}

fun JobType.toDisplayString(): String {
    return this.name.lowercase()
        .split("_")
        .joinToString(" ") { it.replaceFirstChar { c -> c.uppercase() } }
}


fun String.toJobType(): JobType {
    val formattedString = this.split(" ")
        .joinToString("_") { it.uppercase() }

    return JobType.entries.firstOrNull { it.name == formattedString } ?: JobType.FULL_TIME
}
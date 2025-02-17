package id.kitabantu.model

import kotlinx.datetime.LocalDateTime
import java.util.Currency

data class Job(
    val title: String,
    val published: LocalDateTime,
    val company: String,
    val companyLogoUrl: String,
    val location: String,
    val type: JobType,
    val excerpt: String, // Job short description
    val description: List<String>,
    val salary: Long,
    val salaryCurrency: Currency,
)

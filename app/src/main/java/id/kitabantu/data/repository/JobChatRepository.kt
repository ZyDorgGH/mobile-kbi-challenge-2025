package id.kitabantu.data.repository

import android.os.Message
import id.kitabantu.data.Result
import id.kitabantu.model.Job
import id.kitabantu.model.JobChat
import kotlinx.coroutines.flow.Flow

interface JobChatRepository {

    fun getChats(): Flow<Result<List<JobChat>>>

    fun addMessage(job: Job, message: String)
}
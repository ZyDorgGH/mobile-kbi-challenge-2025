package id.kitabantu.data.repository

import android.util.Log
import id.kitabantu.data.Result
import id.kitabantu.data.asResult
import id.kitabantu.model.Job
import id.kitabantu.model.JobChat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class DefaultJobChatRepository : JobChatRepository {

    private val jobChats: MutableStateFlow<List<JobChat>> = MutableStateFlow(emptyList())

    override fun getChats(): Flow<Result<List<JobChat>>> =
        jobChats.asResult()

    override fun addMessage(job: Job, message: String) {
        jobChats.update { jobChats ->
            val mutableChats = jobChats.toMutableList()

            val existingChatIndex = mutableChats.indexOfFirst { it.job == job }
            Log.d("existingChatIndex", "addMessage: $existingChatIndex")
            if (existingChatIndex > -1) {
                val updatedChat = mutableChats[existingChatIndex].copy(
                    messages = (mutableChats[existingChatIndex].messages + message)
                )
                mutableChats[existingChatIndex] = updatedChat
            } else {
                mutableChats.add(JobChat(job, listOf(message)))
            }

            mutableChats
        }
    }
}
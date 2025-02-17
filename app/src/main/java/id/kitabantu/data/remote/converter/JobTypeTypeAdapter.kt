package id.kitabantu.data.remote.converter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import id.kitabantu.model.JobType
import java.lang.reflect.Type

class JobTypeTypeAdapter : JsonDeserializer<JobType> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): JobType? = json?.toString()?.let { value ->
        JobType.valueOf(value.replace("\"", "").uppercase())
    }
}
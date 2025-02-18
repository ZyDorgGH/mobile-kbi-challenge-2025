package id.kitabantu.data.remote.converter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import kotlinx.datetime.LocalDateTime
import java.lang.reflect.Type

class DateTimeTypeAdapter : JsonDeserializer<LocalDateTime> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDateTime? = json?.toString()?.let { value ->
        LocalDateTime.parse(value.replace("\"", ""))
    }
}
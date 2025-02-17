package id.kitabantu.data.remote.converter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.Currency

class CurrencyTypeAdapter : JsonDeserializer<Currency> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Currency {
        val currency = json?.toString()?.let { value ->
            Currency.getInstance(value.replace("\"", ""))
        }

        return currency ?: Currency.getInstance("USD")
    }
}
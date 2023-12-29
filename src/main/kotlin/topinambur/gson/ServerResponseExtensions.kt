package topinambur.gson

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import topinambur.ServerResponse

fun ServerResponse.jsonArray(): JsonArray? {
    return jsonElement()?.asJsonArray
}

fun ServerResponse.jsonObject(): JsonObject? {
    return jsonElement()?.asJsonObject
}

fun ServerResponse.jsonElement(): JsonElement? {
    return JsonParser.parseString(body)
}

inline fun <reified T : Any> ServerResponse.json(gsonBuilder: GsonBuilder = GsonBuilder()): T {
    try {
        return gsonBuilder.create().fromJson(body, object : TypeToken<T>() {}.type)
    } catch (e: NullPointerException) {
        throw IllegalStateException("Cannot convert to ${T::class.java} class. Json: '$body'")
    }
}

inline fun <reified T : Any> ServerResponse.json(vararg deserializers: Deserializer<*>): T {
    return json(GsonBuilder().apply { deserializers.forEach { registerTypeAdapter(it.type.java, it) } })
}
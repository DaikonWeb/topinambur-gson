package topinambur.gson

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import topinambur.ServerResponse

inline fun <reified T : Any> ServerResponse.json(gsonBuilder: GsonBuilder = GsonBuilder()): T {
    return gsonBuilder.create().fromJson(body, object: TypeToken<T>() {}.type)
}

inline fun <reified T : Any> ServerResponse.json(vararg deserializers: Deserializer<*>): T {
    return json(GsonBuilder().apply { deserializers.forEach { registerTypeAdapter(it.type.java, it) } })
}
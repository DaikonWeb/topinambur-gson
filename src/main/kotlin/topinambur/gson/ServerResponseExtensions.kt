package topinambur.gson

import com.google.gson.GsonBuilder
import topinambur.ServerResponse

inline fun <reified T : Any> ServerResponse.json(gsonBuilder: GsonBuilder = GsonBuilder()): T {
    return gsonBuilder.create().fromJson(body, T::class.java)
}
package topinambur.gson

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import topinambur.ServerResponse
import java.time.LocalDateTime
import java.time.LocalDateTime.parse

data class TimeResponse(val time: LocalDateTime)
data class TestResponse(val code: String = "")

class ServerResponseJsonTest {

    @Test
    fun `Empty object`() {
        val response = ServerResponse(200, "{}".toByteArray(), emptyMap())

        assertThat(response.json<TestResponse>()).isEqualTo(TestResponse(""))
    }

    @Test
    fun `valid json string`() {
        val response = ServerResponse(200, """{"code":"prova"}""".toByteArray(), emptyMap())

        assertThat(response.json<TestResponse>()).isEqualTo(TestResponse("prova"))
    }

    @Test
    fun `invalid json string`() {
        val response = ServerResponse(200, "".toByteArray(), emptyMap())

        val errorMessage = assertThrows<IllegalStateException> {
            response.json<TestResponse>()
        }.message

        assertThat(errorMessage).isEqualTo("Cannot convert to class topinambur.gson.TestResponse class. Json: ''")
    }

    @Test
    fun `valid list of json strings`() {
        val response = ServerResponse(200, """[{"code":"first"},{"code":"second"}]""".toByteArray(), emptyMap())

        assertThat(response.json<List<TestResponse>>()).isEqualTo(listOf(TestResponse("first"), TestResponse("second")))
    }

    @Test
    fun `valid list of json datetime`() {
        val response = ServerResponse(200, """[{"time":"2020-01-01T12:00:00"},{"time":"2020-03-03T23:59:59"}]""".toByteArray(), emptyMap())

        val builder = GsonBuilder().registerTypeAdapter(LocalDateTime::class.java, JsonDeserializer { json: JsonElement, _, _ -> parse(json.asString) })

        assertThat(response.json<List<TimeResponse>>(builder)).isEqualTo(listOf(
                TimeResponse(parse("2020-01-01T12:00:00")),
                TimeResponse(parse("2020-03-03T23:59:59"))
        ))
    }

    @Test
    fun `valid list of json datetime using alternative json method`() {
        val response = ServerResponse(200, """[{"time":"2020-01-01T12:00:00"},{"time":"2020-03-03T23:59:59"}]""".toByteArray(), emptyMap())

        val deserializer = Deserializer(LocalDateTime::class) { json: JsonElement, _, _ -> parse(json.asString) }

        assertThat(response.json<List<TimeResponse>>(deserializer)).isEqualTo(listOf(
                TimeResponse(parse("2020-01-01T12:00:00")),
                TimeResponse(parse("2020-03-03T23:59:59"))
        ))
    }

    @Test
    fun `parse to json array`() {
        val response = ServerResponse(200, """[{"code":"first"},{"code":"second"}]""".toByteArray(), emptyMap())

        val json = response.jsonArray()!!

        assertThat(json.get(0).asJsonObject.get("code").asString).isEqualTo("first")
        assertThat(json.get(1).asJsonObject.get("code").asString).isEqualTo("second")
    }

    @Test
    fun `parse to json object`() {
        val response = ServerResponse(200, """{"code":"first"}""".toByteArray(), emptyMap())

        val json = response.jsonObject()!!

        assertThat(json.get("code").asString).isEqualTo("first")
    }

    @Test
    fun `parse to json element`() {
        val response = ServerResponse(200, "\"first\"".toByteArray(), emptyMap())

        val json = response.jsonElement()!!

        assertThat(json.asString).isEqualTo("first")
    }
}

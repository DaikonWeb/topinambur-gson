package topinambur.gson

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import topinambur.ServerResponse

data class TestResponse(val code: String = "")

class ServerResponseJsonTest {

    @Test
    fun `Empty object`() {
        val response = ServerResponse(200, "{}", emptyMap())

        assertThat(response.json<TestResponse>()).isEqualTo(TestResponse(""))
    }

    @Test
    fun `valid json string`() {
        val response = ServerResponse(200, """{"code":"prova"}""", emptyMap())

        assertThat(response.json<TestResponse>()).isEqualTo(TestResponse("prova"))
    }

    @Test
    fun `invalid json string`() {
        val response = ServerResponse(200, """""", emptyMap())

        assertThrows<IllegalStateException> {
            response.json<TestResponse>()
        }
    }
}

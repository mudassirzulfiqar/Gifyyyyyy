package com.moodi.data.mapper

import com.google.gson.Gson
import com.moodi.data.MockUtil
import com.moodi.data.remote.dto.RandomGiphyDTO
import com.moodi.task.mock.API_DATA_RANDOM
import com.moodi.task.mock.fromJson
import org.junit.Test

/**
 * This class is used to test the mapping of [RandomGiphyModelResponse] to [GiphyAppModel].
 */
class RandomModelMapperTest {

    private val mockUtil = MockUtil()

    @Test
    fun `convert RandomGiphyModelResponse as  GiphyAppModel`() {

        val randomGiphyModelResponse =
            mockUtil.convertJsonToString(MockUtil.RANDOM_MODEL).fromJson<RandomGiphyDTO>()

        val expected = API_DATA_RANDOM
        val actual = randomGiphyModelResponse.asAppModel()
        assert(expected == actual)
    }

}
inline fun <reified T : Any> String.fromJson(): T {
    return Gson().fromJson(this, T::class.java)
}
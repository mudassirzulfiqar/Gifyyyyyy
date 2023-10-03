package com.moodi.task.data.mapper

import com.moodi.task.data.remote.model.RandomGiphyModelResponse
import com.moodi.task.mock.API_DATA_RANDOM
import com.moodi.task.mock.MockUtil
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
            mockUtil.convertJsonToString(MockUtil.RANDOM_MODEL).fromJson<RandomGiphyModelResponse>()

        val expected = API_DATA_RANDOM
        val actual = randomGiphyModelResponse.asAppModel()
        assert(expected == actual)
    }

}
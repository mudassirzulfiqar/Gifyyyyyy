package com.moodi.data.mapper

import com.moodi.data.remote.dto.SearchGiphyDTO
import com.moodi.domain.model.GiphyAppModel
import com.moodi.data.mock.API_DATA_SEARCH
import com.moodi.data.mock.MockUtil
import com.moodi.data.mock.fromJson
import org.junit.Test

/**
 * This class is used to test the mapping of [SearchGiphyModelResponse] to [GiphyAppModel].
 */
class SearchModelMapperTest {


    private val mockUtil = MockUtil()

    /**
     * Using Duplicate Model as a sample because business meaning of data is not the case here, only correctness of the data is not important.
     */
    @Test
    fun `convert SearchGiphyModelResponse as List GiphyAppModel`() {

        val searchGiphyModelResponse =
            mockUtil.convertJsonToString(MockUtil.SEARCH_MODEL_LIST)
                .fromJson<SearchGiphyDTO>()

        // make list of GiphyAppModel
        val expected = ArrayList<GiphyAppModel>()
        expected.add(
            API_DATA_SEARCH,
        )
        expected.add(
            API_DATA_SEARCH
        )
        val actual = searchGiphyModelResponse.asAppModel()
        assert(expected == actual)
    }
}
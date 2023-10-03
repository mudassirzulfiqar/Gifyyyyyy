package com.moodi.task.mock

import com.moodi.task.data.Resource
import com.moodi.task.data.local.GiphyAppModel
import com.moodi.task.data.repository.GiphyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * This class is used to mock the repository for testing purpose.
 */
class FakeRepository(
    private val testSearchGif: Resource<List<GiphyAppModel>>?,
    private val testRandomGif: Resource<GiphyAppModel>?
) : GiphyRepository {

    override fun getRandomGif(): Flow<Resource<GiphyAppModel>> {
        return flow {
            emit(Resource.Loading())
            emit(testRandomGif!!)
        }
    }

    override fun searchGif(query: String, limit: Int): Flow<Resource<List<GiphyAppModel>>> {
        return flow {
            emit(Resource.Loading())
            emit(testSearchGif!!)
        }
    }


}

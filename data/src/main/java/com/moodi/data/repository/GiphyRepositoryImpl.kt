package com.moodi.data.repository

import com.moodi.data.mapper.asAppModel
import com.moodi.data.source.RemoteDataSource
import com.moodi.domain.repository.GiphyRepository
import com.moodi.domain.util.Resource
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

/**
 * This class is used to implement the methods defined in GiphyRepository interface
 * The class injects [RemoteDataSource] and [CoroutineContext], reason to add [CoroutineContext] is to
 * test the code with [TestCoroutineDispatcher] as flow is used to get the data from remote source.
 * The class is annotated with @Inject to be used by Dagger Hilt for dependency injection.
 * @param source RemoteDataSource
 * @param ioDispatcher CoroutineContext
 *
 * when getRandomGif() is called, it emit loading state and then call the source to get the data.
 * when searchGif() is called, it emit loading state and then call the source to get the data.
 *
 * This repository is also responsible to map the data from one layer to another.
 */
class GiphyRepositoryImpl(
    private val source: RemoteDataSource,
    private val ioDispatcher: CoroutineContext
) : GiphyRepository {
    override fun getRandomGif() = flow {
        emit(Resource.Loading())
        val result = source.getRandomGif()
        if (result is Resource.Success) {
            val data = result.data?.asAppModel() ?: throw IllegalStateException("Data is null")
            emit(Resource.Success(data))
        } else {
            emit(Resource.Failure(result.errorCode!!, result.errorMessage!!))
        }
    }.flowOn(ioDispatcher)

    override fun searchGif(query: String, limit: Int) = flow {
        emit(Resource.Loading())
        val result = source.searchGif(query, limit)
        if (result is Resource.Success) {
            val data = result.data!!.asAppModel()
            emit(Resource.Success(data))
        } else {
            emit(Resource.Failure(result.errorCode!!, result.errorMessage!!))
        }
    }.flowOn(ioDispatcher)
}

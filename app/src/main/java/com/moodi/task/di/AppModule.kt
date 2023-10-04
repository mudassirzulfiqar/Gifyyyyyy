package com.moodi.task.di

import com.moodi.task.data.remote.api.WebApi
import com.moodi.task.data.remote.config.WebClient
import com.moodi.task.data.repository.GiphyRepository
import com.moodi.task.data.repository.GiphyRepositoryImpl
import com.moodi.task.data.source.RemoteDataSource
import com.moodi.task.data.source.RemoteDataSourceImpl
import com.moodi.task.ui.dispatcher.PeriodicDispatcher
import com.moodi.task.usecase.SearchGifUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideNetworkClient(): Retrofit {
        return WebClient().getRetrofit()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): WebApi {
        return retrofit.create(WebApi::class.java)
    }

    @Provides
    fun provideRemoteDataSource(webApi: WebApi): RemoteDataSource {
        return RemoteDataSourceImpl(webApi)
    }

    @Provides
    fun provideDispatcherProvider(): CoroutineDispatcher {
        return IO
    }

    @Provides
    fun provideGiphyRepository(
        remoteDataSource: RemoteDataSource,
        dispatcher: CoroutineDispatcher
    ): GiphyRepository {
        return GiphyRepositoryImpl(remoteDataSource, dispatcher)
    }

    @Provides
    fun provideSearchGifUseCase(giphyRepository: GiphyRepository): SearchGifUseCase {
        return SearchGifUseCase(giphyRepository)
    }

    @Provides
    fun providePeriodicDispatcher(): PeriodicDispatcher {
        return PeriodicDispatcher(IO)
    }

}
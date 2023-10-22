package com.moodi.task.di

import com.moodi.data.remote.api.WebApi
import com.moodi.data.remote.config.WebClient
import com.moodi.data.repository.GiphyRepositoryImpl
import com.moodi.data.source.RemoteDataSource
import com.moodi.data.source.RemoteDataSourceImpl
import com.moodi.domain.repository.GiphyRepository
import com.moodi.domain.usecase.RandomGiphyUseCase
import com.moodi.domain.usecase.SearchGiphyUseCase
import com.moodi.task.dispatcher.PeriodicDispatcher
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
    fun provideSearchGifUseCase(giphyRepository: GiphyRepository): SearchGiphyUseCase {
        return SearchGiphyUseCase(giphyRepository)
    }

    @Provides
    fun provideRandomGifUseCase(giphyRepository: GiphyRepository): RandomGiphyUseCase {
        return RandomGiphyUseCase(giphyRepository)
    }

    @Provides
    fun providePeriodicDispatcher(): PeriodicDispatcher {
        return PeriodicDispatcher(IO)
    }


}
package com.grl.clientapptfg.core.di

import com.grl.clientapptfg.data.clients.OrderClient
import com.grl.clientapptfg.data.clients.ProductClient
import com.grl.clientapptfg.data.clients.UserClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://apitfg-production.up.railway.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideUserClient(retrofit: Retrofit): UserClient {
        return retrofit.create(UserClient::class.java)
    }

    @Singleton
    @Provides
    fun provideProductClient(retrofit: Retrofit): ProductClient {
        return retrofit.create(ProductClient::class.java)
    }

    @Singleton
    @Provides
    fun provideOrderClient(retrofit: Retrofit): OrderClient {
        return retrofit.create(OrderClient::class.java)
    }
}
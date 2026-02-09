package com.example.thesimpsons.data.network.di

import com.example.thesimpsons.data.network.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideRetrofit():Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://thesimpsonsapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiClient(retrofit: Retrofit):ApiClient {
        return retrofit.create(ApiClient::class.java)
    }


}
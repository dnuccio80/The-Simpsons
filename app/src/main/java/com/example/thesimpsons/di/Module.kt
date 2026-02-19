package com.example.thesimpsons.di

import android.app.Application
import androidx.room.Room
import com.example.thesimpsons.data.local.db.AppDataBase
import com.example.thesimpsons.data.local.dao.CharacterDao
import com.example.thesimpsons.data.local.dao.EpisodeDao
import com.example.thesimpsons.data.local.dao.LocationDao
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

    @Provides
    @Singleton
    fun provideAppDataBase(app: Application): AppDataBase {
        return Room.databaseBuilder(
            app,
            AppDataBase::class.java,
            "app_db"
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    @Singleton
    fun provideCharacterDao(appDatabase: AppDataBase): CharacterDao {
        return appDatabase.characterDao
    }

    @Provides
    @Singleton
    fun provideEpisodeDao(appDatabase: AppDataBase): EpisodeDao {
        return appDatabase.episodeDao
    }

    @Provides
    @Singleton
    fun provideLocationDao(appDatabase: AppDataBase): LocationDao {
        return appDatabase.locationDao
    }


}
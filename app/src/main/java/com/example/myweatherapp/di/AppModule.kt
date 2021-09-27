package com.example.myweatherapp.di

import android.content.Context
import com.example.myweatherapp.BuildConfig
import com.example.myweatherapp.features.permission.data.repository.OperationSystemRepositoryImpl
import com.example.myweatherapp.features.permission.domain.repository.OperationSystemRepository
import com.example.myweatherapp.features.weather.data.repository.WeatherRepositoryImpl
import com.example.myweatherapp.features.weather.domain.repository.IWeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRetrofitInstance(): Retrofit {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }


    @Singleton
    @Provides
    fun injectWeatherRepository(retrofit: Retrofit): IWeatherRepository {
        return WeatherRepositoryImpl(retrofit)
    }

    @Singleton
    @Provides
    fun injectOperationSystem(@ApplicationContext context: Context): OperationSystemRepository {
        return OperationSystemRepositoryImpl(context)
    }
}
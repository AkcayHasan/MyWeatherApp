package com.example.myweatherapp.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myweatherapp.BuildConfig
import com.example.myweatherapp.R
import com.example.myweatherapp.features.weather.data.api.WeatherApi
import com.example.myweatherapp.features.weather.data.repository.WeatherRepositoryImpl
import com.example.myweatherapp.features.weather.domain.repository.IWeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRetrofitInstance(): WeatherApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
            .create(WeatherApi::class.java)
    }

    @Singleton
    @Provides
    fun injectGlideInstance(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
        )

    @Singleton
    @Provides
    fun injectWeatherRepository(retrofit: WeatherApi): IWeatherRepository {
        return WeatherRepositoryImpl(retrofit)
    }
}
package com.pershin.pawpedia.core.network

import android.content.Context
import com.pershin.pawpedia.BuildConfig
import com.pershin.pawpedia.core.PawBreedRepository
import com.pershin.pawpedia.core.PawBreedRepositoryImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
class CoreNetworkModule {

    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient =
        OkHttpClient.Builder()
            .cache(Cache(context.cacheDir, CACHE_SIZE))
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .apply {
                if (BuildConfig.DEBUG) {
                    addNetworkInterceptor(
                        HttpLoggingInterceptor().setLevel(
                            HttpLoggingInterceptor.Level.BODY,
                        ),
                    )
                }
            }
            .build()

    @Provides
    @Singleton
    fun providePawpediaApi(retrofit: Retrofit): PawpediaApi = retrofit.create(PawpediaApi::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): Retrofit = retrofitBuilder
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()


    @Provides
    fun provideRetrofitBuilder(moshi: Moshi): Retrofit.Builder = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun providePawBreedRepository(api: PawpediaApi): PawBreedRepository = PawBreedRepositoryImpl(api)

    companion object {
        private const val BASE_URL: String = "https://dog.ceo/"

        private const val CACHE_SIZE: Long = 10 * 1024 * 1024
    }
}

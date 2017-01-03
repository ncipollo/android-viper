package viper.sample.dagger

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.schedulers.Schedulers
import viper.sample.model.api.GitHubAPI
import javax.inject.Singleton

/**
 * Module which provides API dependencies.
 * Created by Nick Cipollo on 1/3/17.
 */
@Module
class APIModule {
    @Provides
    @Singleton
    fun providesAPI() : GitHubAPI {
        val rxAdapter = RxJavaCallAdapterFactory
                .createWithScheduler(Schedulers.io())
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(rxAdapter)
                .build()

        return retrofit.create(GitHubAPI::class.java)
    }
}
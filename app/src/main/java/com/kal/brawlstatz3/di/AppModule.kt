package com.kal.brawlstatz3.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kal.brawlstatz3.data.repository.BrawlAPI
import com.kal.brawlstatz3.data.repository.BrawlerRepository
import com.kal.brawlstatz3.data.repository.BrawlerRepositoryImpl
import com.kal.brawlstatz3.data.repository.MyBrawlAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    @Named("Brawlers")
    fun provideBrawlerReference(): CollectionReference {
        return Firebase.firestore.collection("Brawlers")
    }

    @Provides
    @Singleton
    @Named("Others")
    fun provideOthersReference(): CollectionReference {
        return Firebase.firestore.collection("Others")
    }

    @Provides
    @Singleton
    fun provideBrawlerRepository(@Named("Brawlers") brawlersRef: CollectionReference , @Named("Others") othersRef: CollectionReference): BrawlerRepository {
        return BrawlerRepositoryImpl(brawlersRef,othersRef)
    }


    @Provides
    @Singleton
    @Named("MyBrawlAPI")
    fun provideMyBrawlAPIBaseURL():String = "https://y2bvckx7ek.execute-api.ap-south-1.amazonaws.com/dev/"

    @Provides
    @Singleton
    @Named("MyBrawlAPI")
    fun provideMyBrawlAPIRetrofit(@Named("MyBrawlAPI") baseURL:String):Retrofit{
        return Retrofit.Builder().baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMyBrawlAPI(@Named("MyBrawlAPI") retrofit: Retrofit): MyBrawlAPI {
        return retrofit.create(MyBrawlAPI::class.java)
    }

    @Provides
    @Singleton
    @Named("BrawlAPI")
    fun provideBrawlAPIBaseURL():String = "https://api.brawlify.com/v1/"

    @Provides
    @Singleton
    @Named("BrawlAPI")
    fun provideBrawlAPIRetrofit(@Named("BrawlAPI") baseURL:String):Retrofit{
        return Retrofit.Builder().baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideBrawlAPI(@Named("BrawlAPI") retrofit: Retrofit): BrawlAPI {
        return retrofit.create(BrawlAPI::class.java)
    }
}
package com.kal.brawlstatz3.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kal.brawlstatz3.data.repository.BrawlApiRepository
import com.kal.brawlstatz3.data.repository.BrawlApiRepositoryImpl
import com.kal.brawlstatz3.data.repository.BrawlerRepository
import com.kal.brawlstatz3.data.repository.BrawlerRepositoryImpl
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
    fun provideRetrofitClient(): BrawlApiRepository {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.brawlify.com/v1/")
            .build()
        val service = retrofit.create(BrawlApiRepository::class.java)
        return service
    }


}
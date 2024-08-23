package com.kal.brawlstatz3.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kal.brawlstatz3.data.repository.BrawlerRepository
import com.kal.brawlstatz3.data.repository.BrawlerRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideBrawlerRepository(ref: CollectionReference): BrawlerRepository {
        return BrawlerRepositoryImpl(ref)
    }
    @Provides
    @Singleton
    fun provideBrawlerReference(): CollectionReference {
        return Firebase.firestore.collection("Brawlers")
    }

//    @Provides
//    @Singleton
//    fun provideRetrofitApi(){
//
//    }

}
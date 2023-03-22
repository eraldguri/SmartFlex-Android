package com.erald_guri.smartflex_android.di

import android.content.Context
import androidx.room.Room
import com.erald_guri.smartflex_android.data.database.FlexDatabase
import com.erald_guri.smartflex_android.data.database.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideFlexDatabase(@ApplicationContext context: Context): FlexDatabase {
        return Room.databaseBuilder(context, FlexDatabase::class.java, "smart-flex.db").allowMainThreadQueries()
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(database: FlexDatabase): TaskDao = database.taskDao()

    @Provides
    @Singleton
    fun provideCategoryDao(database: FlexDatabase): CategoryDao = database.categoryDao()

    @Provides
    @Singleton
    fun provideNoteDao(database: FlexDatabase): NoteDao = database.noteDao()

    @Provides
    @Singleton
    fun providesContactDao(database: FlexDatabase): ContactDao = database.contactDao()

    @Provides
    @Singleton
    fun providesAccountDao(database: FlexDatabase): AccountDao = database.accountDao()

    @Provides
    @Singleton
    fun providesSocialLinkDao(database: FlexDatabase): SocialLinkDao = database.socialLinkDao()

}
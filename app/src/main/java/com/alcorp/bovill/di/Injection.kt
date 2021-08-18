package com.alcorp.bovill.di

import android.content.Context
import com.alcorp.bovill.data.source.AppRepository
import com.alcorp.bovill.data.source.local.LocalDataSource
import com.alcorp.bovill.data.source.local.room.AppDatabase
import com.alcorp.bovill.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context) : AppRepository {

        val database = AppDatabase.getInstance(context)
        val localDataSource = LocalDataSource.getInstance(database.appDao())
        val appExecutors = AppExecutors()

        return AppRepository.getInstance(localDataSource, appExecutors)
    }
}
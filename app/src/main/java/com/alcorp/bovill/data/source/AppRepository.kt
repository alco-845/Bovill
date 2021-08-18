package com.alcorp.bovill.data.source

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.alcorp.bovill.data.source.local.LocalDataSource
import com.alcorp.bovill.data.source.local.entity.BookingEntity
import com.alcorp.bovill.utils.AppExecutors

class AppRepository private constructor(
        private val localDataSource: LocalDataSource,
        private val appExecutors: AppExecutors
) : AppDataSource {

    companion object {
        @Volatile
        private var instance: AppRepository? = null

        fun getInstance(localDataSource: LocalDataSource, appExecutors: AppExecutors): AppRepository =
                instance ?: synchronized(this) {
                    instance ?: AppRepository(localDataSource, appExecutors)
                }
    }

    override fun getListBooking(): LiveData<PagedList<BookingEntity>> {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(5)
                .setPageSize(5)
                .build()
        return LivePagedListBuilder(localDataSource.getListBooking(), config).build()
    }

    override fun getBooking(id: Int): LiveData<BookingEntity> {
        return localDataSource.getBooking(id)
    }

    override fun insertBooking(booking: BookingEntity) {
        val list = ArrayList<BookingEntity>()
        val entity = BookingEntity(
                booking.id,
                booking.nama,
                booking.tglin,
                booking.jamin,
                booking.tglout,
                booking.jamout,
                booking.harga
        )
        list.add(entity)
        appExecutors.diskIO().execute { localDataSource.insertBooking(list) }
    }

    override fun updateBooking(booking: BookingEntity) {
        val list = ArrayList<BookingEntity>()
        val entity = BookingEntity(
                booking.id,
                booking.nama,
                booking.tglin,
                booking.jamin,
                booking.tglout,
                booking.jamout,
                booking.harga
        )
        list.add(entity)
        appExecutors.diskIO().execute { localDataSource.updateBooking(list) }
    }

    override fun deleteBooking(id: Int) {
        appExecutors.diskIO().execute { localDataSource.deleteBooking(id) }
    }

    override fun searchBooking(cari: String): LiveData<PagedList<BookingEntity>> {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(5)
                .setPageSize(5)
                .build()
        return LivePagedListBuilder(localDataSource.searchBooking(cari), config).build()
    }

    override fun searchTglIn(cari: String, dari: String, ke: String): LiveData<PagedList<BookingEntity>> {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(5)
                .setPageSize(5)
                .build()
        return LivePagedListBuilder(localDataSource.searchTglIn(cari, dari, ke), config).build()
    }

    override fun searchTglOut(cari: String, dari: String, ke: String): LiveData<PagedList<BookingEntity>> {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(5)
                .setPageSize(5)
                .build()
        return LivePagedListBuilder(localDataSource.searchTglOut(cari, dari, ke), config).build()
    }
}
package com.alcorp.bovill.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.alcorp.bovill.data.source.local.entity.BookingEntity
import com.alcorp.bovill.data.source.local.room.AppDao

class LocalDataSource private constructor(private val mAppdao: AppDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(appDao: AppDao): LocalDataSource {
            if (INSTANCE == null) {
                INSTANCE = LocalDataSource(appDao)
            }
            return INSTANCE as LocalDataSource
        }
    }

    fun getListBooking(): DataSource.Factory<Int, BookingEntity> = mAppdao.getListBooking()

    fun getBooking(id: Int): LiveData<BookingEntity> = mAppdao.getBooking(id)

    fun insertBooking(booking: List<BookingEntity>) = mAppdao.insertBooking(booking)

    fun updateBooking(booking: List<BookingEntity>) = mAppdao.updateBooking(booking)

    fun deleteBooking(id: Int) = mAppdao.deleteBooking(id)

    fun searchBooking(cari: String): DataSource.Factory<Int, BookingEntity> = mAppdao.searchBooking(cari)

    fun searchTglIn(cari: String, dari: String, ke: String): DataSource.Factory<Int, BookingEntity> = mAppdao.searchTglIn(cari, dari, ke)

    fun searchTglOut(cari: String, dari: String, ke: String): DataSource.Factory<Int, BookingEntity> = mAppdao.searchTglOut(cari, dari, ke)
}
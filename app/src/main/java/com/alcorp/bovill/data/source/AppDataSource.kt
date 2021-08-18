package com.alcorp.bovill.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.alcorp.bovill.data.source.local.entity.BookingEntity

interface AppDataSource {

    fun getListBooking(): LiveData<PagedList<BookingEntity>>

    fun getBooking(id: Int): LiveData<BookingEntity>

    fun insertBooking(booking: BookingEntity)

    fun updateBooking(booking: BookingEntity)

    fun deleteBooking(id: Int)

    fun searchBooking(cari: String): LiveData<PagedList<BookingEntity>>

    fun searchTglIn(cari: String, dari: String, ke: String): LiveData<PagedList<BookingEntity>>

    fun searchTglOut(cari: String, dari: String, ke: String): LiveData<PagedList<BookingEntity>>
}
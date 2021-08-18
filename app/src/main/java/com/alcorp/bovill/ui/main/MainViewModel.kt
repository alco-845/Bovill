package com.alcorp.bovill.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.alcorp.bovill.data.source.AppRepository
import com.alcorp.bovill.data.source.local.entity.BookingEntity

class MainViewModel(private val appRepository: AppRepository) : ViewModel() {

    var booking: LiveData<PagedList<BookingEntity>>? = null
        get() {
            if (field == null) {
                field = getListBooking()
            }
            return field
        }
        private set

    fun getListBooking() : LiveData<PagedList<BookingEntity>> = appRepository.getListBooking()

    fun removeBooking(id: Int) = appRepository.deleteBooking(id)

    fun searchBooking(cari: String) : LiveData<PagedList<BookingEntity>> = appRepository.searchBooking(cari)

    fun searchTglIn(cari: String, dari: String, ke: String) : LiveData<PagedList<BookingEntity>> = appRepository.searchTglIn(cari, dari, ke)

    fun searchTglOut(cari: String, dari: String, ke: String) : LiveData<PagedList<BookingEntity>> = appRepository.searchTglOut(cari, dari, ke)
}
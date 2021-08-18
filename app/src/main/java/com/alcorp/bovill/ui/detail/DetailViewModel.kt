package com.alcorp.bovill.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.alcorp.bovill.data.source.AppRepository
import com.alcorp.bovill.data.source.local.entity.BookingEntity

class DetailViewModel(private val appRepository: AppRepository) : ViewModel() {

    var id = MutableLiveData<Int>()

    fun setSelected(id: Int) {
        this.id.value = id
    }

    var booking: LiveData<BookingEntity> = Transformations.switchMap(id) { bookingId ->
        appRepository.getBooking(bookingId)
    }

    fun createBooking(bookingEntity: BookingEntity) {
        appRepository.insertBooking(bookingEntity)
    }

    fun setBooking(bookingEntity: BookingEntity) {
//        val bookingRes = booking.value!!
        appRepository.updateBooking(bookingEntity)
    }
}
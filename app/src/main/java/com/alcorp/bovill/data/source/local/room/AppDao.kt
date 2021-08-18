package com.alcorp.bovill.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.alcorp.bovill.data.source.local.entity.BookingEntity

@Dao
interface AppDao {
    @Query("SELECT * FROM bookingentities")
    fun getListBooking(): DataSource.Factory<Int, BookingEntity>

    @Transaction
    @Query("SELECT * FROM bookingentities WHERE id = :id")
    fun getBooking(id: Int): LiveData<BookingEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBooking(booking: List<BookingEntity>)

    @Update
    fun updateBooking(booking: List<BookingEntity>)

    @Query("DELETE FROM bookingentities WHERE id = :id")
    fun deleteBooking(id: Int)

    @Query("SELECT * FROM bookingentities WHERE nama LIKE '%' || :cari || '%' ORDER BY id ASC")
    fun searchBooking(cari: String): DataSource.Factory<Int, BookingEntity>

    @Query("SELECT * FROM bookingentities WHERE nama LIKE '%' || :cari || '%' AND tglin BETWEEN :dari AND :ke ORDER BY id ASC")
    fun searchTglIn(cari: String, dari: String, ke: String): DataSource.Factory<Int, BookingEntity>

    @Query("SELECT * FROM bookingentities WHERE nama LIKE '%' || :cari || '%' AND tglout BETWEEN :dari AND :ke ORDER BY id ASC")
    fun searchTglOut(cari: String, dari: String, ke: String): DataSource.Factory<Int, BookingEntity>
}
package com.alcorp.bovill.ui.detail

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.text.format.DateFormat
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alcorp.bovill.R
import com.alcorp.bovill.data.source.local.entity.BookingEntity
import com.alcorp.bovill.utils.Helper
import com.alcorp.bovill.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_detail.*
import java.util.*

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private var hour: Int = 0
    private var minute: Int = 0

    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0

    private var id: Int = 0

    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.title = resources.getString(R.string.txt_tambah)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val calendar = Calendar.getInstance()
        year = calendar[Calendar.YEAR]
        month = calendar[Calendar.MONTH]
        day = calendar[Calendar.DAY_OF_MONTH]
        hour = calendar[Calendar.HOUR_OF_DAY]
        minute = calendar[Calendar.MINUTE]

        edtTglIn.setText(Helper.setDatePickerNormal(year, month + 1, day))
        edtTglOut.setText(Helper.setDatePickerNormal(year, month + 1, day))

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        val extras = intent.extras
        if (extras != null) {
            supportActionBar?.title = resources.getString(R.string.txt_perbarui)

            id = extras.getInt(EXTRA_ID)

            if (id != null) {
                viewModel.setSelected(id)

                viewModel.booking.observe(this, Observer { results ->
                    if (results != null) {
                        edtNama.setText(results.nama)
                        edtTglIn.setText(Helper.dateToNormal(results.tglin))
                        edtJamIn.setText(Helper.timeToNormal(results.jamin))
                        edtTglOut.setText(Helper.dateToNormal(results.tglout))
                        edtJamOut.setText(Helper.timeToNormal(results.jamout))
                        edtHarga.setText(results.harga)
                    }
                })
            }
        }

        edtTglIn.setOnClickListener(this)
        edtJamIn.setOnClickListener(this)
        edtTglOut.setOnClickListener(this)
        edtJamOut.setOnClickListener(this)
        btnSave.setOnClickListener(this)
    }

    fun setDate(i: Int) = showDialog(i)

    fun setTime(i: Int) = showDialog(i)

    override fun onCreateDialog(id: Int): Dialog {
        return if (id == 1) {
            DatePickerDialog(this, tglIn, year, month, day)
        } else if (id == 3) {
            DatePickerDialog(this, tglOut, year, month, day)
        } else if (id == 2) {
            TimePickerDialog(this, jamIn, hour, minute, DateFormat.is24HourFormat(this))
        } else {
            TimePickerDialog(this, jamOut, hour, minute, DateFormat.is24HourFormat(this))
        }
    }

    private val tglIn =
        OnDateSetListener { tgl, thn, bln, hari ->
            edtTglIn.setText(Helper.setDatePickerNormal(thn, bln + 1, hari))
        }

    private val tglOut =
        OnDateSetListener { tgl, thn, bln, hari ->
            edtTglOut.setText(Helper.setDatePickerNormal(thn, bln + 1, hari))
        }

    private val jamIn =
        OnTimeSetListener { jam, hour, minute ->
            edtJamIn.setText(Helper.setTimePickerNormal(hour, minute))
        }

    private val jamOut =
        OnTimeSetListener { jam, hour, minute ->
            edtJamOut.setText(Helper.setTimePickerNormal(hour, minute))
        }

    override fun onClick(view: View) {
        if (view == edtTglIn) {
            setDate(1)
        } else if (view == edtJamIn) {
            setTime(2)
        } else if (view == edtTglOut) {
            setDate(3)
        } else if (view == edtJamOut) {
            setTime(4)
        } else if (view == btnSave) {
            val nama = edtNama.text.toString().trim()
            val tglIn = edtTglIn.text.toString().trim()
            val jamIn = edtJamIn.text.toString().trim()
            val tglOut = edtTglOut.text.toString().trim()
            val jamOut = edtJamOut.text.toString().trim()
            val harga = edtHarga.text.toString().trim()

            if (nama == "" || tglIn == "" || jamIn == "" || tglOut == "" || jamOut == "" || harga == "") {
                Toast.makeText(this@DetailActivity, resources.getString(R.string.txt_toast_warning), Toast.LENGTH_SHORT).show()
            } else {
                val extras = intent.extras
                val entity = BookingEntity(
                        id,
                        nama,
                        Helper.convertDate(tglIn),
                        Helper.convertTime(jamIn),
                        Helper.convertDate(tglOut),
                        Helper.convertTime(jamOut),
                        harga
                )
                if (extras != null) {
                    viewModel.setBooking(entity)
                    Toast.makeText(this@DetailActivity, resources.getString(R.string.txt_toast_perbarui), Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    viewModel.createBooking(entity)
                    Toast.makeText(this@DetailActivity, resources.getString(R.string.txt_toast_tambah), Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
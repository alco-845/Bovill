package com.alcorp.bovill.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alcorp.bovill.R
import com.alcorp.bovill.data.source.local.entity.BookingEntity
import com.alcorp.bovill.ui.detail.DetailActivity
import com.alcorp.bovill.utils.Helper
import kotlinx.android.synthetic.main.item_list.view.*

class MainAdapter internal constructor() : PagedListAdapter<BookingEntity, MainAdapter.MainViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BookingEntity>() {
            override fun areItemsTheSame(oldItem: BookingEntity, newItem: BookingEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: BookingEntity, newItem: BookingEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val booking = getItem(position) as BookingEntity
        holder.bind(booking)
    }

    fun getSwipedData(swipedPosition: Int) : BookingEntity = getItem(swipedPosition) as BookingEntity

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(booking: BookingEntity) {
            with(itemView) {
                tvNama.text = booking.nama
                tvIn.text = resources.getString(R.string.txt_in) + "    : "+ Helper.dateToNormal(booking.tglin) + " | " + Helper.timeToNormal(booking.jamin)
                tvOut.text = resources.getString(R.string.txt_out) + " : "+ Helper.dateToNormal(booking.tglout) + " | " + Helper.timeToNormal(booking.jamout)
                tvHarga.text = resources.getString(R.string.txt_harga) + " : " + Helper.removeE(booking.harga)

                setOnClickListener {
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_ID, booking.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}
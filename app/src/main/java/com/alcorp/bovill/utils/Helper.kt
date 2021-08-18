package com.alcorp.bovill.utils

import android.text.TextUtils
import java.text.DecimalFormat

class Helper {
    companion object {
        fun setDatePickerNormal(year: Int, month: Int, day: Int): String? {
            val bln = if (month < 10) {
                "0$month"
            } else {
                month.toString()
            }
            val hri = if (day < 10) {
                "0$day"
            } else {
                day.toString()
            }
            return "$hri/$bln/$year"
        }

        fun setTimePickerNormal(hour: Int, minute: Int): String? {
            val jam: String = if (hour < 10) {
                "0$hour"
            } else {
                hour.toString()
            }
            val menit: String = if (minute < 10) {
                "0$minute"
            } else {
                minute.toString()
            }
            return "$jam:$menit"
        }

        fun dateToNormal(date: String): String? {
            return try {
                val b1 = date.substring(4)
                val b2 = b1.substring(2)
                val m = b1.substring(0, 2)
                val d = b2.substring(0, 2)
                val y = date.substring(0, 4)
                "$d/$m/$y"
            } catch (e: Exception) {
                "ini tanggal"
            }
        }

        fun timeToNormal(time: String): String? {
            return try {
                val b1 = time.substring(2)
                val m = b1.substring(0, 2)
                val h = time.substring(0, 2)
                "$h:$m"
            } catch (e: java.lang.Exception) {
                "ini waktu"
            }
        }

        fun convertDate(date: String): String {
            val a = date.split("/").toTypedArray()
            return a[2] + a[1] + a[0]
        }

        fun convertTime(time: String): String {
            val a = time.split(":").toTypedArray()
            return a[0] + a[1]
        }

        private fun numberFormat(number: String): String {
            try {
                var hasil = ""
                val b = number.split("\\.")
                if (b.size == 1) {
                    val a = number.split("")
                    var c = 0
                    for (i in a.indices.reversed()) {
                        if (c == 4 && !TextUtils.isEmpty(a[i])) {
                            hasil = a[i] + "." + hasil
                            c = 2
                        } else {
                            hasil = a[i] + hasil
                            c++
                        }
                    }
                } else {
                    val a = b[0].split("")
                    var c = 0
                    for (i in a.indices.reversed()) {
                        if (c == 4 && !TextUtils.isEmpty(a[i])) {
                            hasil = a[i] + "." + hasil
                            c = 2
                        } else {
                            hasil = a[i] + hasil
                            c++
                        }
                    }
                    hasil += "," + b[1]
                }
                return hasil
            } catch (e: java.lang.Exception) {
                return ""
            }
        }

        fun removeE(value: String): String {
            val hasil: Double = value.toDouble()
            val df = DecimalFormat("#")
            df.maximumFractionDigits = 8
            return numberFormat(df.format(hasil))
        }
    }
}
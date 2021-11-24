package com.example.healthbackend.webapp.helperclass

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.floor


class Formatter {

    private val sdf = SimpleDateFormat("yyyy/MM/dd")

    fun calculateBMI(weightKgs: Double, heightCm: Double): Double {

//        BMI = Weight(kg)/ Height(M)2 )

        val heightM = heightCm / 100
        val heightSquare = heightM * heightM

        return weightKgs / heightSquare

    }

    fun calculateAge(dateOfBirth: Date): Int {

        val now = Date()
        val timeBetween: Long = now.time - dateOfBirth.time
        val yearsBetween = timeBetween / 3.15576e+10
        return floor(yearsBetween).toInt()

    }


    fun convertDateMillis(strDate: String): Date {

        val millis = strDate.toLong()
        val date = Date(millis)
        return changeDateFormat(date)
    }

    fun changeDateFormat(date: Date): Date {

        val strDate = sdf.format(date)
        return sdf.parse((strDate))
    }
    fun changeDateFormat2(date: Date): String {
        val sdf = SimpleDateFormat("yyyy/MM/dd")
        val strDate = sdf.format(date)
        return strDate
    }
    fun compareDates(visitationDate: Date, providedDate: Date): Boolean{

        var isSameDate = false
        val newVisitationDate = changeDateFormat2(visitationDate)
        val newProvidedDate = changeDateFormat2(providedDate)

        val sdformat = SimpleDateFormat("yyyy/MM/dd")
        val d1 = sdformat.parse(newVisitationDate)
        val d2 = sdformat.parse(newProvidedDate)

//        if (d1 > d2) {
//            println("Date 1 occurs after Date 2")
//        } else if (d1 < d2) {
//            println("Date 1 occurs before Date 2")
//        }
        if (d1.compareTo(d2) === 0) {
            isSameDate = true;
        }
        return isSameDate

    }
}
package com.example.healthbackend.webapp.helperclass

import java.util.*
import kotlin.math.floor

class Formatter {

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
}
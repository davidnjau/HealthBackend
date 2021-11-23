package com.example.healthbackend.webapp.helperclass

class Formatter {

    fun calculateBMI(weightKgs: Double, heightCm: Double): Double {

//        BMI = Weight(kg)/ Height(M)2 )

        val heightM = heightCm / 100
        val heightSquare = heightM * heightM

        return weightKgs / heightSquare

    }

}
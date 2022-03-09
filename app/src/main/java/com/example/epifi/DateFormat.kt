package com.example.epifi

import java.util.*

class DateFormat {

    companion object{
        private val maxDays = arrayOf(0,31,28,31,30,31,30,31,31,30,31,30,31)


        fun validateDate(date: Int, month:Int,year:Int): Int{
            val gregorian = GregorianCalendar()
            val y = gregorian.get(Calendar.YEAR)
            val m = gregorian.get(Calendar.MONTH)+1
            val d = gregorian.get(Calendar.DATE)
            if(year>y
                || (year==y && month>m)
                || (year==y && month == m && date > d) )return 0

            if(year<1000)return 0
            if(year%4 == 0 && month == 2){
                if(date in 1..29){
                    if(y-year<18 ||(y-year ==18 && (m<month || (m==month && d<date)) ) )return 2
                    return 1
                }
            }
            else{
                if(month in 1..12){
                    if(date in 1..maxDays[month]){
                        if(y-year<18 ||(y-year ==18 && (m<month || (m==month && d<date)) ) )return 2
                        return 1
                    }
                }
            }

            return 0
        }


    }
}


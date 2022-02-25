package com.example.dummynotes.others

import android.app.Application

class UtilFun {
    companion object{

        fun getApplicationName(application : Application) : String{
            var applicationName = ""
            try {
                val applicationInfo = application.applicationInfo
                val stringId = applicationInfo.labelRes
                if (stringId == 0) {
                    applicationName = applicationInfo.nonLocalizedLabel.toString()
                }
                else {
                    applicationName = application.getString(stringId)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return applicationName
        }

    }
}
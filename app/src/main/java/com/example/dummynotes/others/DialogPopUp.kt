package com.example.dummynotes.others

import android.content.Context
import androidx.appcompat.app.AlertDialog

class DialogPopUp {

    companion object{

        fun dialogWithYesNo(context : Context, title : String, message : String) {

            val builder = AlertDialog.Builder(context)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setPositiveButton("ok") { dialog, which ->
            }

            builder.show()

        }





    }


}
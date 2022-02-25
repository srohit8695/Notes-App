package com.example.dummynotes.others

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.dummynotes.R
import java.io.FileDescriptor

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



       /* fun customBriefViewDialog(context: Context, title: String, descriptor: String){

            val dialogView = .inflate(R.layout.custome_dialog_brief_view, null)

            val customDialog = AlertDialog.Builder(this)
                .setView(dialogView)
                .show()

        }*/





    }


}
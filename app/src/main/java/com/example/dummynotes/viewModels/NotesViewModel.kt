package com.example.dummynotes.viewModels

import android.provider.ContactsContract
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dummynotes.model.Notes

class NotesViewModel : ViewModel() {
    var list = MutableLiveData<ArrayList<Notes>>()
    var tempList = arrayListOf<Notes>()

    fun addNotes(notes : Notes){
        tempList.add(notes)
        list.value = tempList
    }

    fun removeNotes(notes: Notes){
        tempList.remove(notes)
        list.value = tempList
    }

    fun totalNotes() = list.value?.size

}
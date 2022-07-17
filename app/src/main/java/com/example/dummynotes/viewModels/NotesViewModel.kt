package com.example.dummynotes.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dummynotes.adapters.NotesRecyclerAdapter
import com.example.dummynotes.database.NotesEntity
import com.example.dummynotes.database.NotesRepository

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : NotesRepository = NotesRepository(application)
    val list : LiveData<List<NotesEntity>> = repository.getAllData()


    fun addNotes(notes : NotesEntity) : Long{
        return repository.insertData(notes)
    }

    fun removeNotes(notes: NotesEntity){
        repository.deleteData(notes)
    }

    fun deleteByID(id : Int){
        repository.deleteById(id)
    }

    fun updateNotes(notes : NotesEntity){
        repository.updateData(notes)
    }

    fun totalNotes() = list

    fun priorityData(priority1 : Boolean, priority2 : Boolean, priority3 : Boolean, priority4 : Boolean, dataAdapter : NotesRecyclerAdapter) {
        try {

            var tempList = MutableLiveData<List<NotesEntity>>()
            val tempData = mutableListOf<NotesEntity>()
            if(priority1){
                try {
                    for(i in list.value!!)
                        if(i.priority == 1)
                            tempData.add(i)

                    Log.d("p1 ", tempData.toString())
//                    tempList = repository.getPriority1() as MutableLiveData<List<NotesEntity>>

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            if(priority2){
                try {
                    for(i in list.value!!)
                        if(i.priority == 2)
                            tempData.add(i)

                    println("p2 "+repository.getPriority2().value.toString())
                    tempList = repository.getPriority2() as MutableLiveData<List<NotesEntity>>
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            if(priority3){
                try {
                    for(i in list.value!!)
                        if(i.priority == 3)
                            tempData.add(i)

                    println("p3 "+repository.getPriority3().value.toString() )
                    tempList = repository.getPriority3() as MutableLiveData<List<NotesEntity>>

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            if(priority4) {
                try {
                    for(i in list.value!!)
                        if(i.priority == 4)
                            tempData.add(i)

                    println("p4 "+repository.getPriority4().value.toString() )
                    tempList = repository.getPriority4() as MutableLiveData<List<NotesEntity>>

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            dataAdapter.updateList(tempData)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}
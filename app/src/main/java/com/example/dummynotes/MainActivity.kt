package com.example.dummynotes


import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.dummynotes.activities.AddNotes
import com.example.dummynotes.adapters.DragUpDownAdapter
import com.example.dummynotes.adapters.NotesRecyclerAdapter
import com.example.dummynotes.database.NotesEntity
import com.example.dummynotes.databinding.ActivityMainBinding
import com.example.dummynotes.others.DialogPopUp
import com.example.dummynotes.others.SwipeHelper
import com.example.dummynotes.viewModels.NotesViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: NotesViewModel
    private lateinit var mainBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        try {

            viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
            val dataAdapter = NotesRecyclerAdapter(this)
            mainBinding.recyclerView.adapter = dataAdapter

            viewModel.list.observe(
                this
            ) {
                dataAdapter.updateList(it)
            }


            mainBinding.addNotesFab.setOnClickListener {
                val intent = Intent(this, AddNotes::class.java )
                startActivity(intent)
            }


            /*val callback = DragUpDownAdapter(dataAdapter,
                ItemTouchHelper.UP.or(ItemTouchHelper.DOWN), 0)*/
            val callback = DragUpDownAdapter(dataAdapter,0, 0)
            val helper = ItemTouchHelper(callback)
            helper.attachToRecyclerView(mainBinding.recyclerView)


            swiprFeature(dataAdapter)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    fun swiprFeature(dataAdapter : NotesRecyclerAdapter){

        try {
            object : SwipeHelper(this, recyclerView, false) {

                override fun instantiateUnderlayButton(viewHolder: RecyclerView.ViewHolder?, underlayButtons: MutableList<UnderlayButton>?) {


                    underlayButtons?.add(SwipeHelper.UnderlayButton("Delete",
                        AppCompatResources.getDrawable(this@MainActivity,R.drawable.ic_baseline_delete_24),
                        Color.parseColor("#e8d20e"), R.attr.text_color,
                        UnderlayButtonClickListener { pos: Int ->

                            val builder = AlertDialog.Builder(this@MainActivity)
                            builder.setMessage("Wish to delete")
                            builder.setPositiveButton("Yes") { dialog, which ->

                                viewModel.removeNotes(dataAdapter.noteFromPosition(pos))
                                dataAdapter.notifyItemChanged(pos)
                            }
                            builder.setNegativeButton("No"){ dialog, which ->
                            }

                            builder.show()

                        }

                    ))

                    underlayButtons?.add(SwipeHelper.UnderlayButton("Edit",
                        AppCompatResources.getDrawable(this@MainActivity,R.drawable.ic_baseline_edit_24),
                        Color.parseColor("#e35d5d"),  R.attr.text_color,
                        UnderlayButtonClickListener { pos: Int ->
                            val noteToEdit = dataAdapter.noteFromPosition(pos)
                            val intent = Intent(baseContext, AddNotes::class.java )
                            intent.putExtra("id", noteToEdit.id.toString())
                            intent.putExtra("title", noteToEdit.title)
                            intent.putExtra("message", noteToEdit.notes)
                            startActivity(intent)
                        }

                    ))

                    // More Button
                    underlayButtons?.add(SwipeHelper.UnderlayButton("More",
                        AppCompatResources.getDrawable(this@MainActivity,R.drawable.ic_baseline_more_horiz_24),
                        Color.parseColor("#60d690"),  R.attr.text_color,
                        UnderlayButtonClickListener { pos: Int ->

                            Toast.makeText(this@MainActivity,"More Button CLicked at Position: " + pos,
                                Toast.LENGTH_SHORT).show()
                            dataAdapter.notifyItemChanged(pos)
                        }

                    ))


                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }




}


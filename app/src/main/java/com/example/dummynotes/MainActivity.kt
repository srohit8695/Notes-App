package com.example.dummynotes


import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.dummynotes.activities.AddNotes
import com.example.dummynotes.adapters.DeleteIconInterface
import com.example.dummynotes.adapters.DragUpDownAdapter
import com.example.dummynotes.adapters.NotesRecyclerAdapter
import com.example.dummynotes.database.NotesEntity
import com.example.dummynotes.databinding.ActivityMainBinding
import com.example.dummynotes.others.SwipeHelper
import com.example.dummynotes.viewModels.NotesViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), DeleteIconInterface {

    private lateinit var viewModel: NotesViewModel
    private lateinit var mainBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        try {
            viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
            val dataAdapter = NotesRecyclerAdapter(this,  this)
            mainBinding.recyclerView.adapter = dataAdapter

            viewModel.list.observe(
                this, {
                    dataAdapter.updateList(it)
                }
            )

            val callback = DragUpDownAdapter(dataAdapter,
                ItemTouchHelper.UP.or(ItemTouchHelper.DOWN), 0)
            val helper = ItemTouchHelper(callback)
            helper.attachToRecyclerView(mainBinding.recyclerView)

            mainBinding.addNotesFab.setOnClickListener {
                val intent = Intent(this, AddNotes::class.java )
                startActivity(intent)
            }

            object : SwipeHelper(this, recyclerView, false) {

                override fun instantiateUnderlayButton(viewHolder: RecyclerView.ViewHolder?, underlayButtons: MutableList<UnderlayButton>?) {
                    // Archive Button
                    underlayButtons?.add(SwipeHelper.UnderlayButton("Archive", AppCompatResources.getDrawable(this@MainActivity,R.drawable.ic_baseline_archive_24 ), Color.parseColor("#000000"), Color.parseColor("#ffffff"),
                        UnderlayButtonClickListener { pos: Int ->
                            Toast.makeText(this@MainActivity,"Delete clicked at " + pos, Toast.LENGTH_SHORT).show()
                        }
                    ))

                    // Flag Button
                    underlayButtons?.add(SwipeHelper.UnderlayButton("Flag",
                        AppCompatResources.getDrawable(this@MainActivity,R.drawable.ic_baseline_flag_24),
                        Color.parseColor("#FF0000"), Color.parseColor("#ffffff"),
                        UnderlayButtonClickListener { pos: Int ->
                            Toast.makeText(this@MainActivity,"Flag Button Clicked at Position: " + pos,
                                Toast.LENGTH_SHORT).show()
                            dataAdapter.notifyItemChanged(pos)
                        }

                    ))

                    // More Button
                    underlayButtons?.add(SwipeHelper.UnderlayButton("More",
                        AppCompatResources.getDrawable(this@MainActivity,R.drawable.ic_baseline_more_horiz_24),
                        Color.parseColor("#00FF00"), Color.parseColor("#ffffff"),
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

    override fun onDeleteIconClick(note: NotesEntity) {
        viewModel.removeNotes(note)
    }


}


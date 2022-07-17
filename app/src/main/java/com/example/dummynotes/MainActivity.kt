package com.example.dummynotes


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.dummynotes.activities.AddNotes
import com.example.dummynotes.adapters.DragUpDownAdapter
import com.example.dummynotes.adapters.NotesRecyclerAdapter
import com.example.dummynotes.databinding.ActivityMainBinding
import com.example.dummynotes.others.SwipeHelper
import com.example.dummynotes.others.SwipeHelper.UnderlayButtonClickListener
import com.example.dummynotes.others.UtilFun
import com.example.dummynotes.viewModels.NotesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: NotesViewModel
    private lateinit var mainBinding : ActivityMainBinding
    private lateinit var dataAdapter : NotesRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        try {

            viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
            dataAdapter = NotesRecyclerAdapter(this) {
                customBriefViewDialog(it.title, it.notes)
            }

            mainBinding.recyclerView.adapter = dataAdapter

            viewModel.list.observe(
                this
            ) {
                for(i in it)
                print("Rohit "+i.toString()+"\n")
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

    fun customBriefViewDialog( title: String, descriptor: String){

        val dialogView = layoutInflater.inflate(R.layout.custome_dialog_brief_view, null)

        val customDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .show()

        val titleTV = dialogView.findViewById<TextView>(R.id.title)
        titleTV.text = title

        val descriptionTV = dialogView.findViewById<TextView>(R.id.description)
        descriptionTV.text = descriptor

    }

    private fun swiprFeature(dataAdapter : NotesRecyclerAdapter){

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
                            intent.putExtra("priority", noteToEdit.priority)
                            startActivity(intent)
                        }

                    ))


                    underlayButtons?.add(SwipeHelper.UnderlayButton("More",
                        AppCompatResources.getDrawable(this@MainActivity,R.drawable.ic_baseline_share_24),
                        Color.parseColor("#60d690"),  R.attr.text_color,
                        UnderlayButtonClickListener { pos: Int ->
                            val noteToView = dataAdapter.noteFromPosition(pos)
                                val shareText = Intent(Intent.ACTION_SEND)
                                shareText.type = "text/plain"
                                val messageToShare = noteToView.title+"\n"+noteToView.notes
                                shareText.putExtra(Intent.EXTRA_SUBJECT, "Subject from "+UtilFun.getApplicationName(application))
                                shareText.putExtra(Intent.EXTRA_TEXT, messageToShare)
                                startActivity(Intent.createChooser(shareText, "Share Via"))
                        }

                    ))


                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_screen_title_options,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_filter -> {
            showBottomSheetFilters()
            true
        }
        R.id.action_sort ->{
            showBottomSheetSort()
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun showBottomSheetFilters() {

        /*var binding: FilterBottomSheetBinding

        binding = BottomSheetDatePickerBinding.inflate(
            LayoutInflater.from(context)
        )
        setContentView(binding.root)*/

        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.filter_bottom_sheet)
        val priority1 = bottomSheetDialog.findViewById<CheckBox>(R.id.priority1)
        val priority2 = bottomSheetDialog.findViewById<CheckBox>(R.id.priority2)
        val priority3 = bottomSheetDialog.findViewById<CheckBox>(R.id.priority3)
        val priority4 = bottomSheetDialog.findViewById<CheckBox>(R.id.priority4)
        val applyFilters = bottomSheetDialog.findViewById<Button>(R.id.applyFiltersButton)
        val clearFilters = bottomSheetDialog.findViewById<Button>(R.id.clearFiltersButton)

        clearFilters?.setOnClickListener {
            viewModel.list.value?.let { it1 -> dataAdapter.updateList(it1) }
            bottomSheetDialog.dismiss()
        }

        applyFilters?.setOnClickListener {

            var checkPriority1 = false
            var checkPriority2 = false
            var checkPriority3 = false
            var checkPriority4 = false

            if(priority1!!.isChecked)
            {
                checkPriority1 = true
            }

            if(priority2!!.isChecked)
            {
                checkPriority2 = true
            }

            if(priority3!!.isChecked)
            {
                checkPriority3 = true
            }

            if(priority4!!.isChecked)
            {
                checkPriority4 = true
            }

            if (checkPriority1 || checkPriority2 || checkPriority3 || checkPriority4) {//executes only if any check box is ticked
                viewModel.priorityData(checkPriority1, checkPriority2, checkPriority3, checkPriority4, dataAdapter)
            }

            bottomSheetDialog.dismiss()
        }


        bottomSheetDialog.show()
    }

    private fun showBottomSheetSort() {
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.sort_bottom_sheet)
        val asscendingSort = bottomSheetDialog.findViewById<RadioButton>(R.id.asscendingSort)
        val description = bottomSheetDialog.findViewById<RadioButton>(R.id.description)

        bottomSheetDialog.show()
    }


}









































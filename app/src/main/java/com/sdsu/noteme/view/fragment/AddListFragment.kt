package com.sdsu.noteme.view.fragment

import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.core.view.size
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sdsu.noteme.R
import com.sdsu.noteme.Util.NoteUtil
import com.sdsu.noteme.data.db.async.InsertListTask
import com.sdsu.noteme.data.model.AllNotesModel
import com.sdsu.noteme.viewmodel.AddListViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.add_list_fragment.*


class AddListFragment : DialogFragment() {

    companion object {
        fun newInstance() =
            AddListFragment()
    }
    lateinit var finalString:String
    var itemlist = arrayListOf<String>()
    lateinit var adapter: ArrayAdapter<String>
    private lateinit var viewModel: AddListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddListViewModel::class.java)
        setUpView(view)
        initListener()
        observeAddListModel()
        observeValidateInputs(view)
    }

    private fun observeAddListModel() {
        viewModel.getValueList().observe(viewLifecycleOwner, Observer<Boolean>{ value ->
            if(value){
                Toast.makeText(context, "Added to Database", Toast.LENGTH_LONG).show()
                closeCurrentFragment()
            }else
                Log.e("NO ","No");
        })
    }

    private fun closeCurrentFragment() {
        fragmentManager?.beginTransaction()?.remove(this)?.commit()
    }

    private fun initListener() {
        add.setOnClickListener {
            itemlist.add(editText.text.toString())
            listView.adapter = adapter
            adapter.notifyDataSetChanged()
            // This is because every time when you add the item the input space or the eidt text space will be cleared
            editText.text.clear()
        }

        delete.setOnClickListener {
            val position: SparseBooleanArray = listView.checkedItemPositions
            val count = listView.count
            var item = count - 1
            while (item >= 0) {
                if (position.get(item)) {
                    adapter.remove(itemlist.get(item))
                }
                item--
            }
            position.clear()
            adapter.notifyDataSetChanged()
        }

        listView.setOnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(
                context,
                "You Selected the item --> " + itemlist.get(i),
                Toast.LENGTH_LONG
            ).show()
        }
        addList.setOnClickListener {
            if (view?.let { it1 ->  validateInputs(it1) }!!) {
                if (itemlist != null) {
                    finalString = convertHashMapToGson(makeListHashMap())
                }

                if (itemlist.size > 0) {
                    saveList()
                }
            }
        }
    }

    private fun saveList() {
        var title :String
        title = listTitle.text.toString()
        var notesModel =
                AllNotesModel(
                        0,
                        title,
                        finalString,
                        NoteUtil.LIST,"",
                        "",3)

        InsertListTask(this.context, viewModel, notesModel).execute()

    }

    private fun convertHashMapToGson(makeListHashMap: HashMap<String, Boolean>): String {
        val gson = Gson()
        var jsonString: String = gson.toJson(makeListHashMap)
        //Toast.makeText(context,"string"+ jsonString,Toast.LENGTH_SHORT).show()
        return jsonString
    }

    private fun makeListHashMap(): HashMap<String, Boolean> {
        val checked: SparseBooleanArray = listView.checkedItemPositions
        val data = hashMapOf<String,Boolean >()
        for (i in 0 until listView.adapter.count) {
            if (checked[i]) {
                data.put(itemlist.get(i), true)
            } else {
                data.put(itemlist.get(i), false)
            }
        }
        return data
    }

    private fun setUpView(view: View) {

        adapter = ArrayAdapter(view.context,
                android.R.layout.simple_list_item_multiple_choice
                , itemlist )
    }

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.MATCH_PARENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }

    private fun validateInputs(view: View): Boolean {
        //Form Validation
        var listTitle = view.findViewById<EditText>(R.id.listTitle)
        var listItemView = view.findViewById<ListView>(R.id.listView)
        if(!NoteUtil.checkInput(listTitle)) {
            listTitle.error = "Title can't be empty!"
            return false
        } else if(listItemView.count <= 0) {
            //Toast.makeText(context, "List is empty..", Toast.LENGTH_SHORT).show()
            editText.error = "List is empty.."
            return false
        }
        return true
    }

    private fun observeValidateInputs(view: View) {
        var listTitle = view.findViewById<EditText>(R.id.listTitle)
        listTitle.setOnFocusChangeListener { v, hasFocus ->
            run {
                if (!hasFocus) {
                    if (!NoteUtil.checkInput(listTitle)) {
                        listTitle.error = "Title can't be empty!"
                    }
                }
            }
        }

        var listItemView = view.findViewById<ListView>(R.id.listView)
        listItemView.setOnFocusChangeListener { v, hasFocus ->
            run {
                if (!hasFocus) {
                    if (listItemView.adapter.count <= 0) {
                        Toast.makeText(context, "List is empty..", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}






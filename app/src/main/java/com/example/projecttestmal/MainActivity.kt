package com.example.projecttestmal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var editText: EditText
    private lateinit var addButton: Button
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        editText = findViewById(R.id.editText)
        addButton = findViewById(R.id.addButton)

        val itemList = mutableListOf<String>()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, itemList)
        listView.adapter = adapter

        addButton.setOnClickListener {
            val newItem = editText.text.toString()
            if (newItem.isNotEmpty()) {
                itemList.add(newItem)
                adapter.notifyDataSetChanged()
                editText.text.clear()
            }
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = itemList[position]
            showItemDialog(selectedItem, position)
        }
    }

    private fun showItemDialog(item: String, position: Int) {
        val dialog = ItemDialogFragment.newInstance(item)
        dialog.setOnItemEditedListener { editedItem ->
            editItem(position, editedItem)
        }
        dialog.setOnItemDeletedListener {
            deleteItem(position)
        }
        dialog.show(supportFragmentManager, "ItemDialog")
    }

    private fun editItem(position: Int, editedItem: String) {
        adapter.getItem(position)?.let {
            adapter.remove(it)
            adapter.insert(editedItem, position)
        }
    }

    private fun deleteItem(position: Int) {
        adapter.remove(adapter.getItem(position))
    }
}
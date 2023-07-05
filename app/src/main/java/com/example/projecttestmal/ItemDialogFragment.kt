package com.example.projecttestmal

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class ItemDialogFragment : DialogFragment() {

    private var item: String? = null
    private var onItemEditedListener: ((String) -> Unit)? = null
    private var onItemDeletedListener: (() -> Unit)? = null

    private var editText: EditText? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context: Context = requireContext()

        val alertDialogBuilder = AlertDialog.Builder(context)
            .setTitle("Редактирование элемента")
            .setMessage("Введите новое значение:")
            .setPositiveButton("Сохранить") { _, _ ->
                val editedItem = editText?.text.toString()
                onItemEditedListener?.invoke(editedItem)
            }
            .setNegativeButton("Удалить") { _, _ ->
                onItemDeletedListener?.invoke()
            }
            .setNeutralButton("Отмена", null)

        editText = EditText(context)
        editText?.setText(item)
        alertDialogBuilder.setView(editText)

        return alertDialogBuilder.create()
    }

    companion object {
        fun newInstance(item: String): ItemDialogFragment {
            val fragment = ItemDialogFragment()
            fragment.item = item
            return fragment
        }
    }

    fun setOnItemEditedListener(listener: (String) -> Unit) {
        onItemEditedListener = listener
    }

    fun setOnItemDeletedListener(listener: () -> Unit) {
        onItemDeletedListener = listener
    }
}
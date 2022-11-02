package com.arash.mvinote.utils

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import com.arash.mvinote.adapter.SpinnerAdapter
import com.arash.mvinote.data.model.ModelSpinner

fun Spinner.setupListWithAdapter(list: MutableList<ModelSpinner>, callback: (String) -> Unit) {
    val adapter = SpinnerAdapter(context, list)
    this.adapter = adapter
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            callback(list[position].spinnerItemText!!)
        }
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }
    }
}

fun Array<out Any>.getIndexFromList(list: Array<String>, item: String): Int {
    var index = 0
    for (i in this.indices) {
        if (this[i] == item) {
            index = i
            break
        }
    }
    return index
}
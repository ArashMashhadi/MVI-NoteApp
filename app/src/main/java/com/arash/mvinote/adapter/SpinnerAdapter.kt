package com.arash.mvinote.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.arash.mvinote.R
import com.arash.mvinote.data.model.ModelSpinner

class SpinnerAdapter(
    private val ctx: Context, private val customList: MutableList<ModelSpinner>,
) : ArrayAdapter<ModelSpinner>(ctx, 0, customList as List<ModelSpinner>) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, parent)
    }

    @SuppressLint("ResourceAsColor")
    private fun getCustomView(position: Int, parent: ViewGroup?): View {
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val row: View = inflater.inflate(R.layout.spinner_custom, parent, false)
        val textView = row.findViewById(R.id.txt) as TextView
        textView.text = customList[position].spinnerItemText
        val imageView: ImageView = row.findViewById(R.id.img) as ImageView
        imageView.setImageResource(customList[position].SpinnerItemImage)
        return row
    }
}

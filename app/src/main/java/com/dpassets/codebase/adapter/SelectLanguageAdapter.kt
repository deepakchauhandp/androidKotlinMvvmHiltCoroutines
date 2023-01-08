package com.dpassets.codebase.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dpassets.codebase.R
import com.dpassets.codebase.callbacks.RecyclerViewClickListener
import com.dpassets.codebase.databinding.ItemSelectLanguageBinding
import com.dpassets.codebase.data.models.language.LanguageModel

class SelectLanguageAdapter( var list: ArrayList<LanguageModel>, var listener: RecyclerViewClickListener) :
        RecyclerView.Adapter<SelectLanguageAdapter.MyViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val binding = DataBindingUtil.inflate<ItemSelectLanguageBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_select_language,
                parent,
                false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.model = list[position]
        holder.binding.llMain.setOnClickListener { view ->
            listener.onClick(view, position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class MyViewHolder(val binding: ItemSelectLanguageBinding) : RecyclerView.ViewHolder(binding.root) {
    }
}
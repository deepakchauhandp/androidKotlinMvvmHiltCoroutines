package com.dpassets.codebase.callbacks

import android.view.View

interface RecyclerViewClickListener {
    fun onClick(v: View?, position: Int)
    fun onLongClick(v: View?, position: Int)
}
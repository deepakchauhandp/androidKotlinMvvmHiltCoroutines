package com.dpassets.codebase.utills

import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.dpassets.codebase.R
import com.google.android.material.snackbar.Snackbar

class SnackBarUtil {
    companion object {

        fun showSnackBar(view : View, message : String) {
            val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            snackBar.show()
        }

        fun showErrorSnackBar(view : View, message : String) {
            val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            snackBar.view.setBackgroundColor(ResourcesCompat.getColor(view.resources, R.color.text_color_red_0000, null))
            snackBar.show()
        }

        fun showSuccessSnackBar(view : View, message : String) {
            val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            snackBar.view.setBackgroundColor(ResourcesCompat.getColor(view.resources, R.color.text_color_green_17, null))
            snackBar.show()
        }
    }
}
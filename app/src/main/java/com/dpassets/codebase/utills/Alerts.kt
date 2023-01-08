package com.dpassets.codebase.utills

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.dpassets.codebase.adapter.SelectLanguageAdapter
import com.dpassets.codebase.callbacks.RecyclerViewClickListener
import com.dpassets.codebase.data.models.language.LanguageModel
import com.dpassets.codebase.R
import com.dpassets.codebase.databinding.DialogPickImageBinding
import com.dpassets.codebase.databinding.DialogSelectLanguageBinding
import java.util.*

class Alerts {

    companion object {

        fun selectLanguageDialog(context: Context, callback: LanguageCodeCallback) {
            val dialog = BottomSheetDialog(context, R.style.SheetDialog)
            val binding = DataBindingUtil.inflate<DialogSelectLanguageBinding>(
                LayoutInflater.from(context),
                R.layout.dialog_select_language, null, false
            )
            dialog.setContentView(binding.root)
            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(true)
            dialog.window?.setGravity(Gravity.BOTTOM)
            dialog.window?.attributes?.windowAnimations = R.style.DialogAnimationBottom
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val list: ArrayList<LanguageModel> = ArrayList()
            for (i in 0 until context.resources.getStringArray(R.array.languageArray).size) {
                list.add(
                    LanguageModel(
                        context.resources.getStringArray(R.array.languageArray)[i],
                        context.resources.getStringArray(R.array.languageCodeArray)[i])
                )
            }
            val selectLanguageAdapter = SelectLanguageAdapter(list, object : RecyclerViewClickListener {
                override fun onClick(v: View?, position: Int) {
                    callback.action(list[position].languageCode)
                    dialog.dismiss()
                }

                override fun onLongClick(v: View?, position: Int) {}
            })
            binding.adapter = selectLanguageAdapter
            dialog.show()
        }

        fun pickImageDialog(context: Context, callback: AlertMultipleCallback) {
            val dialog = Dialog(context)
            val binding = DataBindingUtil.inflate<DialogPickImageBinding>(
                LayoutInflater.from(context),
                R.layout.dialog_pick_image,
                null,
                false
            )
            dialog.setContentView(binding.root)
            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(true)
            dialog.window?.setGravity(Gravity.BOTTOM)
            dialog.window?.attributes?.windowAnimations = R.style.DialogAnimationBottom
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            binding.tvCaptureImage.setOnClickListener {
                callback.action(1)
                dialog.dismiss()

            }
            binding.tvGallery.setOnClickListener {
                callback.action(2)
                dialog.dismiss()
            }
        }
    }


    interface AlertCallback {
        fun action(action: Boolean)
    }

    interface AlertMultipleCallback {
        fun action(action: Int)
    }

    interface LanguageCodeCallback {
        fun action(languageCode: String)
    }

}


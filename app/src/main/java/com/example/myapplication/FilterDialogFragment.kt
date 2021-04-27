package com.example.myapplication

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment

class FilterDialogFragment() : DialogFragment() {
    var booleanTags = booleanArrayOf(true, true, true, true)
    private val parentListener by lazy {
        activity as? FragmentOnClickListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val listTags = arrayOf("Features", "Developer news", "Challenge week", "Other")
        booleanTags = requireArguments().getBooleanArray(KEY_NEWS) ?: error("Boolean array is null")
        return AlertDialog.Builder(requireContext())
            .setTitle("Choose tags")
            .setMultiChoiceItems(listTags, booleanTags) { _, which, isChecked ->
                if (isChecked) {
                    AppActivity.toast(requireContext(), "You choose ${listTags[which]}")
                } else {
                    AppActivity.toast(requireContext(), "You cancel ${listTags[which]}")
                }
            }
            .setPositiveButton("Filter") { _, which ->
                Log.d(
                    "CustomTAF",
                    "сейчас лист такой во фрагменте: ${booleanTags[0]},${booleanTags[1]},${booleanTags[2]},${booleanTags[3]}"
                )
                parentListener?.onFragmentClick(booleanTags)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()

    }

    companion object {
        private const val KEY_NEWS = "news"
        fun newInstance(
            booleanList: BooleanArray
        ): FilterDialogFragment {
            return FilterDialogFragment().withArguments {
                putBooleanArray(KEY_NEWS, booleanList)
            }
        }
    }
}

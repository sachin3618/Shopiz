package com.example.shopiz.ui.fragments

import android.app.Dialog
import androidx.fragment.app.Fragment
import com.example.shopiz.R
import kotlinx.android.synthetic.main.dialog_progress.*

/**
 * A base fragment class is used to define the functions and members which we will use in all the fragments.
 * It inherits the Fragment class so in other fragment class we will replace the Fragment with BaseFragment.
 */
open class BaseFragment : Fragment() {

    /**
     * This is a progress dialog instance which we will initialize later on.
     */
    private lateinit var mProgressDialog: Dialog
    // END

    /**
     * This function is used to show the progress dialog with the title and message to user.
     */
    fun showProgressDialog(text: String) {
        mProgressDialog = Dialog(requireActivity())

        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        mProgressDialog.setContentView(R.layout.dialog_progress)

        mProgressDialog.tv_progress_text.text = text

        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)

        //Start the dialog and display it on screen.
        mProgressDialog.show()
    }

    /**
     * This function is used to dismiss the progress dialog if it is visible to user.
     */
    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }
}
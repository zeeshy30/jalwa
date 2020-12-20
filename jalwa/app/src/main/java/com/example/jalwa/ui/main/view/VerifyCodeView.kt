package com.example.jalwa.ui.main.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.example.jalwa.R
import com.example.jalwa.ui.main.viewmodel.LoginSignupViewModel
import org.koin.android.ext.android.inject

class VerifyCodeView : Fragment() {
    val viewModel: LoginSignupViewModel by inject()
    private lateinit var proceedButton: AppCompatButton

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.verify_account_page, container, false)
    }

    private fun findViews(view: View) {
        proceedButton = view.findViewById(R.id.proceedButton)
    }

    private fun setListeners(view: View) {
//        proceedButton.setOnClickListener {
//            viewModel.requestCode("+92${phoneNumber.text}")
//            viewModel.LoginSignupObservable.observe(viewLifecycleOwner, { isError ->
//                if(isError.value != true) {
//
//                }
//            })
//        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findViews(view)
        setListeners(view)
    }

}
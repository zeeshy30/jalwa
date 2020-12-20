package com.example.jalwa.ui.main.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.jalwa.R
import com.example.jalwa.ui.main.viewmodel.LoginSignupViewModel
import org.koin.android.ext.android.inject

class LoginSingupView : Fragment() {
    val viewModel: LoginSignupViewModel by inject()
    private lateinit var phoneNumber: EditText
    private lateinit var backButton: AppCompatButton
    private lateinit var proceedButton: AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_signup_page, container, false)
    }

    private fun findViews(view: View) {
        backButton = view.findViewById(R.id.backButton)
        phoneNumber = view.findViewById(R.id.number)
        proceedButton = view.findViewById(R.id.proceedButton)
    }

    private fun setListeners(view: View) {
        backButton.setOnClickListener {
            (requireContext() as Activity).onBackPressed()
        }
        proceedButton.setOnClickListener {
            viewModel.requestCode("+92${phoneNumber.text}")
            viewModel.loginSignupObservable.observe(viewLifecycleOwner, { isError ->
                if(isError.value != true) {
                    findNavController().navigate(R.id.action_verify_number, Bundle())
                }
            })
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findViews(view)
        setListeners(view)
    }

}
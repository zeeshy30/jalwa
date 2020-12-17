package com.example.jalwa.ui.main.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.example.jalwa.R
import com.google.android.material.textfield.TextInputLayout

class LoginSingupView : Fragment() {
    private lateinit var phoneNumber: EditText
    private lateinit var backButton: AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.login_signup_page, container, false)
    }

    private fun findViews(view: View) {
        backButton = view.findViewById(R.id.backButton)
        phoneNumber = view.findViewById(R.id.number)
    }

    private fun setListeners(view: View) {
        backButton.setOnClickListener {
            (requireContext() as Activity).onBackPressed()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findViews(view)
        setListeners(view)
    }

}
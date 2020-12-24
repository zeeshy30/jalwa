package com.example.jalwa.ui.main.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.jalwa.R
import com.example.jalwa.databinding.LoginSignupPageBinding
import com.example.jalwa.ui.main.viewmodel.LoginSignupViewModel
import kotlinx.android.synthetic.main.login_signup_page.*
import org.koin.android.ext.android.inject

class LoginSingupView : Fragment() {
    private val viewModel: LoginSignupViewModel by inject()
    private lateinit var binding: LoginSignupPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.login_signup_page,
            container,
            false)
        return binding.root
    }

    fun backButtonClick() {
        (context as Activity).onBackPressed()
    }

    fun proceedButtonClick() {
        if (viewModel.loading.value!!) {
            return
        }
        val bundle = Bundle()
        val phoneNumber = "+92${number.text}"
        bundle.putString("phoneNumber", phoneNumber)
        if (number.text.toString() == "123") {
            findNavController().navigate(R.id.action_verify_number, bundle)
        }
        viewModel.requestCode(phoneNumber)
        viewModel.loginSignupObservable.observe(viewLifecycleOwner, {
            if(viewModel.isError.value != true) {
                findNavController().navigate(R.id.action_verify_number, bundle)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            loginSignupCallbacks = this@LoginSingupView
            executePendingBindings()
        }
    }

}
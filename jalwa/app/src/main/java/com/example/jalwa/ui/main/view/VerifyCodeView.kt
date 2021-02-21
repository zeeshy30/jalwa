package com.example.jalwa.ui.main.view

import android.app.Activity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.jalwa.R
import com.example.jalwa.databinding.VerifyAccountPageBinding
import com.example.jalwa.ui.main.viewmodel.LoginSignupViewModel
import kotlinx.android.synthetic.main.verify_account_page.*
import org.koin.android.ext.android.inject

class VerifyCodeView : Fragment() {
    val viewModel: LoginSignupViewModel by inject()
    private lateinit var binding: VerifyAccountPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.verify_account_page,
            container,
            false
        )
        return binding.root
    }

    private fun setListeners() {
        firstDigit.doOnTextChanged { _, _, _, count ->
            if (count == 1)
                secondDigit.requestFocus()
        }
        secondDigit.doOnTextChanged { _, _, _, count ->
            if (count == 1)
                thirdDigit.requestFocus()
        }
        thirdDigit.doOnTextChanged { _, _, _, count ->
            if (count == 1)
                fourthDigit.requestFocus()
        }
    }

    fun backButtonClick() {
        (context as Activity).onBackPressed()
    }

    fun proceedButtonClick() {
        print("loginnn")
    }

    private fun startTimer() {
        val countdownTimer = object : CountDownTimer(60000, 1000) {
            override fun onFinish() {
            }

            override fun onTick(p0: Long) {
                val timeLeft = (p0 / 1000).toString()
                updateTime(timeLeft)
            }
        }
        countdownTimer.start()
    }

    private fun updateTime(timeLeft: String) {
        binding.time = "Resend Code : ${timeLeft}s"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        binding.apply {
            time = "Resend Code : 60s"
            phoneNumber = arguments?.getString("phoneNumber")
            verifyCodeViewCallbacks = this@VerifyCodeView
            executePendingBindings()
        }
        startTimer()
    }
}
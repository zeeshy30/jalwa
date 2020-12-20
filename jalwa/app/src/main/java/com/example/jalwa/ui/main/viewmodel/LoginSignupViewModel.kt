package com.example.jalwa.ui.main.viewmodel

import io.reactivex.rxjava3.core.Notification
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.jalwa.data.api.ApolloClientManager
import com.example.jalwa.data.api.suspendQuery
import com.example.jalwa.ProductsQuery
import com.example.jalwa.RequestCodeMutation
import com.example.jalwa.data.api.suspendMutate
import kotlinx.coroutines.launch

class LoginSignupViewModel: ViewModel() {
    val loading = MutableLiveData(true)
    val isError = MutableLiveData(false)
    val loginSignupObservable: MutableLiveData<Notification<Boolean?>> = MutableLiveData()

    fun requestCode(phoneNumber: String){
        viewModelScope.launch {
            try {
                val req = RequestCodeMutation(phoneNumber)
                val data = ApolloClientManager
                    .apolloClient
                    .suspendMutate(req)
                    .data!!
                data.requestCode
            }
            catch (e: Exception) {
                isError.value = true
            }
            finally {
                loading.value = false
                loginSignupObservable.postValue(Notification.createOnNext(isError.value))
            }
        }
    }
}
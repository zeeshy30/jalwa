package com.example.jalwa.ui.main.viewmodel

import io.reactivex.rxjava3.core.Notification
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.jalwa.data.api.ApolloClientManager
import com.example.jalwa.RequestCodeMutation
import com.example.jalwa.data.api.suspendMutate
import kotlinx.coroutines.launch

class LoginSignupViewModel: ViewModel() {
    val loading = MutableLiveData(false)
    val isError = MutableLiveData(false)
    val loginSignupObservable: MutableLiveData<Notification<RequestCodeMutation.Data>> = MutableLiveData()

    fun requestCode(phoneNumber: String){
        loading.value = true
        isError.value = false
        viewModelScope.launch {
            try {
                val req = RequestCodeMutation(phoneNumber)
                val data: RequestCodeMutation.Data = ApolloClientManager
                    .apolloClient
                    .suspendMutate(req)
                    .data!!
                loginSignupObservable.postValue(Notification.createOnNext(data))
            }
            catch (e: Exception) {
                isError.value = true
                loginSignupObservable.postValue(Notification.createOnError(e))
            }
            finally {
                loading.value = false
            }
        }
    }
}
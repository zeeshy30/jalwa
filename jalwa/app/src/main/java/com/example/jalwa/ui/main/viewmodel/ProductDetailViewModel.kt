package com.example.jalwa.ui.main.viewmodel

import io.reactivex.rxjava3.core.Notification
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.jalwa.data.api.ApolloClientManager
import com.example.jalwa.GetProductSKUsQuery
import com.example.jalwa.data.api.suspendQuery
import kotlinx.coroutines.launch

class ProductDetailViewModel: ViewModel() {
    val loading = MutableLiveData(false)
    val isError = MutableLiveData(false)
    val productSKUsObservable: MutableLiveData<Notification<GetProductSKUsQuery.Data>> = MutableLiveData()

    fun getProductDetails(handle: String){
        loading.value = true
        viewModelScope.launch {
            try {
                val req = GetProductSKUsQuery(handle)
                val data: GetProductSKUsQuery.Data = ApolloClientManager
                    .apolloClient
                    .suspendQuery(req)
                    .data!!
                productSKUsObservable.postValue(Notification.createOnNext(data))
            }
            catch (e: Exception) {
                isError.value = true
            }
            finally {
                loading.value = false
            }
        }
    }
}
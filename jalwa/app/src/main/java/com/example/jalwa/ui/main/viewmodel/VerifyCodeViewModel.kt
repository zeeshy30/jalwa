package com.example.jalwa.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Notification

class VerifyCodeViewModel : ViewModel() {
    val loading = MutableLiveData(true)
    val isError = MutableLiveData<Boolean>()
    var list: ArrayList<Any> = arrayListOf()
    val productsObservable: MutableLiveData<Notification<ArrayList<Any>>> = MutableLiveData()
//
//    init {
//        viewModelScope.launch {
//            try {
//                val viewerInfo: ProductsQuery.Data = ApolloClientManager
//                        .apolloClient
//                        .suspendQuery(ProductsQuery())
//                        .data!!
//                viewerInfo.products?.let { list.addAll(it) }
//                productsObservable.postValue(Notification.createOnNext(list))
//            }
//            catch (e: Exception) {
//                isError.value = true
//            }
//            finally {
//                loading.value = false
//            }
//        }
//    }
}
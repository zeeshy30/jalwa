package com.example.jalwa.ui.main.viewmodel

import io.reactivex.rxjava3.core.Notification
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.jalwa.data.api.ApolloClientManager
import com.example.jalwa.data.api.suspendQuery
import com.example.jalwa.ProductsQuery
import kotlinx.coroutines.launch

class ProductViewModel: ViewModel() {
    val loading = MutableLiveData(true)
    val isError = MutableLiveData<Boolean>()
    var list: ArrayList<Any> = arrayListOf()
    val productsObservable: MutableLiveData<Notification<ArrayList<Any>>> = MutableLiveData()

    init {
        viewModelScope.launch {
            try {
                val viewerInfo: ProductsQuery.Data = ApolloClientManager
                    .apolloClient
                    .suspendQuery(ProductsQuery())
                    .data!!
                viewerInfo.products?.let { list.addAll(it) }
                productsObservable.postValue(Notification.createOnNext(list))
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
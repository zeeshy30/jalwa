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
    val productSKUs: ArrayList<GetProductSKUsQuery.ProductSKU?> = arrayListOf()
    val productSKUsObservable: MutableLiveData<Notification<ArrayList<GetProductSKUsQuery.ProductSKU?>>> = MutableLiveData()

    fun getProductDetails(handle: String){
        loading.value = true
        viewModelScope.launch {
            try {
                val req = GetProductSKUsQuery(handle)
                val data: GetProductSKUsQuery.Data = ApolloClientManager
                    .apolloClient
                    .suspendQuery(req)
                    .data!!
                if (data.productSKUs.__typename == "Error") {
                    isError.value = true
                    val message = data.productSKUs.asError?.message
                    productSKUsObservable.postValue(Notification.createOnError(Throwable(message)))
                }
                data.productSKUs.asProductSKUs?.productSKUs?.let { productSKUs.addAll(it) }
                productSKUsObservable.postValue(Notification.createOnNext(productSKUs))
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
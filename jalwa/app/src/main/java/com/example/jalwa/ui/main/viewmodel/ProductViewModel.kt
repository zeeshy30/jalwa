package com.example.jalwa.ui.main.viewmodel

import io.reactivex.rxjava3.core.Notification
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.jalwa.CategoriesQuery
import com.example.jalwa.ProductsFilteredByCategoryQuery
import com.example.jalwa.data.api.ApolloClientManager
import com.example.jalwa.data.api.suspendQuery
import kotlinx.coroutines.launch

class ProductViewModel: ViewModel() {
    val loading = MutableLiveData(true)
    val isError = MutableLiveData<Boolean>()
    val productList: ArrayList<Any> = arrayListOf()
    val categoryList: ArrayList<Any> = arrayListOf()
    val productsObservable: MutableLiveData<Notification<ArrayList<Any>>> = MutableLiveData()
    val categoriesObservable: MutableLiveData<Notification<ArrayList<Any>>> = MutableLiveData()
    var selectedCategory = 0
    init {
        val category = CategoriesQuery.Category(category = "All")
        categoryList.add(category)
        viewModelScope.launch {
            try {
                val filteredProducts: ProductsFilteredByCategoryQuery.Data = ApolloClientManager
                    .apolloClient
                    .suspendQuery(ProductsFilteredByCategoryQuery("All"))
                    .data!!
                filteredProducts.productsFilteredByCategory?.let { productList.addAll(it) }
                productsObservable.postValue(Notification.createOnNext(productList))

                val categoriesQueryData: CategoriesQuery.Data = ApolloClientManager
                    .apolloClient
                    .suspendQuery(CategoriesQuery())
                    .data!!
                categoriesQueryData.categories?.let { categoryList.addAll(it) }
                categoriesObservable.postValue(Notification.createOnNext(categoryList))
            }
            catch (e: Exception) {
                isError.value = true
            }
            finally {
                loading.value = false
            }
        }
    }

    fun getProductsFilteredByCategory(category: String, index: Int) {
        selectedCategory = index
        viewModelScope.launch {
            try {
                loading.value = true
                val filteredProducts: ProductsFilteredByCategoryQuery.Data = ApolloClientManager
                    .apolloClient
                    .suspendQuery(ProductsFilteredByCategoryQuery(category))
                    .data!!
                productList.clear()
                filteredProducts.productsFilteredByCategory?.let { productList.addAll(it) }
                productsObservable.postValue(Notification.createOnNext(productList))
            } catch (e: Exception) {
                isError.value = true
            } finally {
                loading.value = false
            }
        }
    }
}
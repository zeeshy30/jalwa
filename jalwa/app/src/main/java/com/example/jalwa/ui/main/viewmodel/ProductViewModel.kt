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
    val isErrorFetchingProducts = MutableLiveData<Boolean>()
    val isErrorFetchingCategories = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val productList: ArrayList<ProductsFilteredByCategoryQuery.Product?> = arrayListOf()
    val categoryList: ArrayList<CategoriesQuery.Category?> = arrayListOf()
    val productsObservable:
            MutableLiveData<Notification<ArrayList<ProductsFilteredByCategoryQuery.Product?>>> = MutableLiveData()
    val categoriesObservable: MutableLiveData<Notification<ArrayList<CategoriesQuery.Category?>>> = MutableLiveData()
    var selectedCategory = 0
    init {
        val category = CategoriesQuery.Category(category = "All")
        categoryList.add(category)
        getProductsFilteredByCategory("All", 0)
        viewModelScope.launch {
            try {
                val categoriesQueryData: CategoriesQuery.Data = ApolloClientManager
                    .apolloClient
                    .suspendQuery(CategoriesQuery())
                    .data!!
                if (categoriesQueryData.categories?.__typename == "Error") {
                    isErrorFetchingCategories.value = true
                    val message = categoriesQueryData.categories.asError?.message
                    categoriesObservable.postValue(Notification.createOnError(Throwable(message)))
                } else {
                    categoriesQueryData.categories.asCategories?.categories.let {
                        if (it != null) {
                            categoryList.addAll(it)
                        }
                        categoriesObservable.postValue(Notification.createOnNext(categoryList))
                    }
                }
            }
            catch (e: Exception) {
                isErrorFetchingCategories.value = true
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
                if (filteredProducts.productsFilteredByCategory?.__typename == "Error") {
                    isErrorFetchingProducts.value = true
                    val message = filteredProducts.productsFilteredByCategory.asError?.message
                    productsObservable.postValue(Notification.createOnError(Throwable(message)))
                } else {
                    filteredProducts.productsFilteredByCategory.asProducts?.products.let {
                        if (it != null) {
                            productList.addAll(it)
                        }
                        productsObservable.postValue(Notification.createOnNext(productList))
                    }
                }
            } catch (e: Exception) {
                isErrorFetchingProducts.value = true
            } finally {
                loading.value = false
            }
        }
    }
}
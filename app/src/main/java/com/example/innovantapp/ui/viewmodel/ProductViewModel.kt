package com.example.innovantapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innovantapp.data.model.Product
import com.example.innovantapp.data.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val repository = ProductRepository()

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product
    val isDetailsVisible = MutableLiveData(true)

    init {
        viewModelScope.launch {
            try {
                val response = repository.getProduct()
                _product.value = response.data
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error: ${e.message}")
            }
        }
    }

    fun toggleDetails() {
        isDetailsVisible.value = !(isDetailsVisible.value ?: false)
    }

    fun extractColorsFromDescription(description: String): List<String> {
        val regex = Regex("""Color:\s*(.*?)</p>""", RegexOption.IGNORE_CASE)
        val match = regex.find(description)
        return match?.groups?.get(1)?.value
            ?.split(",")
            ?.map { it.trim() }
            ?: emptyList()
    }

}

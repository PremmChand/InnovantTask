package com.example.innovantapp.data.repository

import android.util.Log
import com.example.innovantapp.data.api.RetrofitInstance.api
import com.example.innovantapp.data.model.ProductResponse

class ProductRepository {

    suspend fun getProduct(): ProductResponse {
        val response = api.getProduct() // This returns ProductResponse


       // Log.d("API_DATA", response.toString())
        response.data.name?.let {
           // Log.d("PRODUCT_NAME", it)
        }

        return response // ✅ Return the whole ProductResponse object
    }
}

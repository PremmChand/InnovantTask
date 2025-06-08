package com.example.innovantapp.data.model

data class Product(
    val id: String = "",
    val sku: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val description: String = "",
    val image: String = "",
    val brand_name: String = "",
    val final_price: String = "",
    val web_url: String = "",
    val configurable_option: List<ConfigurableOption> = emptyList(),

    // ✅ Add this line to include all images
    val media_gallery: List<MediaGalleryItem> = emptyList()
)

data class ConfigurableOption(
    val attribute_id: Int = 0,
    val type: String = "",
    val attribute_code: String = "",
    val attributes: List<Attribute> = emptyList()
)

data class Attribute(
    val value: String = "",
    val option_id: String = "",
    val price: String = "",
    val images: List<String> = emptyList()
)

// ✅ New data class to hold image paths
data class MediaGalleryItem(
    val file: String = ""
)

data class ColorImage(
    val name: String,
    val imageUrl: String
)


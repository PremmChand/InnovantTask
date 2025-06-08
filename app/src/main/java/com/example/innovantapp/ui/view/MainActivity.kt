package com.example.innovantapp.ui.view

import ColorAdapter
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.example.innovantapp.R
import com.example.innovantapp.databinding.ActivityMainBinding
import com.example.innovantapp.ui.viewmodel.ProductViewModel
import com.example.innovantapp.utils.ImagePagerAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.innovantapp.data.model.ColorImage

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ProductViewModel by viewModels()
    private var quantity = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.product.observe(this) { product ->

            val baseUrl = "https://cdn.klinq.com"
            var imageList = product.media_gallery.map { "$baseUrl${it.file}" }
                .filter { it.endsWith(".jpg") || it.endsWith(".png") || it.endsWith(".jpeg") }

            if (imageList.isEmpty()) {
                imageList = product.configurable_option
                    .flatMap { it.attributes }
                    .flatMap { it.images }
                    .filter { it.endsWith(".jpg") || it.endsWith(".png") || it.endsWith(".jpeg") }
            }


            if (imageList.isNotEmpty()) {
                val adapter = ImagePagerAdapter(imageList)
                binding.viewPager.adapter = adapter

                addDots(imageList.size)
                highlightDot(0)


                val colorsFromApi = viewModel.extractColorsFromDescription(product.description ?: "")
                val imagesFromApi = product.configurable_option
                    .flatMap { it.attributes }
                    .flatMap { it.images }
                    .filter { it.endsWith(".jpg") || it.endsWith(".png") || it.endsWith(".jpeg") }

                val colorImages = colorsFromApi.zip(imagesFromApi) { name, url ->
                    val finalUrl = if (url.startsWith("http")) url else "$baseUrl$url"
                    ColorImage(name, finalUrl)
                }.toMutableList()

                val colorAdapter = ColorAdapter(colorImages) { selectedIndex ->
                    binding.viewPager.currentItem = selectedIndex
                }
                //Above new added

                binding.colorRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                binding.colorRecyclerView.adapter = colorAdapter

                //new code

                binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                      highlightDot(position)
                        val currentImageUrl = imageList.getOrNull(position) ?: ""
                        val updatedList = colorsFromApi.map { colorName ->
                            ColorImage(name = colorName, imageUrl = currentImageUrl)
                        }

                        (binding.colorRecyclerView.adapter as? ColorAdapter)?.apply {
                            updateList(updatedList)
                            setSelectedPosition(position)
                        }

                        binding.colorRecyclerView.scrollToPosition(position)

                    }
                })
            } else {
                Log.e("MainActivity", "Image list is empty")
            }

        }



        val textView = binding.paymentAmount
        val boldText = "0.88 KWD"
        val linkText = " Learn more"
        val fullText = boldText + linkText

        val spannable = SpannableString(fullText)
        spannable.setSpan(
            StyleSpan(Typeface.BOLD),
            0,
            boldText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            UnderlineSpan(),
            boldText.length,
            fullText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textView.text = spannable


    //Increment and decrement button
        binding.incrementButton.setOnClickListener{
            quantity++
            updateQuantity()
        }
        binding.decrementButton.setOnClickListener{
            if (quantity > 1) {
                quantity--
                updateQuantity()
            }
        }

        binding.AddToBagBtn.setOnClickListener{
            Toast.makeText(this, "Added $quantity item(s) to bag", Toast.LENGTH_SHORT).show()
            Log.d("Cart", "Product ID: 6701, Quantity: $quantity")
        }

    }

    private fun updateQuantity() {
       binding.quantityValue.text = quantity.toString()
    }


    private fun addDots(count: Int) {
        val layout = binding.dotsContainer
        layout.removeAllViews()

        val sizeInDp = 12
        val marginInDp = 2
        val density = resources.displayMetrics.density
        val sizeInPx = (sizeInDp * density).toInt()
        val marginInPx = (marginInDp * density).toInt()

        repeat(count) {
            val dot = ImageView(this).apply {
                layoutParams = LinearLayout.LayoutParams(sizeInPx, sizeInPx).apply {
                    setMargins(marginInPx, 0, marginInPx, 0)
                }
                setImageResource(R.drawable.dot_inactive)
            }
            layout.addView(dot)
        }
    }

    private fun highlightDot(position: Int) {
        val layout = binding.dotsContainer
        for (i in 0 until layout.childCount) {
            val dot = layout.getChildAt(i) as ImageView
            if (i == position) {
                dot.setImageResource(R.drawable.dot_active)
            } else {
                dot.setImageResource(R.drawable.dot_inactive)
            }
        }
    }
}

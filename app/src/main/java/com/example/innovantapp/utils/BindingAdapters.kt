package com.example.innovantapp.utils

import android.os.Build
import android.text.Html
import android.text.Spanned
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("htmlText")
fun setHtmlText(textView: TextView, html: String?) {
    if (html == null) {
        textView.text = ""
        return
    }
    val spanned: Spanned = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(html)
    }
    textView.text = spanned
}

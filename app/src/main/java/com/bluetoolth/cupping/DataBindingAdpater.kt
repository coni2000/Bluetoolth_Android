package com.bluetoolth.cupping

import android.webkit.WebView
import androidx.databinding.BindingAdapter

/**
 * Created by KimBH on 2023/02/20.
 */
object DataBindingAdpater {

    @JvmStatic
    @BindingAdapter("loadUrl")
    fun loadUrl(webView: WebView, url: String) {
        webView.loadUrl(url)
    }
}
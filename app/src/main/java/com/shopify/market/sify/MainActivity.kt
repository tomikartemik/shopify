package com.shopify.market.sify

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var webView : WebView
    val URL = "https://shopifyle.site"
    val userStr = "Mozilla/5.0 (Linux; Android 12; SM-G991U) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Mobile Safari/537.36"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        webView = findViewById(R.id.web)
        val refresh : ImageView = findViewById(R.id.imageView)
        val text : TextView = findViewById(R.id.textView)

        if (checkForInternet(this)) {
            webView.visibility = View.VISIBLE
            refresh.visibility = View.GONE
            text.visibility = View.GONE
            open_wv()
        } else {
            webView.visibility = View.GONE
            refresh.visibility = View.VISIBLE
            text.visibility = View.VISIBLE
            refresh.setOnClickListener{

                if (checkForInternet(this)) {
                    webView.visibility = View.VISIBLE
                    refresh.visibility = View.GONE
                    text.visibility = View.GONE
                    open_wv()
                }
            }
        }

    }

    private fun checkForInternet(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

    override fun onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack()
        }else{
            super.onBackPressed()
        }
    }
    private fun open_wv(){
        webView.apply {
            settings.apply {
                javaScriptEnabled = true
                userAgentString = userStr
            }
        }
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView? , url: String?): Boolean {
                view?.loadUrl(URL)
                return true
            }
        }
        webView.loadUrl(URL)
    }
}
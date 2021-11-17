package com.ua.coffeeboom

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private val url = "https://coffee-boom.com.ua/"
    private lateinit var webView: WebView

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        webView = findViewById(R.id.myWebView)

        // Get the web view settings instance
        val settings = webView.settings
        // Enable java script in web view
        settings.javaScriptEnabled = true
        // Enable and setup web view cache
        settings.cacheMode = WebSettings.LOAD_DEFAULT


        // Enable zooming in web view
        settings.setSupportZoom(false)
        settings.builtInZoomControls = false
        settings.displayZoomControls = false

        // Zoom web view text
        settings.textZoom = 100

        // Enable disable images in web view
        settings.blockNetworkImage = false
        // Whether the WebView should load image resources
        settings.loadsImagesAutomatically = true


        // More web view settings
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            settings.safeBrowsingEnabled = true  // api 26
        }
        //settings.pluginState = WebSettings.PluginState.ON
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.mediaPlaybackRequiresUserGesture = false


        // More optional settings, you can enable it by yourself
        settings.domStorageEnabled = true
        settings.setSupportMultipleWindows(true)
        settings.loadWithOverviewMode = true
        settings.allowContentAccess = true
        settings.setGeolocationEnabled(true)
        settings.allowFileAccess = true

        // WebView settings
        webView.fitsSystemWindows = true


        //Cookies enable
        CookieManager.allowFileSchemeCookies()
        CookieManager.getInstance().acceptCookie()

        /*
            if SDK version is greater of 19 then activate hardware acceleration
            otherwise activate software acceleration
        */
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)


        // Set web view client
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null) {
                    view?.loadUrl(url)
                }
                return true
            }
        }
        webView.loadUrl(url);


        fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            if (url.contains("coffee-boom.com.ua")) {
                view.loadUrl(url)
            } else {
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(i)
            }
            return true
        }
    }


    // Method to show app exit dialog
    private fun showAppExitDialog() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Please confirm")
        builder.setMessage("No back history found, want to exit the app?")
        builder.setCancelable(true)

        builder.setPositiveButton("Yes") { _, _ ->

            super@MainActivity.onBackPressed()
        }

        builder.setNegativeButton("No") { _, _ ->
            // Do something when want to stay in the app
            toast("thank you.")
        }

        // Create the alert dialog using alert dialog builder
        val dialog = builder.create()

        // Finally, display the dialog when user press back button
        dialog.show()
    }

}


fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
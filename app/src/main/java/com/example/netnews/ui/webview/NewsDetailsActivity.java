package com.example.netnews.ui.webview;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.example.netnews.R;
import com.example.netnews.databinding.ActivityNewsDetailsBinding;

public class NewsDetailsActivity extends AppCompatActivity {

    private ActivityNewsDetailsBinding binding;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String linkURL = getIntent().getStringExtra("link");
        binding = DataBindingUtil.setContentView(this,R.layout.activity_news_details);
        webView = binding.webView;
        webView.loadUrl(linkURL);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
    }
}
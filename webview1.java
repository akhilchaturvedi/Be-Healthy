package com.example.behealthy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class webview1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview1);
        WebView wv1=findViewById(R.id.wv1);
        Intent intent=getIntent();
        String url=intent.getStringExtra("url1");
        wv1.loadUrl(url);
    }
}

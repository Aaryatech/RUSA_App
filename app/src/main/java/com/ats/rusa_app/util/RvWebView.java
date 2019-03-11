package com.ats.rusa_app.util;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class RvWebView extends WebView {

    public RvWebView(Context context) {
        super(context);
        initDefaultSetting();
    }

    public RvWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDefaultSetting();
    }

    public RvWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefaultSetting();
    }

    public RvWebView(Context context, AttributeSet attrs, int defStyleAttr,
                     int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initDefaultSetting();
    }


    private void initDefaultSetting() {
        WebSettings webSettings = this.getSettings();
        webSettings.setJavaScriptEnabled(true);
        setWebChromeClient(new WebChromeClient());
        setWebViewClient(new RvWebViewClient());
    }

    /**
     * Load Web View with url
     */
    public void load(String url) {
        this.loadUrl(url);
    }


}


class RvWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        view.loadUrl(request.getUrl().toString());
        return true;

    }
}
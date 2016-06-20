package nl.enterbv.easion.Activities;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import nl.enterbv.easion.R;

/**
 * Created by joepv on 17.jun.2016.
 */

public class EnqueteWebViewActivity extends AppCompatActivity {


    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_surveywebview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_wv_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        String url = "" + getIntent().getStringExtra("URL");
        Log.e("testTag16", url);
        WebView surveyWebView = (WebView) findViewById(R.id.wv_survey);
        surveyWebView.clearHistory();
        surveyWebView.clearCache(true);
        clearCookies(this);
        surveyWebView.getSettings().setJavaScriptEnabled(true);
        if (!url.equals("")) {
            surveyWebView.loadUrl(url);

        }


        surveyWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //TODO fetch data from server to check for changes


                return false;
            }
        });

    }

    /*
       Clears all cookies to prevent webpage from re-using previous webview session, even after user has logged onto a different account
     */
    @SuppressWarnings("deprecation")
    public static void clearCookies(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }


    /**
     * catches 'home' button click in the webview, giving it the same behaviour as a 'back' key press.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

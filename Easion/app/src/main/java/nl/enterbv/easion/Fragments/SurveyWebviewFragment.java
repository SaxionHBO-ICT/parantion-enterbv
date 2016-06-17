package nl.enterbv.easion.Fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import nl.enterbv.easion.R;


public class SurveyWebviewFragment extends Fragment {
    private WebView surveyWebView;
    private String url;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments().getString("URL");



    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View mView = inflater.inflate(R.layout.fragment_surveywebview, container, false);

        surveyWebView = (WebView) mView.findViewById(R.id.wv_survey);
        surveyWebView.clearHistory();
        surveyWebView.clearCache(true);
        clearCookies(getContext());




        Log.e("testTag15", "url = " + url);
        //surveyWebView.getSettings().setJavaScriptEnabled(true);
        surveyWebView.getSettings().setDomStorageEnabled(true);
        if (!url.equals("")){
            surveyWebView.loadUrl(url);

        }

        surveyWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //TODO fetch data from server to check for changes


                return false;
            }
        });

        //Toast.makeText(getContext(), "actually in survey :DDDDdd", Toast.LENGTH_SHORT).show();


        return mView;
    }

    @SuppressWarnings("deprecation")
    public static void clearCookies(Context context)
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else
        {
            CookieSyncManager cookieSyncMngr=CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager=CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Enquete");
    }
}

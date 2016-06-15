package nl.enterbv.easion.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import nl.enterbv.easion.R;


public class SurveyWebviewFragment extends Fragment {
    WebView surveyWebView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View mView = inflater.inflate(R.layout.fragment_surveywebview, container, false);


        surveyWebView = (WebView)mView.findViewById(R.id.wv_survey);
        surveyWebView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //TODO fetch data from server to check for changes



                return true;
            }
        });



        return mView;
    }
}
